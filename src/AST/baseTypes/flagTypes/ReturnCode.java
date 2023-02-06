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

    public ReturnCode(int choice, BasicType value) {
        super(choice);
        this.value = value;
    }
    public ReturnCode(int choice) {
        super(choice);
    }
}
