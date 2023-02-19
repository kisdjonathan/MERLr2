package AST.baseTypes.advanced;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.flagTypes.ControlCode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * types which store specific values
 */
public abstract class Storage extends Container {
    public String getName() {
        return "storage";
    }

    public List<SyntaxNode> getFields() {
        return null;    //TODO
    }

    public SyntaxNode getField(String name) {
        return null;    //TODO
    }

    public BasicType interpret(){
        Storage ret = this.emptyClone();
        for(SyntaxNode child : getChildren()) {
            BasicType value = child.interpret();
            if(value instanceof ControlCode)
                return value;
            ret.addChild(value);
        }
        return ret;
    }


    private static class SequenceIterator implements Iterator<SyntaxNode> {
        private final Storage storage;
        private int index = 0;

        public SequenceIterator(Storage storage) {
            this.storage = storage;
        }

        public boolean hasNext() {
            return index < storage.size();
        }

        public SyntaxNode next() {
            return storage.getChild(index++);
        }
    }
    //Interpreter function
    public Iterator<SyntaxNode> asIterator() {
        return new SequenceIterator(this);
    }

    public abstract Storage emptyClone();

    public String valueString() {
        List<String> values = new ArrayList<>();
        for(SyntaxNode child : getChildren())
            values.add(child.getType().valueString());
        return values.toString();
    }
    public String toString() {
        return getName() + " " + getChildren();
    }
}
