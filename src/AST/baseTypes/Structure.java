package AST.baseTypes;

import AST.abstractNode.SyntaxNode;
import AST.components.Variable;

import java.util.HashMap;
import java.util.Map;

//TODO
public class Structure extends BasicType {
    private Map<String, BasicType> fields = new HashMap<>();

    public Structure(SyntaxNode variable, SyntaxNode body) {
        addChild(variable);
        addChild(body);
    }

    @Override
    public String getName() {
        return null;
    }

    public Variable asVariable() {
        return null;    //TODO
    }

    public boolean typeEquals(BasicType other) {
        return other instanceof Structure;
    }
    public Structure clone() {
        return new Structure(getChild(0), getChild(1));
    }
}
