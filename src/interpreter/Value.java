package interpreter;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;

import java.util.Map;

//TODO
public class Value {
    private SyntaxNode type;
    private Map<String, Value> fields;
    private BasicType value;

    public Value(){}
    public Value(SyntaxNode type) {
        setType(type);
    }
    public Value(BasicType value) {
        this.value = value;
    }

    public BasicType getValue() {
        return value;
    }

    /**
     * sets value to a literal but does not change this' type
     */
    public void setValue(BasicType value) {
        this.value = value;
    }

    public SyntaxNode getType(){
        return type;
    }
    public void setType(SyntaxNode type) {
        this.type = type;
        fields.clear();
        for(SyntaxNode field : type.getBaseType().getFields()) {
            fields.put(field.getName(), new Value(field));
        }
    }
}
