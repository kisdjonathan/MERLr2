package AST.operations.variable;

import AST.baseTypes.BasicType;
import AST.operations.Operator;

public class In extends Operator {
    //TODO

    public String getName() {
        return "in";
    }

    @Override
    public BasicType getType() {
        return null;
    }

    @Override
    public BasicType interpret() {
        return null;
    }
}
