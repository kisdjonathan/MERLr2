package AST.baseTypes.advanced;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.numerical.Int;

public class DiscreteRange extends RangeII{
    public DiscreteRange(){}
    public DiscreteRange(int start, int stop){
        super(start, stop, 1);
    }

    public SyntaxNode getStep() {
        if(size() < 3)
            return new Int(1);
        return getChild(2);
    }

    public boolean typeEquals(BasicType other) {
        return other instanceof DiscreteRange;
    }
    public DiscreteRange emptyClone(){
        return new DiscreteRange();
    }
}
