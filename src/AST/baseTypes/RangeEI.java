package AST.baseTypes;


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
    //TODO remaining constructors for floats and SyntaxNodes

    public SyntaxNode getIndex(int i) {
        return new Add(getStart(), new Multiply(new Int(i + 1), getStep()));
    }
}
