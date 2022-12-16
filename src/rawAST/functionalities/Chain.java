package rawAST.functionalities;

import rawAST.abstractNode.SyntaxNode;

public class Chain extends SyntaxNode {
    private SyntaxNode origin = null, vector = null;

    public Chain(SyntaxNode first, SyntaxNode second){
        origin = first;
        vector = second;

        origin.setParent(this);
        vector.setParent(this);
    }

    public String getName() {
        return " ";
    }
    public String getUse() {
        return "Chain";
    }

    public SyntaxNode getOrigin() {
        return origin;
    }
    public SyntaxNode getVector() {
        return vector;
    }

    //true if: IDENTIFIER (..)
    //TODO L allow for structures on functions
    public boolean isInferredFunction() {
        return vector.equals(Usage.GROUP, "()") && origin.equals(Usage.IDENTIFIER);
    }

    //true if: IDENTIFIER {..} (..)
    public boolean isTypedFunction() {
        return vector.equals(Usage.GROUP, "()") &&
                origin.equals(Usage.FIELD) &&
                ((Chain)origin).isStructure();
    }

    //true if: ANY (..)
    public boolean isCall() {
        return vector.equals(Usage.GROUP, "()");
    }

    //true if: IDENTIFIER {..}
    public boolean isStructure() {
        return vector.equals(Usage.GROUP, "{}") && origin.equals(Usage.IDENTIFIER);
    }

    //true if: (..) {..}
    public boolean isInferredLambda() {
        return vector.equals(Usage.GROUP, "{}") && origin.equals(Usage.GROUP, "()");
    }

    //true if {..}(..){..}
    public boolean isTypedLambda(){
        return vector.equals(Usage.GROUP, "{}") &&
                origin.equals(Usage.FIELD) &&
                ((Chain)origin).getOrigin().equals(Usage.GROUP, "{}") &&
                ((Chain)origin).getVector().equals(Usage.GROUP, "()");
    }

    //true if ANY [...]
    public boolean isIndex() {
        return vector.equals(Usage.GROUP, "[]");
    }

    //true if ANY_GROUP SUFFIX
    public boolean isGroupedLiteral() {
        return origin.equals(Usage.GROUP) && vector.equals(Usage.IDENTIFIER) && BasicType.isSuffix(vector.getName());
    }

    public FinalSyntaxNode getReplacement() {
        if(isCall()) {
            if(origin.equals(Usage.IDENTIFIER)) {
                Tuple args = Tuple.asTuple(((baseAST.Group) vector).getBody().getReplacement());
                Signature sig = new Signature(args, null);    //TODO rets

                Function func = getFunction(origin.getName(), sig);
                return new Call(func, args);
            }
            else {
                return new Call(getOrigin().getReplacement(), getVector().getReplacement());
            }
        }
        else if(isIndex())
            return new Index(origin.getReplacement(), ((baseAST.Group)vector).getBody().getReplacement());
        else if(isTypedLambda())
            return new Function(
                    null,
                    Tuple.asTuple(((Chain)origin).getVector().getReplacement()),
                    Tuple.asTuple(((Chain)origin).getOrigin().getReplacement())) {{
            setBody(vector.getReplacement());
        }};
        else if(isInferredLambda()) new Function(
                null,
                Tuple.asTuple(origin.getReplacement()),
                null/*TODO L function inference return*/) {{
            setBody(vector.getReplacement());
        }};
        else if(isGroupedLiteral()) {
            switch (vector.getName()) {
                case "r":
                    return Range.decode((baseAST.Group) origin);
                case "set": //TODO these cases
                case "seto":
                case "setm":
                case "setmo":
                case "dict":
                case "dicto":
                case "dictm":
                case "dictmo":
                default:
                    return origin.getReplacement();//TODO L
            }
        }

        return new Contextualization(origin, vector);
    }

    public String toString() {
        return super.toString() + "[" +
                origin + ", " + vector +
                "]";
    }
    public boolean equals(Object other) {
        return super.equals(other) && other instanceof Chain gOther
                && gOther.getOrigin().equals(getOrigin()) && gOther.getVector().equals(getVector());
    }
}
