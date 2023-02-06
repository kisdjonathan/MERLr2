package AST.baseTypes.advanced;


import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;

public class RangeEE extends Range {
    public RangeEE(){}
    public RangeEE(int start, int stop){
        super(start, stop);
    }
    public RangeEE(int start, int stop, int step){
        super(start, stop, step);
    }

    public RangeEE(SyntaxNode start, SyntaxNode end) {
        //TODO
    }

    public boolean typeEquals(BasicType other) {
        return other instanceof RangeEE;
    }
    public RangeEE clone() {
        RangeEE ret = new RangeEE();
        ret.setStart(getStart().clone());
        ret.setStop(getStop().clone());
        ret.setStep(getStep().clone());
        return ret;
    }
}
