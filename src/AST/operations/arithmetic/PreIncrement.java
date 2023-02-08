package AST.operations.arithmetic;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.Tuple;
import AST.baseTypes.numerical.Float;
import AST.baseTypes.numerical.Int;
import AST.operations.Operator;

public class PreIncrement extends Operator {
    public static BasicType increment(BasicType node) {
        return interpretEvaluate("preinc", new Tuple(node.interpret()));
    }

    public PreIncrement(){}

    public PreIncrement(SyntaxNode origin) {
        addChild(origin);
    }

    static {
        addEvaluationOperation("preinc");
        setUnaryEvaluation("preinc",new Int(), new Int(), x -> {
            x.setValue(x.asInt() + 1);
            return x;
        });
        setUnaryEvaluation("preinc",new Float(), new Float(), x -> {
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
