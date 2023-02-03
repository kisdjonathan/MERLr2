package AST.control;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.Bool;
import AST.baseTypes.VoidType;
import AST.components.Locality;
import AST.components.Variable;
import AST.operations.variable.In;

import java.util.*;

public abstract class Control extends Locality {
    protected class Node extends Locality {
        //[0]:condition, [1]: body, [2]: execFalse, [3]: execTrue
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
            if(success.equals(new Bool(true))) {    //TODO implementation as switch statement
                BasicType val = getChild(1).interpret();
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
    }

    //private final List<Node> nodes = new ArrayList<>();

    /**
     * pass null for condition for always true
     */
    public void addElse(SyntaxNode condition, SyntaxNode body) {
        if(condition == null) condition = new Bool(true);
        for(int i = size()-1; i >= 0; --i)
            if(getChild(i).executionFalse < 0) {
                getChild(i).executionFalse = size();
            }
        Node newNode = new Node(condition, body);
        addChild(newNode);
    }
    public void addNelse(SyntaxNode condition, SyntaxNode body) {
        if(condition == null) condition = new Bool(true);
        for(int i = size()-1; i >= 0; --i)
            if(getChild(i).executionTrue < 0) {
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

    public static Control decode(String controlName, SyntaxNode condition, SyntaxNode body) {
        switch (controlName) {
            case "if":
                return new If(condition, body);
            case "repeat":
                return new Repeat(condition, body);
            case "while":
                return new While(condition, body);
            case "for":
                if(condition instanceof In icondition)
                    return new For(icondition.getChild(0), icondition.getChild(1), body);
            default:
                throw new Error("no control by the name " + controlName);
        }
    }
}
