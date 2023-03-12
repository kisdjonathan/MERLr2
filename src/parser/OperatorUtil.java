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
import AST.operator.chained.*;
import AST.operator.unary.*;
import AST.operator.binary.arithmetic.*;
import AST.operator.binary.comparison.*;
import AST.operator.binary.bitwise.*;
import AST.type.DiscreteRange;
import AST.type.Tuple;

import java.util.*;

/**
 * note: the operations of MERL are allocated without conflicts between precedence levels so far
 * i.e. the same operator symbol will not have different precedences when used as a prefix, postfix, or an infix
 *
 * custom operators are still in need of implementing
 */
public class OperatorUtil {
    private static class PrecedenceLevel {
        public static int SPACING = 128, MIN_VALUE = Integer.MIN_VALUE;
        public String previous, next, repr;
        public double position;
        public PrecedenceLevel(double pos, String prev, String nxt, String repr) {
            position = pos;
            previous = prev;
            next = nxt;
            this.repr = repr;
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


    private static Map<String, OperatorInfo> infixes = new HashMap<>();
    private static Map<String, OperatorInfo> prefixes = new HashMap<>();
    private static Map<String, OperatorInfo> postfixes = new HashMap<>();

    static {
        int levelNum = PrecedenceLevel.MIN_VALUE;

        PrecedenceLevel level = new PrecedenceLevel(levelNum, null, null, ";");
        infixes.put(";", new OperatorInfo(new Tuple(), level));
        levelNum += PrecedenceLevel.SPACING;

        level = new PrecedenceLevel(levelNum, level.repr, null, level.next = If.symbol);
        infixes.put(If.symbol, new OperatorInfo(new If(), level));
        infixes.put(Repeat.symbol, new OperatorInfo(new Repeat(), level));
        infixes.put(While.symbol, new OperatorInfo(new While(), level));
        infixes.put(For.symbol, new OperatorInfo(new For(), level));
        levelNum += PrecedenceLevel.SPACING;

        level = new PrecedenceLevel(levelNum, level.repr, null, level.next = With.symbol);
        infixes.put(With.symbol, new OperatorInfo(new With(), level));
        infixes.put(Then.symbol, new OperatorInfo(new Then(), level));
        levelNum += PrecedenceLevel.SPACING;

        level = new PrecedenceLevel(levelNum, level.repr, null, level.next = Declare.symbol);
        infixes.put(Declare.symbol, new OperatorInfo(new Declare(), level));
        infixes.put(Modify.symbol, new OperatorInfo(new Modify(), level));
        levelNum += PrecedenceLevel.SPACING;

        level = new PrecedenceLevel(levelNum, level.repr, null, level.next = In.symbol);
        infixes.put(In.symbol, new OperatorInfo(new In(), level));
        levelNum += PrecedenceLevel.SPACING;

        level = new PrecedenceLevel(levelNum, level.repr, null, level.next = Field.symbol);
        infixes.put(Field.symbol, new OperatorInfo(new Field(), level));
        levelNum += PrecedenceLevel.SPACING;

        level = new PrecedenceLevel(levelNum, level.repr, null, level.next = ",");
        infixes.put(",", new OperatorInfo(new Tuple(), level));
        levelNum += PrecedenceLevel.SPACING;

        level = new PrecedenceLevel(levelNum, level.repr, null, level.next = "...");
        infixes.put("...", new OperatorInfo(new DiscreteRange(), level));
        levelNum += PrecedenceLevel.SPACING;

        level = new PrecedenceLevel(levelNum, level.repr, null, level.next = Or.literal);
        infixes.put(Or.literal, new OperatorInfo(new Or(), level));
        infixes.put(Nor.literal, new OperatorInfo(new Nor(), level));
        infixes.put(Xor.literal, new OperatorInfo(new Xor(), level));
        infixes.put(Xnor.literal, new OperatorInfo(new Xnor(), level));
        levelNum += PrecedenceLevel.SPACING;

        level = new PrecedenceLevel(levelNum, level.repr, null, level.next = And.literal);
        infixes.put(And.literal, new OperatorInfo(new And(), level));
        infixes.put(Nand.literal, new OperatorInfo(new Nand(), level));
        levelNum += PrecedenceLevel.SPACING;

        level = new PrecedenceLevel(levelNum, level.repr, null, level.next = Equal.symbol);
        infixes.put(Equal.symbol, new OperatorInfo(new ComparisonChain(), level));
        infixes.put(NoEqual.symbol, new OperatorInfo(new ComparisonChain(), level));
        levelNum += PrecedenceLevel.SPACING;

        level = new PrecedenceLevel(levelNum, level.repr, null, level.next = Lesser.symbol);
        infixes.put(Lesser.symbol, new OperatorInfo(new ComparisonChain(), level));
        infixes.put(Greater.symbol, new OperatorInfo(new ComparisonChain(), level));
        infixes.put(NoLesser.symbol, new OperatorInfo(new ComparisonChain(), level));
        infixes.put(NoGreater.symbol, new OperatorInfo(new ComparisonChain(), level));
        levelNum += PrecedenceLevel.SPACING;

        level = new PrecedenceLevel(levelNum, level.repr, null, level.next = Not.literal);
        prefixes.put(Not.literal, new OperatorInfo(new Not(), level));
        levelNum += PrecedenceLevel.SPACING;

        level = new PrecedenceLevel(levelNum, level.repr, null, level.next = Modulo.literal);
        infixes.put(Modulo.literal, new OperatorInfo(new Modulo(), level));
        levelNum += PrecedenceLevel.SPACING;

        level = new PrecedenceLevel(levelNum, level.repr, null, level.next = Add.symbol);
        infixes.put(Add.symbol, new OperatorInfo(new Add(), level));
        infixes.put(Subtract.symbol, new OperatorInfo(new Subtract(), level));
        levelNum += PrecedenceLevel.SPACING;

        level = new PrecedenceLevel(levelNum, level.repr, null, level.next = Parallel.symbol);
        infixes.put(Parallel.symbol, new OperatorInfo(new Parallel(), level));
        levelNum += PrecedenceLevel.SPACING;

        level = new PrecedenceLevel(levelNum, level.repr, null, level.next = Multiply.symbol);
        infixes.put(Multiply.symbol, new OperatorInfo(new Multiply(), level));
        infixes.put(Divide.symbol, new OperatorInfo(new Divide(), level));
        infixes.put(Remainder.symbol, new OperatorInfo(new Remainder(), level));
        infixes.put(Modulo.symbol, new OperatorInfo(new Modulo(), level));
        levelNum += PrecedenceLevel.SPACING;

        level = new PrecedenceLevel(levelNum, level.repr, null, level.next = Exponent.symbol);
        infixes.put(Exponent.symbol, new OperatorInfo(new Exponent(), level));
        infixes.put(Root.symbol, new OperatorInfo(new Root(), level));
        levelNum += PrecedenceLevel.SPACING;

        level = new PrecedenceLevel(levelNum, level.repr, null, level.next = Factorial.symbol);
        postfixes.put(Factorial.symbol, new OperatorInfo(new Factorial(), level));
        levelNum += PrecedenceLevel.SPACING;

        level = new PrecedenceLevel(levelNum, level.repr, null, level.next = Shift.symbol);
        infixes.put(Shift.symbol, new OperatorInfo(new Shift(), level));
        infixes.put(Cycle.symbol, new OperatorInfo(new Cycle(), level));
        levelNum += PrecedenceLevel.SPACING;

        level = new PrecedenceLevel(levelNum, level.repr, null, level.next = Or.symbol);
        infixes.put(Or.symbol, new OperatorInfo(new Or(), level));
        infixes.put(Nor.symbol, new OperatorInfo(new Nor(), level));
        infixes.put(Xor.symbol, new OperatorInfo(new Xor(), level));
        infixes.put(Xnor.symbol, new OperatorInfo(new Xnor(), level));
        levelNum += PrecedenceLevel.SPACING;

        level = new PrecedenceLevel(levelNum, level.repr, null, level.next = And.symbol);
        infixes.put(And.symbol, new OperatorInfo(new And(), level));
        infixes.put(Nand.symbol, new OperatorInfo(new Nand(), level));
        levelNum += PrecedenceLevel.SPACING;

        level = new PrecedenceLevel(levelNum, level.repr, null, level.next = Not.symbol);
        prefixes.put(Not.symbol, new OperatorInfo(new Not(), level));
        levelNum += PrecedenceLevel.SPACING;

        level = new PrecedenceLevel(levelNum, level.repr, null, level.next = Cardinal.symbol);
        postfixes.put(Cardinal.symbol, new OperatorInfo(new Cardinal(), level));
        levelNum += PrecedenceLevel.SPACING;

        level = new PrecedenceLevel(levelNum, level.repr, null, level.next = PreIncrement.symbol);
        prefixes.put(PreIncrement.symbol, new OperatorInfo(new PreIncrement(), level));
        prefixes.put(PreDecrement.symbol, new OperatorInfo(new PreDecrement(), level));
        postfixes.put(PostIncrement.symbol, new OperatorInfo(new PostIncrement(), level));
        postfixes.put(PostDecrement.symbol, new OperatorInfo(new PostDecrement(), level));
        levelNum += PrecedenceLevel.SPACING;

        level = new PrecedenceLevel(levelNum, level.repr, null, level.next = Print.symbol);
        prefixes.put(Print.symbol, new OperatorInfo(new Print(), level));
        levelNum += PrecedenceLevel.SPACING;

        level = new PrecedenceLevel(levelNum, level.repr, null, level.next = Cast.symbol);
        infixes.put(Cast.symbol, new OperatorInfo(new Cast(), level));
        infixes.put(As.symbol, new OperatorInfo(new As(), level));
        levelNum += PrecedenceLevel.SPACING;

        level = new PrecedenceLevel(levelNum, level.repr, null, level.next = Ref.symbol);
        prefixes.put(Ref.symbol, new OperatorInfo(new Ref(), level));
        prefixes.put(Loc.symbol, new OperatorInfo(new Loc(), level));
        prefixes.put(Pin.symbol, new OperatorInfo(new Pin(), level));
        levelNum += PrecedenceLevel.SPACING;

        level = new PrecedenceLevel(levelNum, level.repr, null, level.next = Call.symbol);
        infixes.put(Call.symbol, new OperatorInfo(new Call(), level));
        infixes.put(Index.symbol, new OperatorInfo(new Index(), level));
        infixes.put(Construct.symbol, new OperatorInfo(new Construct(), level));
        levelNum += PrecedenceLevel.SPACING;
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