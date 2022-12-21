package AST.baseTypes;


import AST.abstractNode.SyntaxNode;
import interpreter.Context;
import interpreter.Value;

public class Int extends Numerical{
    private long value;

    public Int(){
        value = 0;
    }
    public Int(long val){
        value = val;
    }
    public Int(String val) {
        //TODO val is float 0.0, or int 0,1
        value = Long.parseLong(val);
    }

    public String getName() {
        return "int";
    }
    public long getValue() {
        return value;
    }

    public boolean typeEquals(SyntaxNode other) {
        return getByteSize().compareTo(other.getBaseType().getByteSize()) == 0;
    }
    public boolean typeContains(SyntaxNode other) {
        return getByteSize().compareTo(other.getBaseType().getByteSize()) >= 0;
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
