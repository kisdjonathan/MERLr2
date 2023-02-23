package AST.operations.variable;

import AST.baseTypes.BasicType;
import AST.baseTypes.advanced.*;
import AST.baseTypes.numerical.Float;
import AST.baseTypes.numerical.Int;
import AST.operations.Operator;
import AST.abstractNode.SyntaxNode;
import AST.operations.UnaryOperator;
import AST.operations.arithmetic.Divide;
import AST.operations.arithmetic.Subtract;

public class Cardinal extends UnaryOperator {
    static {
        addEvaluationOperation("cardinal");
        setEvaluation("cardinal", new DynamicArray(),   new Int(),  x -> new Int(x.size()));
        setEvaluation("cardinal", new FixedArray(),     new Int(),  x -> new Int(x.size()));
        setEvaluation("cardinal", new Sequence(),       new Int(),  x -> new Int(x.size()));
        setEvaluation("cardinal", new Str(),            new Int(),  x -> new Int(x.length()));
        setEvaluation("cardinal", new UnorderedMap(),   new Int(),  x -> new Int(x.size()));
        setEvaluation("cardinal", new UnorderedSet(),   new Int(),  x -> new Int(x.size()));
        //TODO cardinal for ranges with exclusive boundaries
        setEvaluation("cardinal", new RangeEE(),        new Int(),  x ->
                (Int)Divide.divide(Subtract.subtract((BasicType)x.getStop(), (BasicType)x.getStart()), (BasicType)x.getStep()));
        setEvaluation("cardinal", new RangeEI(),        new Int(),  x ->
                (Int)Divide.divide(Subtract.subtract((BasicType)x.getStop(), (BasicType)x.getStart()), (BasicType)x.getStep()));
        setEvaluation("cardinal", new RangeIE(),        new Int(),  x ->
                (Int)Divide.divide(Subtract.subtract((BasicType)x.getStop(), (BasicType)x.getStart()), (BasicType)x.getStep()));
        setEvaluation("cardinal", new RangeII(),        new Int(),  x ->
                (Int)Divide.divide(Subtract.subtract((BasicType)x.getStop(), (BasicType)x.getStart()), (BasicType)x.getStep()));
    }

    public Cardinal(){}
    public Cardinal(SyntaxNode value) {
        addChild(value);
    }

    public String getName() {
        return "cardinal";
    }

    public Cardinal clone() {
        return new Cardinal(getChild(0).clone());
    }

    public BasicType getType() {
        return new Int();
    }
}
