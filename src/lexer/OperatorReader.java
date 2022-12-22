package lexer;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.Structure;
import AST.baseTypes.Tuple;
import AST.operations.variable.*;
import AST.operations.*;
import AST.operations.arithmetic.*;
import AST.operations.bool.*;
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
            case "^"        ->new Exponent  ();
            case "!"        ->new Factorial ();
            //boolean
            case "and"      ->new And       ();
            case "or"       ->new Or        ();
            case "nor"      ->new Nor       ();
            case "xor"      ->new Xor       ();
            case "xnor"     ->new Xnor      ();
            //comparison
            case "!="       ->new NotEqual  ();
            case "="        ->new Equal     ();
            case ">=",">"   ->new Ascending ();
            case "<=","<"   ->new Descending();
            //bitwise
            case "$and"     ->new BitAnd    ();
            case "$or"      ->new BitOr     ();
            case "$nor"     ->new BitNor    ();
            case "$xor"     ->new BitXor    ();
            case "$xnor"    ->new BitXnor   ();
            //other
            case ">>"       ->new Field     ();
            case "<<"       ->new Assign    ();
            case "->"       ->new Cast      ();
            case "with"     ->new With      ();
            case "then"     ->new Without   ();
            case "in"       ->new In        ();

            case ";",","    ->new Tuple     ();

            default -> throw new Error("unable to find operator " + name);
        };
    }
    public static SyntaxNode prefix(String oper, SyntaxNode opand) {
        return switch (oper) {
            case "+"        ->new Positive  (opand);
            case "-"        ->new Negative  (opand);
            case "not","!"  ->new Not       (opand);
            case "$not"     ->new BitNot    (opand);
            case "#"        ->new Cardinal  (opand);
            default -> throw new Error("unable to find prefix " + oper);
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
            case "$invert"  ->new BitNot    (opand);
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
            {";", ":"},
            {"if", "while", "repeat", "for", "else", "nelse"},
            {"in"},
            {"with", "then"},
            {"<<", ">>"},
            {","},
            {"or"}, {"nor"}, {"xor"}, {"xnor"}, {"and"},
            {"="}, {"!="}, {"<", ">", "<=", ">="},
            {"not"},
            {"+", "-"}, {"||"}, {"%"}, {"*", "/"}, {"^"}, {"!"},
            {"$up", "$down", "$left", "$right"},
            {"$or"}, {"$nor"}, {"$xor"}, {"$xnor"}, {"$and"},
            {"$invert"},
            {"->"},
            {"ref"}
    };

    private static final Set<String> infixes = new HashSet<>(Arrays.asList(
            "else", "nelse",
            "with", "then",
            ",", ";",
            "or", "nor", "xor", "xnor", "and",
            "<<", ">>",
            "=", "!=", "<", ">", "<=", ">=",
            "+", "-", "||", "%", "*", "/", "^",
            "$up", "$down", "$left", "$right",
            "$or", "$nor", "$xor", "$xnor", "$and",
            "->",
            ":"
    ));
    private static final Set<String> prefixes = new HashSet<>(Arrays.asList(
            "if", "for", "while", "repeat",
            "+", "-",
            "not", "$invert",
            "ref"
    ));
    private static final Set<String> postfixes = new HashSet<>(Arrays.asList(
            ";", "%", "!"
    ));
    private static final List<Set<String>> chainGroups = Arrays.asList(
            new HashSet<>(Arrays.asList("<", "<=", "=")),
            new HashSet<>(Arrays.asList(">", ">=", "=")),
            new HashSet<>(Arrays.asList("else", "nelse")),
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
        return "<<".equals(op) || ">>".equals(op);
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
}
