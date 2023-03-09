package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.advanced.Str;
import AST.baseTypes.numerical.Char;
import AST.baseTypes.numerical.Float;
import AST.baseTypes.numerical.Int;
import AST.operations.BinaryOperator;

public class Round extends BinaryOperator {
    public static BasicType round(BasicType node1, BasicType node2) {
        return interpretEvaluate("round", node1.interpret(), node2.interpret());
    }

    public Round(){}
    public Round(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    static  {
        addEvaluationOperation("round");
        setEvaluation("round", new Char(),  new Int(),  new Char(), (x, y) -> new Char((short) (Math.round(x.asInt() * Math.pow(10, y.asInt()))/Math.pow(10, y.asInt()))));
        setEvaluation("round", new Int(),   new Int(),  new Int(), (x, y) -> new Int((int) (Math.round(x.asInt() * Math.pow(10, y.asInt()))/Math.pow(10, y.asInt()))));
        setEvaluation("round", new Float(), new Int(),  new Float(), (x, y) -> new Float(Math.round(x.asInt() * Math.pow(10, y.asInt()))/Math.pow(10, y.asInt())));
        setEvaluation("round", new Str(),   new Int(),  new Str(), (x, y) -> new Str(x.getValue() + y.getValue()));
        setEvaluation("round", new Str(),   new Int(),  new Str(), (x, y) -> new Str(x.getValue() + y.getValue()));
        setEvaluation("round", new Int(),   new Str(),  new Str(), (x, y) -> new Str(x.getValue() + y.getValue()));
        setEvaluation("round", new Str(),   new Float(),new Str(), (x, y) -> new Str(x.getValue() + y.getValue()));
        setEvaluation("round", new Float(), new Str(),  new Str(), (x, y) -> new Str(x.getValue() + y.getValue()));
    }

    public String getName() {
        return "add";
    }

    public Round clone() {
        return new Round(getChild(0).clone(), getChild(1).clone());
    }
}
