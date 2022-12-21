package lexer;
import AST.components.Variable;
import AST.operations.Operator;
import AST.operations.control.Control;
import AST.operations.variable.Field;
import parser.SymbolReader;
import AST.abstractNode.SyntaxNode;

import java.io.File;

//TokenReader reads in the source file as raw tokens (only Groups, Tuples from semicolons, loops, id, Literals, and function calls are returned)
//read methods take in an additional pre-read argument
//TODO complete
public class TokenReader {
    private final SymbolReader source;

    public TokenReader(File source) {
        this.source = new SymbolReader(source);
    }

    public SyntaxNode get() {
        if(eof())
            return null;

        String next = source.get();
        SyntaxNode ret = null;
        if(GroupReader.isStartDelimiter(next))
            ret = readGroup(next);
        else if(ControlReader.isControl(next))
            ret = readControl(next);
        else if(OperatorReader.isPrefix(next))
            ret = OperatorReader.decodePrefix(next, get());
        else if(LiteralReader.isLiteral(next))
            ret = readLiteral(next);
        else
            ret = readIdentifier(next);

        while(!eof() && !OperatorReader.isOperator(source.peek()))
            ret = new Field(ret, get());

        return ret;
    }

    //takes in first part of a literal
    public SyntaxNode readLiteral(String value) {
        if(value.equals("\"") || value.equals("\'")){  //string
            String delim = value; value = source.get();
            if(LiteralReader.isSuffix(source.peek()))
                return LiteralReader.decodeStringWithSuffix(value, source.get());
            else
                return LiteralReader.decodeString(value, delim);
        }
        else if(Character.isDigit(value.charAt(0)))    //number
            return LiteralReader.decodeNumber(value);
        else
            return null;
    }
    public SyntaxNode getLiteral() {
        return readLiteral(source.get());
    }

    public SyntaxNode readIdentifier(String id) {
        return new Variable(id);
    }
    public SyntaxNode getIdentifier() {
        return readIdentifier(source.get());
    }

    public Control readControl(String id) {
        Control ret;

        {
            SyntaxNode control = readGroup("", ":");
            SyntaxNode body = get();
            while (OperatorReader.isOperator(source.peek()) &&
                    OperatorReader.isBefore(source.peek(), id))
                body = getOperator(body);

            ret = Control.decode(id, control, body);
        }

        while(ControlReader.isCase(source.peek())) {
            String name = source.get();     //else/nelse
            SyntaxNode control = readGroup("", ":");
            SyntaxNode body = get();
            while (OperatorReader.isOperator(source.peek()) &&
                    OperatorReader.isBefore(source.peek(), id))
                body = getOperator(body);

            ret.addChild(name, control, body);
        }

        return ret;
    }
    public Control getControl() {
        return readControl(source.get());
    }

    private boolean isPostfix(String oper) {
        return OperatorReader.isPostfix(oper) && (
                eof() ||
                OperatorReader.isInfix(source.peek()) &&
                        (!OperatorReader.isPrefix(source.peek()) || OperatorReader.isAfter(source.peek(), oper))
        );
    }

    //takes in a SymbolReader (starting right after the operator) and generates nodes with consideration to chaining and precedence
    private SyntaxNode readOperator(SyntaxNode predecessor, String oper) {
        //group
        if(GroupReader.isStartDelimiter(oper)) {
            SyntaxNode args = readGroup(oper);
            return OperatorReader.decodeCall(predecessor, args);
        }

        SyntaxNode ret = OperatorReader.decode(oper); ret.addChild(oper, predecessor);
        //infix
        if(!isPostfix(oper)) {
            SyntaxNode next = get();

            while (!eof()) {
                if (OperatorReader.isBefore(source.peek(), oper))
                    next = getOperator(next);
                else if(OperatorReader.isChainable(oper, source.peek())) {
                    ret.addChild(source.get(), next);

                    if(eof())
                        return ret;
                    else
                        next = get();
                }
                else break;
            }
            ret.addChild(null, next);
        }
        return ret;
    }
    public SyntaxNode getOperator(SyntaxNode predecessor) {
        return readOperator(predecessor, source.get());
    }

    //takes in a SymbolReader (starting right after the starting delimiter) and generates nodes for which it constructs a local up to the next delimiter
    public SyntaxNode readGroup(String startDelim) {
        SyntaxNode body = null;
        if(!source.eof() && !GroupReader.isEndDelimiter(source.peek())) {
            body = get();
            while (!source.eof() && !GroupReader.isEndDelimiter(source.peek()))
                body = getOperator(body);
        }
        String endDelim = source.eof() ? "EOF" : source.get();
        if(LiteralReader.isSuffix(source.peek()))
            return GroupReader.decodeWithSuffix(body, startDelim, endDelim, source.get());
        else
            return GroupReader.decode(body, startDelim, endDelim);
    }
    public SyntaxNode readGroup(String startDelim, String endDelim) {
        SyntaxNode body = null;
        if(!source.eof() && !endDelim.equals(source.peek())) {
            body = get();
            while (!source.eof() && !endDelim.equals(source.peek()))
                body = getOperator(body);
        }
        source.get();   //clear endDelim
        if(LiteralReader.isSuffix(source.peek()))
            return GroupReader.decodeWithSuffix(body, startDelim, endDelim, source.get());
        else
            return GroupReader.decode(body, startDelim, endDelim);
    }
    public SyntaxNode getGroup() {
        return readGroup(source.get());
    }

    public boolean eof() {
        return source.eof();
    }
}
