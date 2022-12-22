package interpreter;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.Bool;
import AST.baseTypes.Int;
import AST.baseTypes.Numerical;
import AST.components.Variable;
import lexer.TokenReader;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

//entry point of the program
//TODO complete
public class Entry {
    private static String path = "test1.merl";

    public static void main(String[] args) {
        TokenReader reader = new TokenReader(new File(path));
        SyntaxNode body = reader.readGroup("");
        Map<String, Variable> globalVariables = new HashMap<>();
        Variable _true = new Variable("true");
        _true.setType(Bool.TRUE);
        globalVariables.put(_true.getName(), _true);
        Variable _false = new Variable("false");
        _true.setType(Bool.FALSE);
        globalVariables.put(_false.getName(), _false);
        body.unifyVariables(globalVariables);
        BasicType value = body.interpret();
        System.out.println(value);
        System.out.println(globalVariables);
    }
}
