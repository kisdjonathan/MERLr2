package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.numerical.Bool;
import AST.baseTypes.numerical.Float;
import AST.baseTypes.numerical.Int;
import AST.operations.UnaryOperator;

public class PostDecrement extends UnaryOperator {

    public PostDecrement(){}

    public PostDecrement(SyntaxNode origin) {
        addChild(origin);
    }

    static {
        addEvaluationOperation("postdec");
        setRawEvaluation("postdec", new Int(), new Bool(), (x) -> {
            BasicType val = x.getType();
            x.setType(new Int(((Int)x.getType()).getValue() - 1));
            return val;
        });
        setRawEvaluation("postdec", new Float(), new Float(), (x) -> {
            BasicType val = x.getType();
            x.setType(new Float(((Float)x.getType()).getValue() - 1));
            return val;
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
