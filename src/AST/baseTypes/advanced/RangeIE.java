package AST.baseTypes.advanced;


import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;

public class RangeIE extends Range {
    public RangeIE(){}
    public RangeIE(int start, int stop){
        super(start, stop);
    }
    public RangeIE(int start, int stop, int step){
        super(start, stop, step);
    }

    public RangeIE(SyntaxNode start, SyntaxNode end) {
        //TODO
    }

    public boolean typeEquals(BasicType other) {
        return other instanceof RangeIE;
    }
    public RangeIE clone() {
        RangeIE ret = new RangeIE();
        ret.setStart(getStart().clone());
        ret.setStop(getStop().clone());
        ret.setStep(getStep().clone());
        return ret;
    }
}
