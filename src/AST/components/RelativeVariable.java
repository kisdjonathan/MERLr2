package AST.components;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.InferredType;
import AST.baseTypes.Structure;

public class RelativeVariable extends Variable {

    public RelativeVariable(String name, SyntaxNode structParent) {
        super(name);
        addChild(structParent);
    }
    public RelativeVariable(String name, BasicType type, SyntaxNode structParent) {
        super(name, type);
        addChild(structParent);
    }
    public RelativeVariable(Variable original, SyntaxNode structParent) {
        this(original.getName(), original.getType(), structParent);
        addChild(structParent);
    }


    public RelativeVariable clone() {
        return new RelativeVariable(getName(), getType().clone(), getChild(0).clone());
    }

    public BasicType interpret() {
        Structure struct = (Structure)getChild(0).interpret();
        Variable var = struct.getVariable(getName());
        return var.interpret();
    }
}
