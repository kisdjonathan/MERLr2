package AST.baseTypes;

import AST.abstractNode.SyntaxNode;

import java.util.HashMap;
import java.util.Map;

//TODO Tuple, except with named fields
public abstract class Structure extends Tuple {
    private Map<String, Integer> namePositions = new HashMap<>();

    public SyntaxNode removeChild(String name) {
        return removeChild(namePositions.get(name));
    }
    public void addChild(String name, SyntaxNode child) {
        addChild(child);
        namePositions.put(name, namePositions.size());
    }
    public void addChild(SyntaxNode child) {
        addChild(String.valueOf(namePositions.size()), child);
    }

    public SyntaxNode getChild(String name) {
        return getIndex(namePositions.get(name));
    }
    public SyntaxNode setChild(String name, SyntaxNode val) {
        return setIndex(namePositions.get(name), val);
    }

//    public Collection<Pair> pairFieldsIn(SyntaxNode ref, SyntaxNode t) {
//        if(size() > t.size())
//            return null;
//        List<Pair> ret = new ArrayList<>();
//        for(String s : namePositions.keySet())
//            ret.add(new Pair(new Field(ref, s), new Field(t, s)));
//        return  ret;
//    }
}
