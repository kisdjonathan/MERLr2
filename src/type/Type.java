package type;

import AST.abstractNode.SyntaxNode;
import compiler.Assembly;
import interpreter.Value;

public abstract class Type extends SyntaxNode {
    public static boolean typeEquals(Type first, Type second) {
        return (first.isInferred() || second.isInferred()) ||
                (first.typeEquals(second) || second.typeEquals(first));
    }

    /**
     * returns the name of the type
     * comparing names will not suffice to check for equality
     **/
    public abstract String getName();
    public Type getType(){
        return this;
    }


    /**
     * true if the type is currently undefined to some extent
     * used with InferredType
     */
    public boolean isInferred() {
        return false;
    }


    /**
     * true if this is obj
     */
    public abstract boolean typeEquals(Type obj);


    /**
     * compiler
     */
    public abstract Assembly getAssembly();


    public String valueString() {
        return toString();
    }
    public String toString() {
        return getName();
    }



    public Value interpret() {
        return new Value(this);
    }
}
