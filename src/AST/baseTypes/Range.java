package AST.baseTypes;


import AST.abstractNode.SyntaxNode;

import java.util.List;

public abstract class Range extends BasicType {
    private SyntaxNode start = new Int(0), stop, step = new Int(1);   //TODO compare between values of literals

    public Range(){}
    public Range(int start, int stop) {
        this.start = new Int(start);
        this.stop = new Int(stop);
    }
    public Range(int start, int stop, int step) {
        this.start = new Int(start);
        this.stop = new Int(stop);
        this.step = new Int(step);
    }

    public String getName() {
        return "range";
    }


    public SyntaxNode getStart() {
        return start;
    }
    public void setStart(SyntaxNode v) {
        v.setParent(this);
        start = v;
    }

    public SyntaxNode getStop() {
        return stop;
    }
    public void setStop(SyntaxNode v) {
        v.setParent(this);
        stop = v;
    }

    /**
     * null step means consecutive
     */
    public SyntaxNode getStep() {
        return step;
    }
    public void setStep(SyntaxNode v) {
        v.setParent(this);
        step = v;
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
