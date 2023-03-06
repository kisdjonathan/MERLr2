package AST.baseTypes.advanced;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.*;
import AST.baseTypes.flagTypes.ControlCode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * types which store specific values
 */
public abstract class Storage extends Container {
//    private class RemoveValue extends ReturnCode{
//        public RemoveValue(SyntaxNode removed) {
//            addChild(removed);
//        }
//
//        public BasicType getValue() {
//            BasicType interpretedValue = getChild(0).interpret();
//            for(int i = 0; i < Storage.this.size(); ++i) {
//                if(Storage.this.getChild(i).equals(interpretedValue)) {
//                    return Storage.this.removeChild(i).interpret();
//                }
//            }
//            return new VoidType();
//        }
//
//        public RemoveValue clone() {
//            return new RemoveValue(getChild(0).clone());
//        }
//    }
//    private class StorageSize extends Variable{
//        public StorageSize() {
//            super("size");
//        }
//
//        public BasicType getType() {
//            return new Int(Storage.this.size());
//        }
//
//        public BasicType interpret() {
//            return new Int(Storage.this.size());
//        }
//
//        public StorageSize clone() {
//            return new StorageSize();
//        }
//    }
//
//    public Storage(){
//        //interpreter
//        //TODO make this the -= overload
//        Signature remove = new Signature("remove_value");
//        putField(remove.getName(), remove);
//        {
//            Variable removed = new Variable("removed");
//            Function removal = new Function(Tuple.asTuple(removed), Tuple.asTuple(new InferredType()));
//            removal.addChild(new RemoveValue(removed));
//            //removal.unifyVariables(variables);
//            remove.addOverload(removal);
//        }
//
//        Variable sizeVariable = new StorageSize();
//        putField(sizeVariable.getName(), sizeVariable);
//    }

    public String getName() {
        return "storage";
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

    public Storage clone() {
        Storage ret = emptyClone();
        for(SyntaxNode child : getChildren())
            ret.addChild(child);
        ret.getFields().putAll(getFieldClones());
        return ret;
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
