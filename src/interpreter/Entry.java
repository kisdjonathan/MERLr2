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
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

//entry point of the program
//TODO complete
public class Entry {
    private static String path = "test files/test1.merl";

    public static void main(String[] args) {
        if(args.length > 0)
            path = args[0];
        interpret(path);
    }

    public static BasicType interpret(String path) {
        TokenReader reader = new TokenReader(new File(path));
        SyntaxNode body = reader.readGroup("");
        Locality.Wrapper globalVariables = new Locality.Wrapper();
        globalVariables.putVariable("true", new Variable("true"){{setType(new Bool(true));}});
        globalVariables.putVariable("false", new Variable("false"){{setType(new Bool(false));}});
        globalVariables.putVariable("IO", getIOVar());
        body.unifyVariables(loadBaseTypes(globalVariables));
        return body.interpret();
    }


    //interpreter IO
    public static Scanner scanner = new Scanner(System.in);
    public static PrintStream writer = System.out;

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
    private static class WriteValue extends ReturnCode {
        public static final int INT = 0, FLOAT = 1, STR = 2, CHAR = 4;
        private final int writetype;

        public WriteValue(int wtype, SyntaxNode val) {
            writetype = wtype;
            addChild(val);
        }

        public BasicType getValue() {
            writer.print(switch(writetype){
                case INT -> ((Int)getChild(0).interpret()).getValue();
                case FLOAT -> ((Float)getChild(0).interpret()).getValue();
                case STR -> ((Str)getChild(0).interpret()).getValue();
                case CHAR -> (char)(((Char)getChild(0).interpret()).getValue());
                default -> throw new Error("trying to read an invalid type ");
            });
            return new VoidType();
        }

        public WriteValue clone() {
            return new WriteValue(writetype, getChild(0).clone());
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
        readc.setRets(new Char());
        readc.addChild(new ReadValue(ReadValue.CHAR));

        Signature read = new Signature("read");
        read.addOverload(readl);
        read.addOverload(readi);
        read.addOverload(readf);
        read.addOverload(readc);


        Function writei = new Function();
        Variable vari = new Variable("written", new Int());
        writei.setArgs(vari);
        writei.setRets(new VoidType());
        writei.addChild(new WriteValue(WriteValue.INT, vari));

        Function writef = new Function();
        Variable varf = new Variable("written", new Float());
        writef.setArgs(varf);
        writef.setRets(new VoidType());
        writef.addChild(new WriteValue(WriteValue.FLOAT, varf));

        Function writec = new Function();
        Variable varc = new Variable("written", new Char());
        writec.setArgs(varc);
        writec.setRets(new VoidType());
        writec.addChild(new WriteValue(WriteValue.CHAR, varc));

        Function writes = new Function();
        Variable vars = new Variable("written", new Str());
        writes.setArgs(vars);
        writes.setRets(new VoidType());
        writes.addChild(new WriteValue(WriteValue.STR, vars));

        Signature write = new Signature("write");
        write.addOverload(writei);
        write.addOverload(writef);
        write.addOverload(writec);
        write.addOverload(writes);


        retBody.putVariable(read.getName(), read);
        retBody.putVariable(write.getName(), write);

        ret.setType(retBody);
        return ret;
    }
}
