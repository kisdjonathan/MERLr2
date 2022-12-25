package AST.control;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;

public class Repeat extends Control {
    public Repeat(SyntaxNode count, SyntaxNode body) {
        setBase(count, body);
    }
    private Repeat(){}

    //TODO
    @Override
    public BasicType getType() {
        return null;
    }

    public Repeat clone() {
        Repeat ret = new Repeat();
        ret.setBase(getBase().clone());
        return ret;
    }
    @Override
    public BasicType interpret() {
        return null;
    }
}
