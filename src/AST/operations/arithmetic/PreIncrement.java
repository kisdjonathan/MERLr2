package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.numerical.Float;
import AST.baseTypes.numerical.Int;
import AST.operations.UnaryOperator;

public class PreIncrement extends UnaryOperator {

    public PreIncrement(){}

    public PreIncrement(SyntaxNode origin) {
        addChild(origin);
    }

    static {
        addEvaluationOperation("preinc");
        setEvaluation("preinc",new Int(), new Int(), x -> {
            x.setValue(x.asInt() + 1);
            return x;
        });
        setEvaluation("preinc",new Float(), new Float(), x -> {
            x.setValue(x.asDouble() + 1);
            return x;
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