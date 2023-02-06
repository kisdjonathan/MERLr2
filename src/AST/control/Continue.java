package AST.control;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.flagTypes.ControlCode;
import AST.baseTypes.numerical.Int;

public class Continue extends SyntaxNode {
    public Continue() {
        addChild(new Int(1));
    }
    public Continue(SyntaxNode layers) {
        addChild(layers);
    }

    public BasicType getType() {
        return new ControlCode(ControlCode.BREAK);
    }

    public SyntaxNode clone() {
        return new Continue(getChild(0));
    }

    public BasicType interpret() {
        return new ControlCode(ControlCode.CONTINUE,((Int)getChild(0).interpret()).asInt());
    }
}
