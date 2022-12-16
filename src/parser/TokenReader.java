package parser;
import rawAST.abstractNode.SyntaxNode;
import rawAST.functionalities.*;

import java.io.File;
import java.text.NumberFormat;
import java.text.ParsePosition;

//TokenReader reads in the source file as raw tokens (only Groups, Tuples from semicolons, loops, id, Literals, and function calls are returned)
//read methods take in an additional pre-read argument
//TODO complete
//TODO move field and calls to get (rather than getOperator)
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
        if(Group.isStartDelimiter(next))
            ret = readGroup(next);
        else if(Control.isControl(next))
            ret = readControl(next);
        else if(Operator.isPrefix(next))
            ret = new Operator(next){{
                setPrefix(true);
                addOperand(get());
            }};
        else if(Literal.isLiteral(next))
            ret = readLiteral(next);
        else
            ret = readIdentifier(next);

        while(!eof() && !Operator.isOperator(source.peek()))
            ret = new Chain(ret, get());

        return ret;
    }

    //takes in first part of a literal
    public Literal readLiteral(String value) {
        if(value.equals("\"") || value.equals("\'")){  //string
            value = source.get();
            return new Literal(value, Literal.isSuffix(source.peek()) ? source.get() :
                    value.equals("\"") ? "s" : "c");
        }
        else if(Character.isDigit(value.charAt(0))){    //number
            ParsePosition pos = new ParsePosition(0);
            if(value.length() > 1 && value.charAt(0) == '0') {
                if(value.charAt(1) == 'x' || value.charAt(1) == 'X')
                    value = String.valueOf(Integer.parseInt(value.substring(2), 16));
                else if(value.charAt(1) == 'b' || value.charAt(1) == 'B')
                    value = String.valueOf(Integer.parseInt(value.substring(2), 2));
                else if(value.charAt(1) == 'o' || value.charAt(1) == 'O')
                    value = String.valueOf(Integer.parseInt(value.substring(2), 8));
                else if(value.charAt(1) == 'q' || value.charAt(1) == 'Q')
                    value = String.valueOf(Integer.parseInt(value.substring(2), 4));
            }
            Number n = NumberFormat.getInstance().parse(value, pos);
            String suffix = value.substring(pos.getIndex());
            return new Literal(n.toString(), suffix.length() > 0 ? suffix :
                    value.contains(".") || value.contains("E") || value.contains("e") ? "f" : "d");
        }
        else
            return null;
    }
    public SyntaxNode getLiteral() {
        return readLiteral(source.get());
    }

    public SyntaxNode readIdentifier(String id) {
        return new Identifier(id);
    }
    public SyntaxNode getIdentifier() {
        return readIdentifier(source.get());
    }

    public Control readControl(String id) {
        SyntaxNode control = readGroup("", ":").getBody();
        SyntaxNode body = get();
        while(Operator.isOperator(source.peek()) &&
                Operator.isBefore(source.peek(), id))
            body = getOperator(body);

        SyntaxNode finalBody = body;
        Control ret = new Control(id){{
            setControl(control);
            setBody(finalBody);
        }};

        if(Control.isControl(id))
            while(Control.isCase(source.peek())) {                //else/nelse
                Control chain = getControl();
                ret.addChild(chain.getName(), chain.getControl(), chain.getBody());
            }

        return ret;
    }
    public Control getControl() {
        return readControl(source.get());
    }

    private boolean isPostfix(String oper) {
        return Operator.isPostfix(oper) && (
                eof() ||
                Operator.isInfix(source.peek()) &&
                        (!Operator.isPrefix(source.peek()) || Operator.isAfter(source.peek(), oper))
        );
    }
    private Operator readPostfix(SyntaxNode predecessor, String postfix) {
        Operator ret = new Operator(postfix); ret.addOperand(predecessor);
        if(isPostfix(source.peek()))
            return readPostfix(ret, source.get());
        else
            return ret;
    }

    //takes in a SymbolReader (starting right after the operator) and generates nodes with consideration to chaining and precedence
    private Operator readOperator(SyntaxNode predecessor, String oper) {
        Operator ret = new Operator(oper); ret.addOperand(predecessor);

        //infix
        if(!isPostfix(oper)) {
            SyntaxNode next = get();

            while (!eof()) {
                if (Operator.isBefore(source.peek(), oper))
                    next = getOperator(next);
                else if(Operator.isChainable(oper, source.peek())) {
                    ret.addOperator(oper, predecessor);
                    ret.addOperator(source.get(), next);

                    if(eof())
                        return ret;
                    else
                        next = get();
                }
                else break;
            }
            ret.addOperand(next);
        }
        return ret;
    }
    public Operator getOperator(SyntaxNode predecessor) {
        return readOperator(predecessor, source.get());
    }

    //takes in a SymbolReader (starting right after the starting delimiter) and generates nodes for which it constructs a local up to the next delimiter
    public Group readGroup(String startDelim) {
        Group ret = new Group();
        if(!source.eof() && !Group.isEndDelimiter(source.peek())) {
            SyntaxNode body = get();
            while (!source.eof() && !Group.isEndDelimiter(source.peek())) {
                body = getOperator(body);
            }
            ret.setBody(body);
        }
        String endDelim = source.eof() ? "EOF" : source.get();
        ret.setName(startDelim + endDelim);
        //TODO suffixes
        return ret;
    }
    public Group readGroup(String startDelim, String endDelim) {
        Group ret = new Group();
        if(!source.eof() && !endDelim.equals(source.peek())) {
            SyntaxNode body = get();
            while (!source.eof() && !endDelim.equals(source.peek())) {
                body = getOperator(body);
            }
            ret.setBody(body);
        }
        source.get();   //clear endDelim
        ret.setName(startDelim + endDelim);
        //TODO suffixes
        return ret;
    }
    public Group getGroup() {
        return readGroup(source.get());
    }

    public boolean eof() {
        return source.eof();
    }
}
