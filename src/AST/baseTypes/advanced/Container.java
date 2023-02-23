package AST.baseTypes.advanced;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.InferredType;
import AST.baseTypes.Structure;

import java.util.Iterator;

/**
 * types which represent a contained amount of values
 * all values are of the same type
 */
public abstract class Container extends Structure {
    private BasicType storedType = new InferredType();
    public void setStoredType(BasicType type) {
        storedType = type;
    }
    public BasicType getStoredType() {
        return storedType;
    }


    //Interpreter function
    public abstract Iterator<SyntaxNode> asIterator();
}
