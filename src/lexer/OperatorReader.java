package lexer;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.numerical.Bool;
import AST.baseTypes.Structure;
import AST.baseTypes.Tuple;
import AST.operations.variable.*;
import AST.operations.*;
import AST.operations.arithmetic.*;
import AST.operations.comparison.*;
import AST.operations.bitwise.*;

import java.util.*;

public class OperatorReader {
    /**
     * returns a new instance of the operation corresponding to name
     * includes both infix and suffix
     */
    public static SyntaxNode decode(String name) {
        return switch (name) {
            //Arithmetic
            case "+"        ->new Add       ();
            case "-"        ->new Subtract  ();
            case "*"        ->new Multiply  ();
            case "/"        ->new Divide    ();
            case "%"        ->new Modulo    ();
            case "**"       ->new Exponent  ();
            case "!"        ->new Factorial ();
            //boolean, bitwise
            case "and","&&" ->new And       ();
            case "or","||"  ->new Or        ();
            case "nor","~|" ->new Nor       ();
            case "xor","^^" ->new Xor       ();
            case "xnor","~^"->new Xnor      ();
            //other
            case "<<"       ->null;
            case ">>"       ->new Field     ();
            case ":"        ->new Declare   ();
            case "="        ->new Modify    ();
            case "->"       ->new Cast      ();
            case "with"     ->new With      ();
            case "then"     ->new Without   ();
            case "in"       ->new In        ();
            case "@"        ->new Print     ();

            case ";",","    ->new Tuple     ();

            //comparison
            case "!=","==",">=",">","<=","<" -> new ComparisonChain();

            default -> throw new Error("unable to find operator " + name);
        };
    }
    public static SyntaxNode decodeCall(SyntaxNode caller, String startDelim, String endDelim, SyntaxNode args) {
        return switch (startDelim + endDelim) {
            case "()"   ->new Call      (caller, args);
            case "[]"   ->new Index     (caller, args);
            case "{}"   ->new Structure (caller, args);
            default     ->throw new Error("invalid calling brackets " + startDelim + endDelim);
        };
    }
    public static Operator decodePrefix(String oper, SyntaxNode opand) {
        return switch (oper) {
            case "+"        ->new Positive  (opand);
            case "-"        ->new Negative  (opand);
            case "not","!"  ->new Not       (opand);
            case "@"        ->new Print     (opand);
            case "ref"      ->null;
            default         ->throw new Error("invalid prefix " + oper);
        };
    }


    private static class PrecedenceLevel {
        public static int SPACING = 16, MIN_VALUE = Integer.MIN_VALUE;
        public PrecedenceLevel(double pos, String prev, String nxt) {
            position = pos;
            previous = prev;
            next = nxt;
        }
        public String previous = null, next = null;
        public double position;
    }
    private static final String[][] builtinOperators = new String[][]{  //sorted by low to high precedence
            {"|", ")", "}", "]"},
            {";"},
            {"if", "while", "repeat", "for", "else", "nelse"},
            {"break", "continue", "return"},
            {"in"},
            {"with", "then"},
            {":", "="},
            {"<<", ">>"},
            {","},
            {"or"}, {"nor"}, {"xor"}, {"xnor"}, {"and"},
            {"=="}, {"!="}, {"<", ">", "<=", ">="},
            {"not"},
            {"+", "-"}, {"||"}, {"%"}, {"*", "/"}, {"**"}, {"!"},
            {"$up", "$down", "$left", "$right"},
            {"$or"}, {"$nor"}, {"$xor"}, {"$xnor"}, {"$and"},
            {"$invert"},
            {"@"},
            {"as"},
            {"ref"},
            {"(", "[", "{"}
    };

    private static final Set<String> infixes = new HashSet<>(Arrays.asList(
            "else", "nelse",
            "with", "then",
            ",", ";",
            "or", "nor", "xor", "xnor", "and",
            "=", ":",
            "<<", ">>",
            "==", "!=", "<", ">", "<=", ">=",
            "+", "-", "||", "%", "*", "/", "**",
            "$up", "$down", "$left", "$right",
            "$or", "$nor", "$xor", "$xnor", "$and",
            "as"
    ));
    private static final Set<String> prefixes = new HashSet<>(Arrays.asList(
            "if", "for", "while", "repeat",
            "+", "-",
            "not", "$invert",
            "@",
            "ref"
    ));
    private static final Set<String> postfixes = new HashSet<>(Arrays.asList(
            ";", "%", "!"
    ));
    private static final List<Set<String>> chainGroups = Arrays.asList(
            new HashSet<>(Arrays.asList("<", "<=", "==")),
            new HashSet<>(Arrays.asList(">", ">=", "==")),
            new HashSet<>(Arrays.asList("==", "!=")),
            new HashSet<>(Arrays.asList("else", "nelse")),
            new HashSet<>(List.of("=", ":")),
            new HashSet<>(List.of("<<")),
            new HashSet<>(List.of(">>")),
            new HashSet<>(List.of(",")),
            new HashSet<>(List.of(";"))
    );
    private static final Map<String, PrecedenceLevel> precedences = new HashMap<>();
    static {
        int levelNum = PrecedenceLevel.MIN_VALUE;
        String prevRepr = null;
        PrecedenceLevel prevLevel = null;
        for(String[] oplevel : builtinOperators) {
            PrecedenceLevel level = new PrecedenceLevel(levelNum, prevRepr, null);

            for(String op : oplevel)
                precedences.put(op, level);

            if(prevLevel != null)
                prevLevel.next = oplevel[0];
            prevRepr = oplevel[0];
            prevLevel = level;
            levelNum += PrecedenceLevel.SPACING;
        }
    }
    public static boolean isOperator(String op) {
        return precedences.containsKey(op);
    }
    public static boolean isInfix(String op) {
        return infixes.contains(op);
    }
    public static boolean isPrefix(String op) {
        return prefixes.contains(op);
    }
    public static boolean isPostfix(String op) {
        return postfixes.contains(op);
    }
    private static double indexOf(String op) {
        return precedences.get(op).position;
    }

    private static int compareTo(String a, String b) {
        double t = indexOf(a);
        double o = indexOf(b);
        return Double.compare(t, o);
    }

    private static boolean isRightToLeft(String op) {
        return "=".equals(op) || ":".equals(op) || "<<".equals(op) || ">>".equals(op);
    }
    private static boolean isLeftToRight(String op) {
        return !isRightToLeft(op);
    }

    /**
     * pred comes before ref in the line (eg 1+2*3 -> pred = *, ref = +)
     */
    public static boolean isBefore(String pred, String ref) {
        int cmp = compareTo(pred, ref);
        return cmp > 0 || (cmp == 0 && isRightToLeft(pred));
    }

    /**
     * post comes after ref in the line (eg 1*2+3 -> ref = *, post = +)
     */
    public static boolean isAfter(String post, String ref) {
        return !isBefore(post, ref);
    }

    public static void addOperatorBefore(String ref, String op) {
        PrecedenceLevel topLevel = precedences.get(ref);
        PrecedenceLevel lowLevel = precedences.get(topLevel.previous);
        PrecedenceLevel curLevel = new PrecedenceLevel((topLevel.position + lowLevel.position)/2, topLevel.previous, lowLevel.next);
        lowLevel.next = topLevel.previous = op;
        precedences.put(op, curLevel);
    }
    public static void addOperatorAfter(String ref, String op) {
        addOperatorBefore(precedences.get(ref).next, op);
    }
    public static void addOperatorAt(String ref, String op) {
        precedences.put(op, precedences.get(ref));
    }
    public static void addInfix(String op) {
        infixes.add(op);
    }
    public static void addPrefix(String op) {
        prefixes.add(op);
    }
    public static void addPostfix(String op) {
        postfixes.add(op);
    }

    public static boolean isChainable(String op, String with) {
        for(var v : chainGroups) {
            if(v.contains(op) && v.contains(with))
                return true;
        }
        return false;
    }


    /**
     * auxiliary class for parsing
     */
    private static class ComparisonChain extends Operator {
        private List<String> operators = new ArrayList<>();

        public void addChild(String descrip, SyntaxNode child) {
            super.addChild(descrip, child);
            operators.add(descrip);
        }
        public String getOperator(int index) {
            return operators.get(index);
        }

        public ComparisonChain(){}
        public ComparisonChain(SyntaxNode a, SyntaxNode b) {
            addChild(a); addChild(b);
        }

        public BasicType getType() {
            return new Bool();
        }
        public SyntaxNode clone() {
            return null;
        }
        public BasicType interpret() {
            return null;
        }
        public String getName() {
            return "comparison";
        }
    }

    public static SyntaxNode verify(SyntaxNode val) {
        if(val instanceof ComparisonChain compop) {
            List<SyntaxNode> conditions = new ArrayList<>();
            SyntaxNode prevVal = compop.getChild(0);
            for(int i = 1; i < compop.size(); ++i) {
                switch (compop.getOperator(i-1)) {
                    case "<"->
                            conditions.add(new Lesser(prevVal, compop.getChild(i)));
                    case ">"->
                            conditions.add(new Greater(prevVal, compop.getChild(i)));
                    case "<="->
                            conditions.add(new NoGreater(prevVal, compop.getChild(i)));
                    case ">="->
                            conditions.add(new NoLesser(prevVal, compop.getChild(i)));
                    case "=="->
                            conditions.add(new Equal(prevVal, compop.getChild(i)));
                    case "!="->
                            conditions.add(new NoEqual(prevVal, compop.getChild(i)));
                }
                prevVal = compop.getChild(i);
            }
            if(conditions.size() == 1)
                return conditions.get(0);

            And ret = new And(conditions.get(0), conditions.get(1));
            for(int i = 2; i < conditions.size(); ++i)
                ret = new And(ret, conditions.get(i));
            return ret;
        }
        return val;
    }
}
