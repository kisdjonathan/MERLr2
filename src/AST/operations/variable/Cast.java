package AST.operations.variable;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.InferredType;
import AST.components.Locality;
import AST.components.Variable;
import AST.operations.Operator;

/**
 * Cast represents cases of conversions where from != to and from->to is not overridden
 */
public class Cast extends Operator {
    public Cast(){}
    public Cast(SyntaxNode from, SyntaxNode to) {
        addChild(from); addChild(to);
    }

    public String getName() {
        return "cast";
    }

    public Cast clone() {
        return new Cast(getChild(0).clone(), getChild(1).clone());
    }

    public BasicType getType() {
        return getChild(1).getType();
    }

    public void unifyVariables(Locality variables) {
        //TODO v this doesn't seem right
        super.unifyVariables(variables);

        //TODO user-defined casting
        getChild(0).setType(getType());
    }

    public BasicType interpret() {
        return getChild(0).interpret();
    }
}
