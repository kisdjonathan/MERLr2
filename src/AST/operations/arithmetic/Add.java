package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.advanced.Str;
import AST.baseTypes.numerical.Float;
import AST.baseTypes.numerical.Int;
import AST.operations.Operator;

public class Add extends Operator {
    public Add(){}
    public Add(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    static  {
        addEvaluationOperation("add");
        setBinaryEvaluation("add", new Int(), new Int(), new Int(), (x, y) -> new Int(x.asInt() + y.asInt()));
        setBinaryEvaluation("add", new Float(), new Int(), new Float(), (x, y) -> new Float(x.asDouble() + y.asDouble()));
        setBinaryEvaluation("add", new Int(), new Float(), new Float(), (x, y) -> new Float(x.asDouble() + y.asDouble()));
        setBinaryEvaluation("add", new Float(), new Float(), new Float(), (x, y) -> new Float(x.asDouble() + y.asDouble()));
        setBinaryEvaluation("add", new Str(), new Str(), new Str(), (x, y) -> new Str(x.getValue() + y.getValue()));
        setBinaryEvaluation("add", new Str(), new Int(), new Str(), (x, y) -> new Str(x.getValue() + y.getValue()));
        setBinaryEvaluation("add", new Int(), new Str(), new Str(), (x, y) -> new Str(x.getValue() + y.getValue()));
        setBinaryEvaluation("add", new Str(), new Float(), new Str(), (x, y) -> new Str(x.getValue() + y.getValue()));
        setBinaryEvaluation("add", new Float(), new Str(), new Str(), (x, y) -> new Str(x.getValue() + y.getValue()));
    }

    public String getName() {
        return "add";
    }

    public Add clone() {
        return new Add(getChild(0).clone(), getChild(1).clone());
    }
}
