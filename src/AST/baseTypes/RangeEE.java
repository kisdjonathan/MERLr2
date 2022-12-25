package AST.baseTypes;


import AST.abstractNode.SyntaxNode;

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

    public RangeEE clone() {
        RangeEE ret = new RangeEE();
        ret.setStart(getStart().clone());
        ret.setStop(getStop().clone());
        ret.setStep(getStep().clone());
        return ret;
    }
}
