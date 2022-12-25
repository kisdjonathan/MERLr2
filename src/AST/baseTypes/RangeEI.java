package AST.baseTypes;


import AST.abstractNode.SyntaxNode;

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

    public RangeEI clone() {
        RangeEI ret = new RangeEI();
        ret.setStart(getStart().clone());
        ret.setStop(getStop().clone());
        ret.setStep(getStep().clone());
        return ret;
    }
}
