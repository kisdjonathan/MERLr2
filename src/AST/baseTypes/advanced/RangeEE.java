package AST.baseTypes.advanced;


import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.numerical.Bool;
import AST.baseTypes.numerical.Int;
import AST.operations.BinaryOperator;
import AST.operations.Operator;
import AST.operations.arithmetic.Modulo;
import AST.operations.arithmetic.Subtract;
import AST.operations.comparison.*;

import java.util.Iterator;

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
    public RangeEE emptyClone(){
        return new RangeEE();
    }

    public boolean containsIndex(BasicType index) {
        return ((Bool) Greater.greater(index, (BasicType)getStart())).getValue() &&
                ((Bool) Lesser.lesser(index, (BasicType)getStop())).getValue() && (
                getStep() == null ||
                        ((Bool) Equal.equal(Modulo.modulo(Subtract.subtract(index, (BasicType)getStart()), (BasicType)getStep()), new Int(0))).getValue()
        );
    }
}
