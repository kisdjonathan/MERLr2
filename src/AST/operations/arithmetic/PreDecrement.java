package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.numerical.Bool;
import AST.baseTypes.numerical.Float;
import AST.baseTypes.numerical.Int;
import AST.operations.UnaryOperator;

public class PreDecrement extends UnaryOperator {

    public PreDecrement(){}

    public PreDecrement(SyntaxNode origin) {
        addChild(origin);
    }

    static {
        addEvaluationOperation("predec");
        setRawEvaluation("predec", new Int(), new Bool(), (x) -> {
            x.setType(new Int(((Int)x.getType()).getValue() - 1));
            return x.interpret();
        });
        setRawEvaluation("predec", new Float(), new Float(), (x) -> {
            x.setType(new Float(((Float)x.getType()).getValue() - 1));
            return x.interpret();
        });
    }

    @Override
    public SyntaxNode clone() {
        return new PreDecrement(getChild(0).clone());
    }

    @Override
    public String getName() {
        return "predec";
    }
}
