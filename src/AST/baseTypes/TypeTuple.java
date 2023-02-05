package AST.baseTypes;

import AST.abstractNode.SyntaxNode;

import java.util.Arrays;
import java.util.List;

public class TypeTuple extends Tuple {

    public TypeTuple() {
    }

    public TypeTuple(List<SyntaxNode> children) {
        setChildren(children);
    }

    public TypeTuple(SyntaxNode... nodes) {
        setChildren(Arrays.asList(nodes));
    }

    public boolean equals(Object other) {
        if (other instanceof TypeTuple tother) {
            for (int i = 0; i < size(); ++i)
                if (!getChild(i).getType().typeEquals(tother.getChild(i).getType()))
                    return false;
            return true;
        }
        return false;
    }

}
