package AST.baseTypes;


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
    //TODO remaining constructors for floats and SyntaxNodes

    public SyntaxNode getIndex(int i) {
        return new Add(getStart(), new Multiply(new Int(i + 1), getStep()));
    }
}
