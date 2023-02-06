package AST.control;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.flagTypes.ControlCode;
import AST.baseTypes.numerical.Int;

public class Break extends SyntaxNode {
    private Control breakTo = null;
    public Control getBreakTo() {
        return breakTo;
    }
    public void setBreakTo(Control breakTo) {
        this.breakTo = breakTo;
    }

    public Break() {
        addChild(new Int(1));
    }
    public Break(SyntaxNode layers) {
        addChild(layers);
    }
    public Break(SyntaxNode layers, Control breakTo) {
        addChild(layers);
        this.breakTo = breakTo;
    }

    public BasicType getType() {
        return new ControlCode(ControlCode.BREAK);
    }


    public SyntaxNode clone() {
        return new Break(getChild(0), breakTo);
    }

    public BasicType interpret() {
        return new ControlCode(ControlCode.BREAK,((Int)getChild(0).interpret()).asInt());
    }
}
