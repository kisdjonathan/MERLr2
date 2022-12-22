package AST.baseTypes;

import AST.abstractNode.SyntaxNode;

import java.util.HashMap;
import java.util.Map;

//TODO
public class Structure extends BasicType {
    private Map<String, BasicType> fields = new HashMap<>();

    public Structure(SyntaxNode variable, SyntaxNode body) {
        //TODO
    }

    @Override
    public String getName() {
        return null;
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
