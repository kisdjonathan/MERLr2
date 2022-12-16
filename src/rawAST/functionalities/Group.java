package rawAST.functionalities;


import rawAST.abstractNode.SyntaxNode;

//baseAST.Group serves only as a placeholder for the body in order to simulate precedence
public class Group extends SyntaxNode {
    private String name = null;
    private SyntaxNode body = null;
    private boolean constant = false;
    private boolean complete = false;

    public Group(){

    }
    public Group(String name){
        this.name = name;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public Usage getUsage() {
        return Usage.GROUP;
    }

    public int size() {
        return body == null ? 0 : 1;
    }

    public SyntaxNode getBody() {
        return body;
    }
    public void setBody(SyntaxNode body) {
        this.body = body;
        body.setParent(this);
    }

    public FinalSyntaxNode getReplacement() {
        switch (getName()) {
            case "()":
                return body.getReplacement();
            case "[]":
                return new DynamicArray(){{
                    setBody(body);
                }};
            case "{}":
                return new UnorderedSet(){{
                    setBody(body);
                }};
            case "(]":
            case "[)":
                return Range.decode(this);    //TODO L range from [] and ()
            default:    //EOF
                return new Local(){{
                    setBody(body);
                }};
        }
    }

    public boolean isConstant() {
        return constant;
    }
    public void setConstant(boolean v) {
        constant = v;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setType(FinalSyntaxNode type) {
        this.type = type;
    }
    public FinalSyntaxNode getType() {
        return type;
    }

    public void setComplete(boolean v) {
        complete = v;
    }

    public static boolean isStartDelimiter(String s) {
        return s.length() == 1 && switch(s.charAt(0)){
            case '[', '{', '(', '|' ->true;
            default -> false;
        };
    }
    public static boolean isEndDelimiter(String s) {
        return s.length() == 1 && switch(s.charAt(0)){
            case ']', '}', ')', '|' ->true;
            default -> false;
        };
    }

    public String toString() {
        return super.toString() + "[" + body + "]";
    }
    public boolean equals(Object other) {
        return super.equals(other) && other instanceof Group gOther && (
                gOther.getBody() == null && getBody() == null || gOther.getBody().equals(getBody()));
    }
}
