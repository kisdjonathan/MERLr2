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
        private boolean executionFalse = false, executionTrue = false;

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
            if(success.equals(new Bool(true))) {    //TODO switch implementation
                BasicType val = getChild(1).interpret();
                if(executionTrue)
                    return getChild(2).interpret();
                else
                    return val;
            }
             else if(executionFalse && executionTrue)
                 return getChild(3).interpret();
             else if(executionFalse)
                 return getChild(2).interpret();
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

    private final List<Node> nodes = new ArrayList<>();

    /**
     * pass null for condition for always true
     */
    public void addElse(SyntaxNode condition, SyntaxNode body) {
        if(condition == null) condition = new Bool(true);
        for(int i = nodes.size()-1; i >= 0; --i)
            if(!nodes.get(i).executionFalse) {
                Node newNode = new Node(condition, body);
                nodes.get(i).addChild(newNode);
                nodes.get(i).executionFalse = true;
            }
    }
    public void addNelse(SyntaxNode condition, SyntaxNode body) {
        if(condition == null) condition = new Bool(true);
        for(int i = nodes.size()-1; i >= 0; --i)
            if(!nodes.get(i).executionTrue) {
                Node newNode = new Node(condition, body);
                nodes.get(i).addChild(2, newNode);
                nodes.get(i).executionTrue = true;
            }
    }
    public void addChild(String chainName, SyntaxNode condition, SyntaxNode body) {
        switch (chainName) {
            case "else" -> addElse(condition, body);
            case "nelse" -> addNelse(condition, body);
            default -> throw new Error("no chain by the name of " + chainName);
        }
    }

    public Node getBase() {
        return (Node)getChild(0);
    }
    public void setBase(SyntaxNode condition, SyntaxNode body) {
        Node base = new Node(condition, body);
        addChild(base);
        nodes.add(base);
    }
    protected void setBase(Node node) {
        if(node == null) {
            removeChild(0);
            return;
        }
        setChild(0, node);
        Stack<Node> nodes = new Stack<>();
        nodes.add(node);
        while(!nodes.empty()) {
            node = nodes.pop();
            this.nodes.add(node);
            for(int i = 2; i < node.size(); ++i)
                nodes.add((Node)node.getChild(i));
        }
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
