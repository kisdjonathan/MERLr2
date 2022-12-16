package completeAST.baseTypes;

import completeAST.abstractNode.FunctionalNode;
import derivedAST.FinalSyntaxNode;

public class Char extends Numerical{
    private short value = '\0';

    public Char(){}
    public Char(short val) {
        value = val;
    }
    public Char(String val){
        //TODO val is string literal a,b... or 0,1...
        value = (short)val.charAt(0);
    }

    public String getName() {
        return "char";
    }
    public short getValue() {
        return value;
    }

    public boolean typeEquals(FunctionalNode other) {
        return getByteSize().compareTo(other.getBaseType().getByteSize()) == 0;
    }
    public boolean typeContains(FunctionalNode other) {
        return getByteSize().compareTo(other.getBaseType().getByteSize()) >= 0;
    }

    protected int defaultByteSize() {
        return 1;
    }

    public FunctionalNode newInstance(String s) {
        return new Char((short) Integer.parseInt(s));
    }

    public String toString() {
        return super.toString() + " " + value;
    }
}
