package AST.baseTypes;

import AST.abstractNode.SyntaxNode;
import data.Usage;
import AST.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

//TODO complete
public abstract class BasicType extends SyntaxNode {
    /**
     * checks if the type of other equals this
     * the criteria for equality is defined as having homogenous properties
     * i.e. both have the same number of indices and the same fields
     **/
    public boolean typeEquals(SyntaxNode other) {
        BasicType otherType = other.getBaseType();
        return indexCount() == otherType.indexCount() &&
                ((getMethods() != null && otherType.getMethods() != null && new HashSet<>(getMethods()).equals(new HashSet<>(otherType.getMethods()))) ||
                        getMethods() == otherType.getMethods()) &&
                ((getMethods() != null && otherType.getMethods() != null && new HashSet<>(getFields()).equals(new HashSet<>(otherType.getFields()))) ||
                        getMethods() == otherType.getMethods());
    }
    /**
     * checks if the type of other is strictly equals this
     * the criteria for equality is defined as having the exact same properties
     * i.e. both have the same name, the same types, the same number of indices, the same fields
     **/
    public boolean typeStrictEquals(SyntaxNode other) {
        return other.getBaseType().getName().equals(getName());
    }
    /**
     * checks if the type of other is contained within this
     * the criteria for contains is defined as having all properties of other defined
     * i.e. if other is indexed, this must also be indexed, but if other is not, this still can be indexed
     **/
    public boolean typeContains(SyntaxNode other) {
        BasicType otherType = other.getBaseType();
        return indexCount() >= otherType.indexCount() &&
                ((getMethods() != null && otherType.getMethods() != null && new HashSet<>(getMethods()).containsAll(otherType.getMethods())) ||
                        getMethods() == otherType.getMethods()) &&
                ((getMethods() != null && otherType.getMethods() != null && new HashSet<>(getFields()).containsAll(otherType.getFields())) ||
                        getMethods() == otherType.getMethods());
    }


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
    public BasicType getBaseType(){
        return this;
    }

    /**
     * returns true if the type stores a number
     * numbers include int, float, and char
     */
    public boolean isNumeric() {
        return false;
    }

    /**
     * returns the maximum number of indices that this can contain
     * return in [0, int max]
     * returns 0 if not indexable
     **/
    public int indexCount() {
        return 0;
    }
    /**
     * returns the variable associated with index i
     * if none exists, returns null
     * used by compiler
     **/
    public SyntaxNode getIndex(int i) {
        return null;
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
        Collection<Function> potential = getMethods();
        Function closest = null;
        for(Function option : potential)
            if(option.equals(name)){
                if(option.typeStrictEquals(signature))
                    return option;
                if(closest == null && option.typeEquals(signature))
                    closest = option;
            }
        return closest;
    }

    /**
     * returns the size of this
     **/
    public abstract TypeSize getByteSize();

    /**
     * returns a new instance of the type with the value
     */
    public SyntaxNode newInstance(String s) {
        throw new Error("unable to create a new instance of " + getName() + "(from " + s + ")");
    }


    //TODO L allow custom suffixes
    private static HashSet<String> suffixes = new HashSet<>(Arrays.asList(
            "d", "ud", "ld", "uld",
            "c", "uc", "lc", "ulc",
            "f", "uf", "lf", "ulf",

            "cl", "ucl", "lcl", "ulcl", //dynamic string
            "cv", "ucv", "lcv", "ulcv", //static string

            "l", "v",
            "us", "s",
            "m", "um",
            "r"
    ));
    /**
     * used for parsing
     **/
    public static boolean isSuffix(String suffix) {
        return suffixes.contains(suffix);
    }
}
