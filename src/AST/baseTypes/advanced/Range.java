package AST.baseTypes.advanced;


import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.Function;
import AST.baseTypes.InferredType;
import AST.baseTypes.numerical.Int;
import AST.operations.BinaryOperator;
import AST.operations.arithmetic.Add;

import java.util.Iterator;
import java.util.List;

public abstract class Range extends Container {
    public Range(){}
    public Range(int start, int stop) {
        addChild(new Int(start));
        addChild(new Int(stop));
    }
    public Range(int start, int stop, int step) {
        addChild(new Int(start));
        addChild(new Int(stop));
        addChild(new Int(step));
    }

    public String getName() {
        return "range";
    }


    public SyntaxNode getStart() {
        return getChild(0);
    }
    public void setStart(SyntaxNode v) {
        v.setParent(this);
        setChild(0, v);
    }

    public SyntaxNode getStop() {
        return getChild(1);
    }
    public void setStop(SyntaxNode v) {
        v.setParent(this);
        setChild(1, v);
    }

    /**
     * null step means consecutive
     */
    public SyntaxNode getStep() {
        if(size() < 3)
            return null;
        return getChild(2);
    }
    public void setStep(SyntaxNode v) {
        v.setParent(this);
        setChild(2, v);
    }

    public void setStoredType(BasicType type) {
        if(getStoredType() instanceof InferredType) {
            getStart().setType(type);
            getStop().setType(type);
        }
        else
            throw new Error("unable to set stored type for Range to " + type);
    }
    public BasicType getStoredType() {
        return getStart().getType();
    }


    public abstract boolean containsIndex(BasicType index);
    public Iterator<SyntaxNode> asIterator() {
        return new RangeIterator(this);
    }

    //interpreter use only
    private static class RangeIterator implements Iterator<SyntaxNode> {
        private final Range storage;
        private BasicType index, step;

        public RangeIterator(Range storage) {
            if(storage.getStep() == null)
                throw new Error("can not iterate over continuous range " + storage);
            this.storage = storage;
            index = (BasicType)storage.getStart(); step = (BasicType)storage.getStep();
            if(!hasNext()) next();
        }

        public boolean hasNext() {
            return storage.containsIndex(index);
        }

        public SyntaxNode next() {
            BasicType original = index;
            index = Add.add(index, step);
            return original;
        }
    }

    public abstract Range emptyClone();
    public Range clone() {
        Range ret = emptyClone();
        ret.addChild(getStart().clone());
        ret.addChild(getStop().clone());
        ret.addChild(getStep().clone());
        return ret;
    }

    public BasicType interpret() {
        Range ret = emptyClone();
        for(SyntaxNode child: getChildren())
            ret.addChild(child.interpret());
        return ret;
    }

    public List<SyntaxNode> getFields() {
        return null;    //TODO
    }
    public SyntaxNode getField(String name) {
        return null;    //TODO
    }

    public List<Function> getMethods() {
        return null;    //TODO
    }
}
