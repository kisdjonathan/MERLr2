package AST.control;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;

public class Break extends SyntaxNode {
    private int layers = 1;
    public int getLayers() {
        return layers;
    }
    public void setLayers(int layers) {
        this.layers = layers;
    }

    public BasicType getType() {
        return null;//TODO
    }

    public SyntaxNode clone() {
        return null;//TODO
    }

    public BasicType interpret() {
        return null;//TODO
    }
}
