package AST.components;

import AST.abstractNode.SyntaxNode;

import java.util.HashMap;
import java.util.Map;

public class Locality extends SyntaxNode {
    private final Map<String, Variable> variables = new HashMap<>();

    public Variable getVariable(String name) {
    }

    public void unifyVariables(Map<String, Variable> variables) {

    }
}
