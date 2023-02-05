package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.Float;
import AST.baseTypes.Int;
import AST.operations.UnaryOperator;

public class PostDecrement extends UnaryOperator {

    public PostDecrement(){}

    public PostDecrement(SyntaxNode origin) {
        addChild(origin);
    }

    static {
        setEvaluation(new Int(), new Int(), x -> {
            Int result = x.clone();
            x.setValue(x.asInt() - 1);
            return result;
        });
        setEvaluation(new Float(), new Float(), x -> {
            Float result = x.clone();
            x.setValue(x.asDouble() - 1);
            return result;
        });
    }

    @Override
    public SyntaxNode clone() {
        return new PostDecrement(getChild(0).clone());
    }

    @Override
    public String getName() {
        return "postdec";
    }
}
