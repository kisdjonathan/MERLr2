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
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;

//entry point of the program
//TODO complete
public class Entry {
    private static String path = "test1.merl";

    public static void main(String[] args) {
        TokenReader reader = new TokenReader(new File(path));
        SyntaxNode body = reader.readGroup("");
        Map<String, Variable> globalVariables = new HashMap<>();
        globalVariables.put("true", new Variable("true"){{setType(new Bool(true));}});
        globalVariables.put("false", new Variable("false"){{setType(new Bool(false));}});
        body.unifyVariables(globalVariables);
        BasicType value = body.interpret();
        System.out.println(value);
        System.out.println(globalVariables);
    }
}
