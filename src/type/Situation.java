package type;

import AST.abstractNode.SyntaxNode;

/**
 * represents the situation of an operation
 * stores the input/output of the operation
 * used intermediately for determining the evaluation of the operation
 */
public class Situation extends Type {

    public Situation(){}
    public Situation(Type input) {
        addChild(input);
        addChild(new InferredType());
    }
    public Situation(Type input, Type output) {
        addChild(input);
        addChild(output);
    }

    public String getName(){
        return "situation";
    }
    public SyntaxNode emptyClone() {
        return new Situation();
    }

    public boolean typeEquals(Type o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Situation situation = (Situation) o;
        return Type.typeEquals(situation.getChild(0).getType(), getChild(0).getType()) &&
                Type.typeEquals(situation.getChild(1).getType(), getChild(1).getType());
    }
}