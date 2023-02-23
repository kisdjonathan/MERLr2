package AST.baseTypes.advanced;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.numerical.Char;

import java.util.ArrayList;
import java.util.Iterator;
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


    private static class CharSequence extends Sequence{
        private final String strval;
        public CharSequence(String s) {
            strval = s;
        }

        public String getStrval(){
            return strval;
        }

        public int size() {
            return strval.length();
        }

        public void setChild(int index, SyntaxNode value) {
            throw new Error("unable to set child of CharSequence " + this);
        }
        public Char getChild(int index) {
            return new Char((short) strval.charAt(index));
        }
        public List<SyntaxNode> getChildren() {
            ArrayList<SyntaxNode> ret = new ArrayList<>();
            for(char c : strval.toCharArray())
                ret.add(new Char((short) c));
            return ret;
        }
        public CharSequence clone() {
            CharSequence ret = new CharSequence(strval);
            return ret;
        }

        public boolean typeEquals(BasicType other) {
            return other instanceof CharSequence daother;   //TODO or simply sequence
        }
    }
    public Iterator<SyntaxNode> asIterator() {
        return (new CharSequence(value)).asIterator();
    }

    public boolean typeEquals(BasicType other) {
        return other instanceof Str;
    }
    public Str clone() {
        return new Str(value);
    }
    public Str emptyClone() {
        return new Str();
    }

    public BasicType interpret() {
        return this;
    }


    public int length(){
        return value.length();
    }
    public String valueString() {
        return value;
    }
    public String toString() {
        return super.toString() + " \"" + value + "\"";
    }

    public String getValue() {return value;}

}
