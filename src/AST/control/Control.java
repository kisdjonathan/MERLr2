package AST.control;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.flagTypes.ControlCode;
import AST.baseTypes.numerical.Bool;
import AST.baseTypes.VoidType;
import AST.components.Locality;
import AST.operations.variable.In;

public abstract class Control extends Locality {
    protected SyntaxNode conditionControl = new Bool(true);
    protected class Node extends Locality {
        //TODO condition does not have to be bool, but any value, and the loop will execute while the two are equal
        //-1: unassigned, -2: always stop
        protected int executionFalse = -1, executionTrue = -1;

        public Node(SyntaxNode condition, SyntaxNode body) {
            addChild(condition);
            addChild(body);
        }

        private Node(){}

        public BasicType getType() {
            return getChild(1).getType();
        }

        public BasicType interpret() {
            BasicType success = getChild(0).interpret();
            if(success.equals(conditionControl)) {
                BasicType val = getChild(1).interpret();
                if(val instanceof ControlCode)
                    return val;
                if(executionTrue >= 0)
                    return getParent().getChild(executionTrue).interpret();
                else
                    return val;
            }
             else if(executionFalse >= 0)
                 return getParent().getChild(executionFalse).interpret();
             else
                 return new VoidType();
        }

        public Node clone() {
            Node ret = new Node();
            for(SyntaxNode child : getChildren())
                ret.addChild(child.clone());
            ret.executionTrue = executionTrue;
            ret.executionFalse = executionFalse;
            return ret;
        }

        public String toString() {
            return getChild(0) + ":" + getChild(1);
        }
    }

    //private final List<Node> nodes = new ArrayList<>();

    /**
     * pass null for condition for always true
     */
    public void addElse(SyntaxNode condition, SyntaxNode body) {
        if(condition == null) condition = new Bool(true);
        for(int i = size()-1; i >= 0; --i)
            if(getChild(i).executionFalse == -1) {
                getChild(i).executionFalse = size();
            }
        Node newNode = new Node(condition, body);
        addChild(newNode);
    }
    public void addNelse(SyntaxNode condition, SyntaxNode body) {
        if(condition == null) condition = new Bool(true);
        for(int i = size()-1; i >= 0; --i)
            if(getChild(i).executionTrue == -1) {
                getChild(i).executionTrue = size();
            }
        Node newNode = new Node(condition, body);
        addChild(newNode);
    }
    public void addChild(String chainName, SyntaxNode condition, SyntaxNode body) {
        switch (chainName) {
            case "else" -> addElse(condition, body);
            case "nelse" -> addNelse(condition, body);
            default -> throw new Error("no chain by the name of " + chainName);
        }
    }
    public Node getChild(int i) {
        return (Node) super.getChild(i);
    }

    public Node getBase() {
        return getChild(0);
    }
    public void setBase(SyntaxNode condition, SyntaxNode body) {
        Node base = new Node(condition, body);
        setBase(base);
    }
    protected void setBase(Node node) {
        if(size() > 1) {
            throw new Error("can not modify base after conditions have been aded");
        }
        if(node == null) {
            removeChild(0);
            return;
        }
        if (size() == 0)
            addChild(node);
        else
            setChild(0, node);
    }
}
