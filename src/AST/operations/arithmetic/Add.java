package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.advanced.Str;
import AST.baseTypes.numerical.Char;
import AST.baseTypes.numerical.Float;
import AST.baseTypes.numerical.Int;
import AST.operations.BinaryOperator;

public class Add extends BinaryOperator {
    public static BasicType add(BasicType node1, BasicType node2) {
        return interpretEvaluate("add", node1.interpret(), node2.interpret());
    }

    public Add(){}
    public Add(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    static  {
        addEvaluationOperation("add");
        setEvaluation("add", new Char(), new Int(), new Int(), (x, y) -> new Int(x.asInt() + y.asInt()));
        setEvaluation("add", new Int(), new Char(), new Int(), (x, y) -> new Int(x.asInt() + y.asInt()));
        setEvaluation("add", new Int(), new Int(), new Int(), (x, y) -> new Int(x.asInt() + y.asInt()));
        setEvaluation("add", new Float(), new Int(), new Float(), (x, y) -> new Float(x.asDouble() + y.asDouble()));
        setEvaluation("add", new Int(), new Float(), new Float(), (x, y) -> new Float(x.asDouble() + y.asDouble()));
        setEvaluation("add", new Float(), new Float(), new Float(), (x, y) -> new Float(x.asDouble() + y.asDouble()));
        setEvaluation("add", new Str(), new Str(), new Str(), (x, y) -> new Str(x.getValue() + y.getValue()));
        setEvaluation("add", new Str(), new Int(), new Str(), (x, y) -> new Str(x.getValue() + y.getValue()));
        setEvaluation("add", new Int(), new Str(), new Str(), (x, y) -> new Str(x.getValue() + y.getValue()));
        setEvaluation("add", new Str(), new Float(), new Str(), (x, y) -> new Str(x.getValue() + y.getValue()));
        setEvaluation("add", new Float(), new Str(), new Str(), (x, y) -> new Str(x.getValue() + y.getValue()));
    }

    public String getName() {
        return "add";
    }

    public Add clone() {
        return new Add(getChild(0).clone(), getChild(1).clone());
    }
}
