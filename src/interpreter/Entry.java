package interpreter;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.numerical.Bool;
import AST.components.Locality;
import AST.components.Variable;
import lexer.TokenReader;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

//entry point of the program
//TODO complete
public class Entry {
    private static String path = "test files/test2.merl";

    public static void main(String[] args) {
        TokenReader reader = new TokenReader(new File(path));
        SyntaxNode body = reader.readGroup("");
        Map<String, Variable> globalVariables = new HashMap<>();
        globalVariables.put("true", new Variable("true"){{setType(new Bool(true));}});
        globalVariables.put("false", new Variable("false"){{setType(new Bool(false));}});
        body.unifyVariables(new Locality.Wrapper(globalVariables));
        BasicType value = body.interpret();
        System.out.println(value);
        System.out.println(globalVariables);
    }
}
