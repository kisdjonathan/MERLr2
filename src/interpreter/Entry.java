package interpreter;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.BasicType;
import AST.baseTypes.Function;
import AST.baseTypes.Structure;
import AST.baseTypes.Tuple;
import AST.baseTypes.advanced.Str;
import AST.baseTypes.flagTypes.ControlCode;
import AST.baseTypes.flagTypes.ReturnCode;
import AST.baseTypes.numerical.Bool;
import AST.baseTypes.numerical.Float;
import AST.baseTypes.numerical.Int;
import AST.components.Locality;
import AST.components.Signature;
import AST.components.Variable;
import AST.control.Return;
import lexer.TokenReader;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

//entry point of the program
//TODO complete
public class Entry {
    private static String path = "test files/test2.merl";

    public static void main(String[] args) {
        path = args[0];
        TokenReader reader = new TokenReader(new File(path));
        SyntaxNode body = reader.readGroup("");
        Locality.Wrapper globalVariables = new Locality.Wrapper();
        globalVariables.putVariable("true", new Variable("true"){{setType(new Bool(true));}});
        globalVariables.putVariable("false", new Variable("false"){{setType(new Bool(false));}});
        globalVariables.putVariable("IO", getIOVar());
        body.unifyVariables(globalVariables);
        BasicType value = body.interpret();
//        System.out.println(value);
//        System.out.println(globalVariables.getVariables());
    }


    //interpreter IO
    private static Scanner scanner = new Scanner(System.in);

    private static class ReadValue extends ReturnCode {
        public static final int INT = 0, FLOAT = 1, STR = 2, LINE = 3;
        private final int readType;

        public ReadValue(int readtype) {
            readType = readtype;
        }

        public BasicType getValue() {
            return switch(readType){
                case INT -> new Int(scanner.nextInt());
                case FLOAT -> new Float(scanner.nextDouble());
                case STR -> new Str(scanner.next());
                case LINE -> new Str(scanner.nextLine());
                default -> throw new Error("trying to read an invalid type ");
            };
        }

        public ReadValue clone() {
            return new ReadValue(readType);
        }
    }

    private static Variable getIOVar() {
        Variable ret = new Variable("IO");
        Structure retBody = new Structure();

        Function readl = new Function();
        readl.setArgs(new Tuple());
        readl.setRets(new Str());
        readl.addChild(new ReadValue(ReadValue.LINE));

        Function readi = new Function();
        readi.setArgs(new Tuple());
        readi.setRets(new Int());
        readi.addChild(new ReadValue(ReadValue.INT));

        Function readf = new Function();
        readf.setArgs(new Tuple());
        readf.setRets(new Float());
        readf.addChild(new ReadValue(ReadValue.FLOAT));

        Signature read = new Signature("read");
        read.addOverload(readl);
        read.addOverload(readi);
        read.addOverload(readf);

        retBody.putVariable(read.getName(), read);

        Function write = new Function();

        ret.setType(retBody);
        return ret;
    }
}
