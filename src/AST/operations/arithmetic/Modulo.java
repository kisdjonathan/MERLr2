package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.numerical.Float;
import AST.baseTypes.numerical.Int;
import AST.operations.Operator;

public class Modulo extends Operator {
    public Modulo(){}
    public Modulo(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    static  {
        addEvaluationOperation("mod");
        setBinaryEvaluation("mod", new Int(), new Int(), new Int(), (x, y) -> new Int(x.asInt() % y.asInt()));
        setBinaryEvaluation("mod", new Float(), new Int(), new Float(), (x, y) -> new Float(x.asDouble() % y.asDouble()));
        setBinaryEvaluation("mod", new Int(), new Float(), new Float(), (x, y) -> new Float(x.asDouble() % y.asDouble()));
        setBinaryEvaluation("mod", new Float(), new Float(), new Float(), (x, y) -> new Float(x.asDouble() % y.asDouble()));
    }

    public String getName() {
        return "mod";
    }

    public Modulo clone() {
        return new Modulo(getChild(0).clone(), getChild(1).clone());
    }
}
