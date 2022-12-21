package AST.operations.control;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.Tuple;
import AST.components.*;

import java.util.ArrayList;
import java.util.List;

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
            variables.add((Variable) node.evaluated());   //TODO L potentially erroneous
        }
        setBase(iterableVariable, body);
    }
}
