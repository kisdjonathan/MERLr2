package interpreter;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.Int;
import AST.baseTypes.Numerical;
import lexer.TokenReader;

import java.io.File;

//entry point of the program
//TODO complete
public class Entry {
    private static String path = "test1.merl";

    public static void main(String[] args) {
        TokenReader reader = new TokenReader(new File(path));
        SyntaxNode body = reader.readGroup("");
        BasicType value = body.interpret();
        System.out.println(value);
    }
}
