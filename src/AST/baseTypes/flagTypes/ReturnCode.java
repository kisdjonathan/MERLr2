package AST.baseTypes.flagTypes;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.VoidType;

public class ReturnCode extends ControlCode{
    private BasicType value = new VoidType();
    public BasicType getValue() {
        return value;
    }
    public void setValue(BasicType value) {
        this.value = value;
    }

    public ReturnCode(BasicType value) {
        super(ControlCode.RETURN);
        this.value = value;
    }
    public ReturnCode() {
        super(ControlCode.RETURN);
    }

    public ControlCode clone() {
        return new ReturnCode(value.clone());
    }
}
