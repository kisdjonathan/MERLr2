package AST.control;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.Bool;
import AST.baseTypes.VoidType;
import AST.components.Locality;
import AST.components.Variable;
import AST.operations.variable.In;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public abstract class Control extends Locality {
    protected class Node extends Locality {
        public final SyntaxNode condition, body;
        public Node executionFalse = null, executionTrue = null;

        public Node(SyntaxNode condition, SyntaxNode body) {
            body.setParent(this);
            this.condition = condition;
            this.body = body;
        }

        public void setParent(SyntaxNode parent) {
            super.setParent(parent);
            condition.setParent(parent);
        }

        public BasicType getType() {
            return body.getType();
        }

        public BasicType interpret() {
            BasicType success = condition.interpret();
            if(success.equals(new Bool(true))) {    //TODO switch implementation
                BasicType val = body.interpret();
                if(executionTrue != null)
                    return executionTrue.interpret();
                else
                    return val;
            }
             else if(executionFalse != null)
                 return executionFalse.interpret();
             else
                 return new VoidType();
        }

        public Node clone() {
            Node ret = new Node(condition.clone(), body.clone());
            ret.executionFalse = executionFalse == null ? null : executionFalse.clone();
            ret.executionTrue = executionTrue == null ? null : executionTrue.clone();
            return ret;
        }
    }
    
    private Node base;
    private final Set<Node> nodes = new HashSet<>();

    /**
     * pass null for condition for always true
     */
    public void addElse(SyntaxNode condition, SyntaxNode body) {
        if(condition == null) condition = new Bool(true);
        for(Node n : nodes)
            if(n.executionFalse == null) {
                Node newNode = new Node(condition, body);
                n.executionFalse = newNode;
                newNode.setParent(n);
                nodes.add(newNode);
            }
    }
    public void addNelse(SyntaxNode condition, SyntaxNode body) {
        if(condition == null) condition = new Bool(true);
        for(Node n : nodes)
            if(n.executionTrue == null){
                Node newNode = new Node(condition, body);
                n.executionTrue = newNode;
                newNode.setParent(n);
                nodes.add(newNode);
            }
    }
    public void addChild(String chainName, SyntaxNode condition, SyntaxNode body) {
        switch (chainName) {
            case "else":
                addElse(condition,body);
            case "nelse":
                addNelse(condition, body);
            default:
                throw new Error("no chain by the name of " + chainName);
        }
    }

    public Node getBase() {
        return base;
    }
    public void setBase(SyntaxNode condition, SyntaxNode body) {
        this.base = new Node(condition, body);
        nodes.add(base);
    }
    protected void setBase(Node node) {
        this.base = node;
        if(node == null)
            return;
        Stack<Node> nodes = new Stack<>();
        nodes.add(node);
        while(!nodes.empty()) {
            node = nodes.pop();
            if(this.nodes.add(node)) {
                nodes.add(node);
                if(node.executionTrue != null)
                    nodes.add(node.executionTrue);
                if(node.executionFalse != null)
                    nodes.add(node.executionFalse);
            }
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
