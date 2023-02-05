package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.Float;
import AST.baseTypes.Int;
import AST.operations.UnaryOperator;

public class PreIncrement extends UnaryOperator {

    public PreIncrement(){}

    public PreIncrement(SyntaxNode origin) {
        addChild(origin);
    }

    static {
        setEvaluation(new Int(), new Int(), x -> {
            x.setValue(x.asInt() + 1);
            return x;
        });
        setEvaluation(new Float(), new Float(), x -> {
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
