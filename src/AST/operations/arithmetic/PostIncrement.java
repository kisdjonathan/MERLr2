package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.numerical.Bool;
import AST.baseTypes.numerical.Float;
import AST.baseTypes.numerical.Int;
import AST.operations.UnaryOperator;

public class PostIncrement extends UnaryOperator {

    public PostIncrement(){}

    public PostIncrement(SyntaxNode origin) {
        addChild(origin);
    }

    static {
        addEvaluationOperation("postinc");
        setRawEvaluation("postinc", new Int(), new Bool(), (x) -> {
            BasicType val = x.getType();
            x.setType(new Int(((Int)x.getType()).getValue() - 1));
            return val;
        });
        setRawEvaluation("postinc", new Float(), new Float(), (x) -> {
            BasicType val = x.getType();
            x.setType(new Float(((Float)x.getType()).getValue() - 1));
            return val;
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
