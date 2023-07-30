package AST.operator.binary.bitwise;

import AST.abstractNode.SyntaxNode;
import compiler.Assembly;
import interpreter.Value;

public class And extends SyntaxNode {
    public static final String literal = "and";


    public SyntaxNode emptyClone() {
        return new And();
    }

    public Value interpret() {
        return null;
    }

    public void compile(Assembly body) {

    }
}
