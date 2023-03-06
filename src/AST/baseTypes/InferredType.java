package AST.baseTypes;

import AST.abstractNode.SyntaxNode;

import java.util.*;

public class InferredType extends BasicType{
    public String getName() {
        return "inferred";
    }

    //TODO


    public InferredType clone() {
        return (InferredType)(new InferredType().acceptFields(getFieldClones()));
    }
    
    public boolean typeEquals(BasicType other) {
        return true;
    }
    public boolean equals(Object other) {
        return other instanceof BasicType;
    }
}
