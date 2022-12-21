package AST.baseTypes;


import AST.abstractNode.SyntaxNode;
import interpreter.Context;
import interpreter.Value;

public class Int extends Numerical{
    private int value;

    public Int(){
        value = 0;
    }
    public Int(int val){
        value = val;
    }
    public Int(String val) {
        //TODO val is float 0.0, or int 0,1
        value = Integer.parseInt(val);
    }

    public String getName() {
        return "int";
    }
    public int getValue() {
        return value;
    }

    public boolean typeEquals(SyntaxNode other) {
        return false;   //TODO
    }
    public boolean typeContains(SyntaxNode other) {
        return false;   //TODO
    }

    protected int defaultByteSize() {
        return 4;
    }

    public String toString() {
        return super.toString() + " " + value;
    }

    @Override
    public BasicType getType() {
        return null;
    }

    @Override
    public Value interpret(Context context) {
        return null;
    }
}
