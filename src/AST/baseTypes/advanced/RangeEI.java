package AST.baseTypes.advanced;


import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;

public class RangeEI extends Range {
    public RangeEI(){}
    public RangeEI(int start, int stop){
        super(start, stop);
    }
    public RangeEI(int start, int stop, int step){
        super(start, stop, step);
    }

    public RangeEI(SyntaxNode start, SyntaxNode end) {
        //TODO
    }

    public boolean typeEquals(BasicType other) {
        return other instanceof RangeEI;
    }
    public RangeEI clone() {
        RangeEI ret = new RangeEI();
        ret.setStart(getStart().clone());
        ret.setStop(getStop().clone());
        ret.setStep(getStep().clone());
        return ret;
    }
}
