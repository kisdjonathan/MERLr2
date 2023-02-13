package interpreter;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.*;
import AST.baseTypes.advanced.Str;
import AST.baseTypes.flagTypes.ControlCode;
import AST.baseTypes.flagTypes.ReturnCode;
import AST.baseTypes.numerical.*;
import AST.baseTypes.numerical.Float;
import AST.components.*;
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
        body.unifyVariables(loadBaseTypes(globalVariables));
        BasicType value = body.interpret();
//        System.out.println(value);
//        System.out.println(globalVariables.getVariables());
    }


    //interpreter IO
    private static Scanner scanner = new Scanner(System.in);

    private static class ReadValue extends ReturnCode {
        public static final int INT = 0, FLOAT = 1, STR = 2, LINE = 3, CHAR = 4;
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
                case CHAR -> new Char(scanner.nextByte());
                default -> throw new Error("trying to read an invalid type ");
            };
        }

        public ReadValue clone() {
            return new ReadValue(readType);
        }
    }

    private static Locality loadBaseTypes(Locality variables) {
        Variable ivar = new Variable("int", new Int());
        Variable fvar = new Variable("float", new Float());
        Variable cvar = new Variable("char", new Char());
        Variable bvar = new Variable("bool", new Bool());
        Variable vvar = new Variable("void", new VoidType());
        Variable svar = new Variable("str", new Str());

        variables.putVariable(ivar.getName(), ivar);
        variables.putVariable(fvar.getName(), fvar);
        variables.putVariable(cvar.getName(), cvar);
        variables.putVariable(bvar.getName(), bvar);
        variables.putVariable(vvar.getName(), vvar);
        variables.putVariable(svar.getName(), svar);

        return variables;
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

        Function readc = new Function();
        readc.setArgs(new Tuple());
        readc.setRets(new Float());
        readc.addChild(new ReadValue(ReadValue.CHAR));

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
