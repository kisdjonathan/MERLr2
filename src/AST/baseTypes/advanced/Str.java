package AST.baseTypes.advanced;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;

import java.util.List;

//TODO
public class Str extends Storage{
    private boolean extended = false;
    private String value;

    public Str() {
        value = "";
    }
    public Str(String val) {
        value = val;
    }

    public String getName() {
        return "str";
    }

    public BasicType getType() {
        return this;
    }

    public List<SyntaxNode> getFields() {
        //TODO length
        return null;
    }
    public SyntaxNode getField(String name) {
        //TODO length
        return null;
    }

    public boolean typeEquals(BasicType other) {
        return other instanceof Str;
    }
    public Str clone() {
        return new Str(value);
    }

    public String valueString() {
        return value;
    }
    public String toString() {
        return super.toString() + " \"" + value + "\"";
    }

    public String getValue() {return value;}

}
