package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.numerical.Float;
import AST.baseTypes.numerical.Int;
import AST.operations.BinaryOperator;

public class Modulo extends BinaryOperator {
    public static BasicType modulo(BasicType node1, BasicType node2) {
        return interpretEvaluate("mod", node1.interpret(), node2.interpret());
    }

    public Modulo(){}
    public Modulo(SyntaxNode origin, SyntaxNode vector) {
        super(origin, vector);
    }

    static  {
        addEvaluationOperation("mod");
        setEvaluation("mod", new Int(), new Int(), new Int(), (x, y) -> new Int(x.asInt() % y.asInt()));
        setEvaluation("mod", new Float(), new Int(), new Float(), (x, y) -> new Float(x.asDouble() % y.asDouble()));
        setEvaluation("mod", new Int(), new Float(), new Float(), (x, y) -> new Float(x.asDouble() % y.asDouble()));
        setEvaluation("mod", new Float(), new Float(), new Float(), (x, y) -> new Float(x.asDouble() % y.asDouble()));
    }

    public String getName() {
        return "mod";
    }

    public Modulo clone() {
        return new Modulo(getChild(0).clone(), getChild(1).clone());
    }
}
