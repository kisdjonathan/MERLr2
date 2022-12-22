package AST.control;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.Tuple;
import AST.components.*;

public class For extends Control {

    /**
     * formatted as such:
     *  for iterationVariables in iterableVariable:
     *      body
     */
    public For(SyntaxNode iterationVariables, SyntaxNode iterableVariable, SyntaxNode body) {
        iterationVariables.setParent(this);
        for(SyntaxNode node : Tuple.asTuple(iterableVariable)){
            node.setParent(this);
            if(!(node instanceof Variable))
                throw new Error("for loop with non-variable variable");
            addChild(node);
        }
        setBase(iterableVariable, body);
    }

    @Override
    public BasicType getType() {
        return null;    //TODO
    }

    @Override
    public BasicType interpret() {
        return null;
    }
}
