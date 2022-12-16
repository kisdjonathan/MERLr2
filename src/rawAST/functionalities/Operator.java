package rawAST.functionalities;

import completeAST.abstractNode.FunctionalNode;
import rawAST.abstractNode.SyntaxNode;

import java.util.*;

public class Operator extends SyntaxNode implements Iterable<SyntaxNode>{
    private String name = null;
    private List<SyntaxNode> children = new ArrayList<>();
    private List<String> operators = new ArrayList<>();
    private boolean prefix = false;

    public Operator(){}
    public Operator(String name){
        this.name = name;
    }

    public String getUse() {
        return "Operator";
    }
    public String getName() {
        return name;
    }

    public int getSize() {
        return children.size();
    }

    public void addChildren(Collection<SyntaxNode> collection) {
        children.addAll(collection);
    }
    public List<SyntaxNode> getChildren() {
        return children;
    }

    public SyntaxNode getChild(int index) {
        return children.get(index);
    }
    public void addOperand(SyntaxNode child) {
        child.setParent(this);
        children.add(child);
    }
    public void addOperator(String op) {
        operators.add(op);
    }
    public void addOperator(String op, SyntaxNode child) {
        addOperand(child);
        addOperator(op);
    }
    public SyntaxNode setChild(int index, SyntaxNode val) {
        val.setParent(this);
        return children.set(index, val);
    }
    public SyntaxNode removeChild(int index) {
        SyntaxNode ret = children.remove(index);
        ret.setParent(null);
        return ret;
    }

    public boolean isPrefix() {
        return prefix;
    }
    public void setPrefix(boolean v) {
        prefix = v;
    }

    public void evaluated(FunctionalNode parent) {
        //TODO
    }

    public boolean isBefore(Operator other) {
        return isBefore(other.getName());
    }
    public boolean isBefore(String other) {
        return compareTo(getName(), other) <= 0;
    }

    public boolean isAfter(Operator other) {
        return isAfter(other.getName());
    }
    public boolean isAfter(String other) {
        return compareTo(getName(), other) > 0;
    }

    public String toString() {
        return super.toString() + children;
    }
    public Iterator<SyntaxNode> iterator() {
        return children.listIterator();
    }

    public boolean equals(Object other) {
        if(!(Operator.super.equals(other) &&
                getSize() == ((SyntaxNode)other).getSize()))
            return false;
        for(int i = 0; i < getSize(); ++i)
            if(!((Operator)other).getChild(i).equals(getChild(i)))
                return false;
        return true;
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
            {"with"},
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
    private static final Map<String, String> builtinOperatorNames = new HashMap<>(){{
        put("=", "eq"); put("!=", "neq"); put("<", "lt");  put(">", "gt");  put("<=", "lteq");  put(">=", "gteq");
        put("+", "add"); put("-", "sub"); put("||", "parallel"); put("%", "mod"); put("*", "mul"); put("/", "div"); put("^", "exp"); put("!", "factorial");
        put("->", "cast");
        //TODO complete
    }};

    /**
     * returns the spelled-out form of opName
     */
    private String getBuiltin(String opName) {
        String ret = builtinOperatorNames.get(opName);
        if(ret == null)
            return opName;
        return ret;
    }

    private static final Set<String> infixes = new HashSet<>(Arrays.asList(
            "else", "nelse",
            "with",
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
