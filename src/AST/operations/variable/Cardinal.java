package AST.operations.variable;

import baseTypes.Int;
import AST.abstractNode.SyntaxNode;
import operations.BuiltinOperation;

public class Cardinal extends BuiltinOperation {
    public Cardinal(){}
    public Cardinal(SyntaxNode value) {
        setOrigin(value);
    }

    public String getName() {
        return "cardinal";
    }

    public SyntaxNode getDeclaredType() {
        return new Int();
    }
}
