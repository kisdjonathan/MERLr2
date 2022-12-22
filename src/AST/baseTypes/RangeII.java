package AST.baseTypes;

import AST.abstractNode.SyntaxNode;

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
}
