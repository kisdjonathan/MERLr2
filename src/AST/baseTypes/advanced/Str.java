package AST.baseTypes.advanced;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.numerical.Char;

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

    private class CharSequence extends Sequence{
        private final String strval;
        public CharSequence(String s) {
            strval = s;
        }

        public String getStrval(){
            return strval;
        }

        public Char getChild(int index) {
            return new Char((short) strval.charAt(index));
        }
        public CharSequence clone() {
            CharSequence ret = new CharSequence(strval);
            return ret;
        }

        public boolean typeEquals(BasicType other) {
            return other instanceof CharSequence daother;   //TODO or simply sequence
        }
    }
    public Sequence asSequence() {
        return new CharSequence(value);
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

    public String valueString() {
        return value;
    }
    public String toString() {
        return super.toString() + " \"" + value + "\"";
    }

    public String getValue() {return value;}

}
