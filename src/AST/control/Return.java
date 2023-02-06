package AST.control;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.VoidType;
import AST.baseTypes.flagTypes.ControlCode;
import AST.baseTypes.flagTypes.ReturnCode;
import AST.baseTypes.numerical.Int;

public class Return extends SyntaxNode {
    public Return() {
        addChild(new VoidType());
    }
    public Return(SyntaxNode value) {
        addChild(value);
    }

    public BasicType getType() {
        return new ReturnCode(ControlCode.RETURN, getChild(0).getType());
    }


    public SyntaxNode clone() {
        return new Return(getChild(0));
    }

    public BasicType interpret() {
        return new ReturnCode(ControlCode.RETURN, getChild(0).interpret());
    }
}
