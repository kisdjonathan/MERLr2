package parser;

import AST.abstractNode.Control;
import AST.abstractNode.SyntaxNode;
import AST.control.*;
import AST.operations.variable.In;
import AST.operator.binary.list.In;

import java.util.Arrays;
import java.util.HashSet;

public class ControlUtil {
    private static final HashSet<String> controls = new HashSet<>(Arrays.asList(
            "if", "repeat", "while", "for"
    ));
    private static final HashSet<String> cases = new HashSet<>(Arrays.asList(
            "else", "nelse",";else", ";nelse"
    ));
    private static final HashSet<String> statements = new HashSet<>(Arrays.asList(
            "break", "continue", "return"
    ));
    public static boolean isControl(String s) {
        return controls.contains(s);
    }
    public static boolean isCase(String s) {
        return cases.contains(s);
    }
    public static boolean isStatement(String s) {
        return statements.contains(s);
    }

    public static SyntaxNode decodeStatement(String controlName) {
        switch (controlName) {
            case "break":
                return new Break();
            case "continue":
                return new Continue();
            case "return":
                return new Return();
            default:
                throw new Error("no statement by the name " + controlName);
        }
    }

    public static SyntaxNode decodeStatement(String controlName, SyntaxNode body) {
        switch (controlName) {
            case "break":
                return new Break(body);
            case "continue":
                return new Continue(body);
            case "return":
                return new Return(body);
            default:
                throw new Error("no statement by the name " + controlName);
        }
    }

    public static Control decode(String controlName, SyntaxNode condition, SyntaxNode body) {
        switch (controlName) {
            case "if":
                return new If(condition, body);
            case "repeat":
                return new Repeat(condition, body);
            case "while":
                return new While(condition, body);
            case "for":
                if(condition instanceof In icondition)
                    return new For(icondition, body);
            default:
                throw new Error("no control by the name " + controlName);
        }
    }
}