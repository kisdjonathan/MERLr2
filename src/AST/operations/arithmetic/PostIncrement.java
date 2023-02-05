package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.Float;
import AST.baseTypes.Int;
import AST.operations.UnaryOperator;

public class PostIncrement extends UnaryOperator {

    public PostIncrement(){}

    public PostIncrement(SyntaxNode origin) {
        addChild(origin);
    }

    static {
        setEvaluation(new Int(), new Int(), x -> {
            Int result = x.clone();
            x.setValue(x.asInt() + 1);
            return result;
        });
        setEvaluation(new Float(), new Float(), x -> {
            Float result = x.clone();
            x.setValue(x.asDouble() + 1);
            return result;
        });
    }

    @Override
    public SyntaxNode clone() {
        return new PostIncrement(getChild(0).clone());
    }

    @Override
    public String getName() {
        return "postinc";
    }
}
