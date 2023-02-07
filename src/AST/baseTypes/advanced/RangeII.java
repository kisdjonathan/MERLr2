package AST.baseTypes.advanced;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;

public class RangeII extends Range{
    public RangeII(){}
    public RangeII(int start, int stop){
        super(start, stop);
    }
    public RangeII(int start, int stop, int step){
        super(start, stop, step);
    }

    public RangeII(SyntaxNode start, SyntaxNode end) {
        //TODO
    }

    public boolean typeEquals(BasicType other) {
        return other instanceof RangeII;
    }
    public RangeII clone() {
        RangeII ret = new RangeII();
        ret.setStart(getStart().clone());
        ret.setStop(getStop().clone());
        ret.setStep(getStep().clone());
        return ret;
    }
}