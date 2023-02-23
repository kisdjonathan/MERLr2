package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.numerical.Bool;
import AST.baseTypes.numerical.Float;
import AST.baseTypes.numerical.Int;
import AST.operations.UnaryOperator;

public class PreIncrement extends UnaryOperator {
    public static BasicType increment(BasicType node) {
        return interpretEvaluate("preinc", node.interpret());
    }

    public PreIncrement(){}

    public PreIncrement(SyntaxNode origin) {
        addChild(origin);
    }

    static {
        addEvaluationOperation("preinc");
        setRawEvaluation("preinc", new Int(), new Bool(), (x) -> {
            x.setType(new Int(((Int)x.getType()).getValue() + 1));
            return x.interpret();
        });
        setRawEvaluation("preinc", new Float(), new Float(), (x) -> {
            x.setType(new Float(((Float)x.getType()).getValue() + 1));
            return x.interpret();
        });
    }

    @Override
    public SyntaxNode clone() {
        return new PreIncrement(getChild(0).clone());
    }

    @Override
    public String getName() {
        return "preinc";
    }
}
