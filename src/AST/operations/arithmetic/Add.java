package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.Int;
import AST.baseTypes.Float;
import AST.baseTypes.Str;
import AST.operations.BinaryOperator;

public class Add extends BinaryOperator {
    public Add(){}
    public Add(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    static {
        setEvaluation(new Int(), new Int(), new Int(), (x, y) -> new Int(x.asInt() + y.asInt()));
        setEvaluation(new Float(), new Int(), new Float(), (x, y) -> new Float(x.asDouble() + y.asDouble()));
        setEvaluation(new Int(), new Float(), new Float(), (x, y) -> new Float(x.asDouble() + y.asDouble()));
        setEvaluation(new Float(), new Float(), new Float(), (x, y) -> new Float(x.asDouble() + y.asDouble()));
        setEvaluation(new Str(), new Str(), new Str(), (x, y) -> new Str(x.getValue() + y.getValue()));
        setEvaluation(new Str(), new Int(), new Str(), (x, y) -> new Str(x.getValue() + y.getValue()));
        setEvaluation(new Int(), new Str(), new Str(), (x, y) -> new Str(x.getValue() + y.getValue()));
        setEvaluation(new Str(), new Float(), new Str(), (x, y) -> new Str(x.getValue() + y.getValue()));
        setEvaluation(new Float(), new Str(), new Str(), (x, y) -> new Str(x.getValue() + y.getValue()));
    }

    public String getName() {
        return "add";
    }

    public Add clone() {
        return new Add(getChild(0).clone(), getChild(1).clone());
    }
}
