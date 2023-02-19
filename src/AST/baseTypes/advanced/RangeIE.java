package AST.baseTypes.advanced;


import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.numerical.Bool;
import AST.baseTypes.numerical.Int;
import AST.operations.BinaryOperator;
import AST.operations.arithmetic.Modulo;
import AST.operations.arithmetic.Subtract;
import AST.operations.comparison.Equal;
import AST.operations.comparison.Lesser;
import AST.operations.comparison.NoGreater;
import AST.operations.comparison.NoLesser;

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
    public RangeIE emptyClone(){
        return new RangeIE();
    }

    public boolean containsIndex(BasicType index) {
        return ((Bool) Lesser.lesser(index, (BasicType)getStop())).getValue() &&
                ((Bool) NoLesser.noLesser(index, (BasicType)getStart())).getValue() && (
                getStep() == null ||
                        ((Bool) Equal.equal(Modulo.modulo(Subtract.subtract(index, (BasicType)getStart()), (BasicType)getStep()), new Int(0))).getValue()
        );
    }
}
