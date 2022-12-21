package AST.operations.variable;

import AST.operations.Operator;
import AST.abstractNode.SyntaxNode;

public class Copy extends Operator {
    public Copy(SyntaxNode v) {
        setOrigin(v);
    }

    public String getName() {
        return "copy";
    }
}
