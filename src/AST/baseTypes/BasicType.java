package AST.baseTypes;

import AST.abstractNode.SyntaxNode;
import AST.components.Signature;

import java.util.Collection;

//TODO complete
public abstract class BasicType extends SyntaxNode {
    /**
     * returns the name of the type
     * comparing names will not suffice to check for equality
     **/
    public abstract String getName();

    /**
     * implements the getUsage of SyntaxNode
     */
    public String getUse(){
        return "Type";
    }

    /**
     * implements the getUsage of SyntaxNode
     */
    public BasicType getType(){
        return this;
    }

    public BasicType interpret() {
        return this;
    }

    /**
     * returns all fields of this
     * if there are no fields, returns null
     **/
    public Collection<SyntaxNode> getFields() {
        return null;
    }
    /**
     * returns the variable associated with field name
     * if none exists, returns null
     * used by compiler
     **/
    public SyntaxNode getField(String name) {
        return null;
    }

    /**
     * returns all methods of this
     * if there are no methods, returns null
     **/
    public Collection<Function> getMethods() {
        return null;
    }
    /**
     * returns the function associated with method signature
     * if none exists, returns null
     * used by compiler
     **/
    public Function getMethod(String name, Signature signature) {
        return null;    //TODO
    }

    public abstract boolean typeEquals(BasicType obj);

    //TODO heap and stack allocation functions/fields

    public String valueString() {
        return toString();
    }
    public String toString() {
        return getName();
    }
}
