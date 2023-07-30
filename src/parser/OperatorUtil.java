package parser;

import AST.abstractNode.Operator;
import AST.abstractNode.SyntaxNode;
import AST.control.For;
import AST.control.If;
import AST.control.Repeat;
import AST.control.While;
import AST.operator.binary.flow.Then;
import AST.operator.binary.flow.With;
import AST.operator.binary.list.In;
import AST.operator.binary.list.Index;
import AST.operator.binary.variable.*;
import AST.operator.binary.function.Call;
import AST.operator.unary.*;
import AST.operator.binary.arithmetic.*;
import AST.operator.binary.comparison.*;
import AST.operator.binary.bitwise.*;
import AST.operator.unary.arithmetic.Factorial;
import AST.operator.unary.list.Cardinal;
import AST.operator.unary.list.Spread;
import AST.operator.unary.variable.Loc;
import AST.operator.unary.variable.Pin;
import AST.operator.unary.Print;
import AST.operator.unary.variable.Ref;
import type.DiscreteRange;
import type.Tuple;
import type.Type;

import java.util.*;
import java.util.function.Consumer;

/**
 * note: the operations of MERL are allocated without conflicts between precedence levels so far
 * i.e. the same operator symbol will not have different precedences when used as a prefix, postfix, or an infix
 *
 * custom operators are still in need of implementing
 */
public class OperatorUtil {
    private static class PrecedenceLevel {
        public static int SPACING = 128, MIN_VALUE = Integer.MIN_VALUE;
        public double position;
        public PrecedenceLevel(double pos, String prev, String nxt) {
            position = pos;
        }
        public PrecedenceLevel(double pos) {
            position = pos;
        }
    }
    private static class OperatorInfo {
        public SyntaxNode node;
        public PrecedenceLevel precedence;
        public OperatorInfo(SyntaxNode node, PrecedenceLevel precedence) {
            this.node = node;
            this.precedence = precedence;
        }
    }

    private static class SymbolEntry {
        public static final String PREFIX="prefix", INFIX="infix", POSTFIX ="suffix";

        public String type;
        public String symbol;
        public SyntaxNode operator;
        public SymbolEntry(String type, String symbol, SyntaxNode operator) {
            this.type = type;
            this.symbol = symbol;
            this.operator = operator;
        }
    }


    private static Map<String, OperatorInfo> infixes = new HashMap<>();
    private static Map<String, OperatorInfo> prefixes = new HashMap<>();
    private static Map<String, OperatorInfo> postfixes = new HashMap<>();

    /*
     * Operators in increasing order of precedence
     * ;
     * import   export
     * ::
     *(.) if    repeat  while      for      resolve
     * with     then    using
     * do   (prefix)
     * :        =       <=>
     * in
     * ,
     * pop      leak    >>  (suffix and infix)
     * push     slip    <<       \  (suffix and infix)
     * ...
     *(.) or    nor     xor     xnor
     *(.) and   nand
     * ==       !=      ~=      <       >       <=      >=
     * ?=
     *(.) not   (prefix)
     *(.) shift cycle
     *(.) mod   rem
     *(.) +     -
     *(.) ||
     *(.) ><
     *(.) *     /       %
     *(.) !     (suffix)
     *(.) **    */
    /*(.) |     ~|      ^       ~^
     *(.) &     ~&
     *(.) !     ~   (prefix)
     * #    (suffix)
     * ++   --  (suffix and prefix)
     * @
     * to       as
     * ref      loc     pin
     * _(_)     _[_]    _{_}
     * */
    static {
        final int[] levelNum = {PrecedenceLevel.MIN_VALUE};   //wrap levelNum
        final PrecedenceLevel[] level = {new PrecedenceLevel(levelNum[0])};   //placeholder level; wrap level

        Consumer<List<SymbolEntry>> addLevel = (ops) -> {
            level[0] = new PrecedenceLevel(levelNum[0]);
            for(SymbolEntry entry : ops) {
                (switch (entry.type) {
                    case SymbolEntry.INFIX -> infixes;
                    case SymbolEntry.PREFIX -> prefixes;
                    case SymbolEntry.POSTFIX -> postfixes;
                }).put(entry.symbol, new OperatorInfo(entry.operator, level[0]));
            }
            levelNum[0] += PrecedenceLevel.SPACING;
        };

        addLevel.accept(List.of(
                new SymbolEntry(SymbolEntry.INFIX, ";", new Tuple())
        ));
        addLevel.accept(List.of(
                new SymbolEntry(SymbolEntry.INFIX, "::", new Field())
        ));
        addLevel.accept(List.of(
                new SymbolEntry(SymbolEntry.PREFIX, "if", new If()),
                new SymbolEntry(SymbolEntry.PREFIX, "repeat", new Repeat()),
                new SymbolEntry(SymbolEntry.PREFIX, "while", new While()),
                new SymbolEntry(SymbolEntry.PREFIX, "for", new For())
        ));
        addLevel.accept(List.of(
                new SymbolEntry(SymbolEntry.INFIX, "else", null),
                new SymbolEntry(SymbolEntry.INFIX, "also", null)
        ));
        addLevel.accept(List.of(
                new SymbolEntry(SymbolEntry.INFIX, "with", new With()),
                new SymbolEntry(SymbolEntry.INFIX, "then", new Then())
        ));
        addLevel.accept(List.of(
                new SymbolEntry(SymbolEntry.PREFIX, "do", new Call())
        ));
        addLevel.accept(List.of(
                new SymbolEntry(SymbolEntry.INFIX, ":", new Declare()),
                new SymbolEntry(SymbolEntry.INFIX, "=", new Modify()),
                new SymbolEntry(SymbolEntry.INFIX, "<=>", new Associate())
        ));
        addLevel.accept(List.of(
                new SymbolEntry(SymbolEntry.INFIX, "in", new In())
        ));
        addLevel.accept(List.of(
                new SymbolEntry(SymbolEntry.INFIX, ",", new Tuple())
        ));
        addLevel.accept(List.of(
                new SymbolEntry(SymbolEntry.INFIX, "...", new DiscreteRange()),
                new SymbolEntry(SymbolEntry.PREFIX, "...", new Spread())
        ));
        addLevel.accept(List.of(
                new SymbolEntry(SymbolEntry.INFIX, "or", new Or()),
                new SymbolEntry(SymbolEntry.INFIX, "nor", new Nor()),
                new SymbolEntry(SymbolEntry.INFIX, "xor", new Xor()),
                new SymbolEntry(SymbolEntry.INFIX, "xnor", new Xnor())
        ));
        addLevel.accept(List.of(
                new SymbolEntry(SymbolEntry.INFIX, "and", new And()),
                new SymbolEntry(SymbolEntry.INFIX, "nand", new Nand())
        ));
        addLevel.accept(List.of(
                new SymbolEntry(SymbolEntry.INFIX, "=", new ComparisonChain()),
                new SymbolEntry(SymbolEntry.INFIX, "!=", new ComparisonChain()),
                new SymbolEntry(SymbolEntry.INFIX, "~=", new ComparisonChain()),
                new SymbolEntry(SymbolEntry.INFIX, "<", new ComparisonChain()),
                new SymbolEntry(SymbolEntry.INFIX, ">", new ComparisonChain()),
                new SymbolEntry(SymbolEntry.INFIX, "<=", new ComparisonChain()),
                new SymbolEntry(SymbolEntry.INFIX, ">=", new ComparisonChain())
        ));
        addLevel.accept(List.of(
                new SymbolEntry(SymbolEntry.INFIX, "?=", new CompareTo())
        ));
        addLevel.accept(List.of(
                new SymbolEntry(SymbolEntry.PREFIX, "not", new Not())
        ));
        addLevel.accept(List.of(
                new SymbolEntry(SymbolEntry.INFIX, "shift", new Shift()),
                new SymbolEntry(SymbolEntry.INFIX, "cycle", new Cycle())
        ));
        addLevel.accept(List.of(
                new SymbolEntry(SymbolEntry.INFIX, "mod", new Modulo()),
                new SymbolEntry(SymbolEntry.INFIX, "rem", new Remainder())
        ));
        addLevel.accept(List.of(
                new SymbolEntry(SymbolEntry.INFIX, "+", new Add()),
                new SymbolEntry(SymbolEntry.INFIX, "-", new Subtract())
        ));
        addLevel.accept(List.of(
                new SymbolEntry(SymbolEntry.INFIX, "||", new Parallel())
        ));
        addLevel.accept(List.of(
                new SymbolEntry(SymbolEntry.INFIX, "><", new CrossMultiply())
        ));
        addLevel.accept(List.of(
                new SymbolEntry(SymbolEntry.INFIX, "*", new Multiply()),
                new SymbolEntry(SymbolEntry.INFIX, "/", new Divide()),
                new SymbolEntry(SymbolEntry.INFIX, "%", new Remainder())
        ));
        addLevel.accept(List.of(
                new SymbolEntry(SymbolEntry.POSTFIX, "!", new Factorial())
        ));
        addLevel.accept(List.of(
                new SymbolEntry(SymbolEntry.INFIX, "**", new Exponent()),
                new SymbolEntry(SymbolEntry.INFIX, "*/", new Root())
        ));
        addLevel.accept(List.of(
                new SymbolEntry(SymbolEntry.INFIX, "|", new Or()),
                new SymbolEntry(SymbolEntry.INFIX, "~|", new Nor()),
                new SymbolEntry(SymbolEntry.INFIX, "^", new Xor()),
                new SymbolEntry(SymbolEntry.INFIX, "~^", new Xnor())
        ));
        addLevel.accept(List.of(
                new SymbolEntry(SymbolEntry.INFIX, "&", new And()),
                new SymbolEntry(SymbolEntry.INFIX, "~&", new Nand())
        ));
        addLevel.accept(List.of(
                new SymbolEntry(SymbolEntry.PREFIX, "!", new Not()),
                new SymbolEntry(SymbolEntry.PREFIX, "~", new Not())
        ));
        addLevel.accept(List.of(
                new SymbolEntry(SymbolEntry.POSTFIX, "#", new Cardinal())
        ));
        addLevel.accept(List.of(
                new SymbolEntry(SymbolEntry.PREFIX, "++", new PreIncrement()),
                new SymbolEntry(SymbolEntry.POSTFIX, "++", new PostIncrement()),
                new SymbolEntry(SymbolEntry.PREFIX, "--", new PreDecrement()),
                new SymbolEntry(SymbolEntry.POSTFIX, "--", new PostDecrement())
        ));
        addLevel.accept(List.of(
                new SymbolEntry(SymbolEntry.PREFIX, "@", new Print())
        ));
        addLevel.accept(List.of(
                new SymbolEntry(SymbolEntry.INFIX, "to", new Cast()),
                new SymbolEntry(SymbolEntry.INFIX, "as", new As())
        ));
        addLevel.accept(List.of(
                new SymbolEntry(SymbolEntry.PREFIX, "ref", new Ref()),
                new SymbolEntry(SymbolEntry.PREFIX, "loc", new Loc()),
                new SymbolEntry(SymbolEntry.PREFIX, "pin", new Pin())
        ));
        addLevel.accept(List.of(
                new SymbolEntry(SymbolEntry.INFIX, "(", new Call()),
                new SymbolEntry(SymbolEntry.INFIX, "[", new Index()),
                new SymbolEntry(SymbolEntry.INFIX, "{", new Construct())
        ));
    }

    /**
     * returns a new instance of the operation corresponding to name
     * includes both infix and suffix
     */
    public static SyntaxNode decodeInfix(Token name) {
        if(!infixes.containsKey(name.getValue()))
            throw new Error("Syntax error at line " + name.getLineNumber() + ": invalid infix operator " + name.getValue());
        return infixes.get(name.getValue()).node.emptyClone();
    }
    public static SyntaxNode decodePostfix(Token name) {
        if(!postfixes.containsKey(name.getValue()))
            throw new Error("Syntax error at line " + name.getLineNumber() + ": invalid postfix operator " + name.getValue());
        return postfixes.get(name.getValue()).node.emptyClone();
    }
    public static SyntaxNode decodePrefix(Token name) {
        if(!prefixes.containsKey(name.getValue()))
            throw new Error("Syntax error at line " + name.getLineNumber() + ": invalid prefix operator " + name.getValue());
        return prefixes.get(name.getValue()).node.emptyClone();
    }
    public static SyntaxNode decodeCall(SyntaxNode caller, Token startDelim, Token endDelim, SyntaxNode args) {
        String parenthesis = startDelim.getValue() + endDelim.getValue();
        if(!parenthesis.equals("()") &&
            !parenthesis.equals("[]") &&
            !parenthesis.equals("{}"))
            throw new Error("Syntax error at line " + startDelim.getLineNumber() + ": invalid calling brackets " + parenthesis);

        SyntaxNode ret = decodeInfix(startDelim);
        ret.addChild(caller);
        ret.addChild(args);
        ret.setLine(startDelim.getLineNumber());
        return ret;
    }

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
    public static boolean isOperator(String op) {
        return infixes.containsKey(op) || prefixes.containsKey(op) || postfixes.containsKey(op);
    }
    public static boolean isInfix(String op) {
        return infixes.containsKey(op);
    }
    public static boolean isPrefix(String op) {
        return prefixes.containsKey(op);
    }
    public static boolean isPostfix(String op) {
        return postfixes.containsKey(op);
    }
    private static PrecedenceLevel levelOf(String op) {
        if(isPrefix(op))
            return prefixes.get(op).precedence;
        if(isInfix(op))
            return infixes.get(op).precedence;
        if(isPostfix(op))
            return postfixes.get(op).precedence;
        throw new Error("Compiler error: invalid attempt to access non-operator " + op);
    }
    private static double indexOf(String op) {
        return levelOf(op).position;
    }

    private static int compareTo(String a, String b) {
        double t = indexOf(a);
        double o = indexOf(b);
        return Double.compare(t, o);
    }

    private static boolean isRightToLeft(String op) {
        return "=".equals(op) || ":".equals(op);
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

        public Type getType() {
            throw new Error(errorString("Compiler error: accessing type of incomplete operator \"ComparisonChain\""));
        }
        public SyntaxNode emptyClone() {
            throw new Error(errorString("Compiler error: accessing clone of incomplete operator \"ComparisonChain\""));
        }


        public String getName() {
            return "comparison";
        }
        protected List<Evaluation> getEvaluationList() {
            throw new Error(errorString("Compiler error: accessing evaluation of incomplete operator \"ComparisonChain\""));
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