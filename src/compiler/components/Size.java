package compiler.components;

public class Size extends AssemblyArgument{
    private boolean constant;
    private int constantValue;
    private AssemblyArgument variableValue;

    public Size(int sz) {
        constant = true;
        constantValue = sz;
    }
    public Size(AssemblyArgument sz) {
        constant = false;
        variableValue = sz;
    }
    //TODO
}
