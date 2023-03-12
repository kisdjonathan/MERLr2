package parser;

import AST.abstractNode.Control;
import AST.abstractNode.SyntaxNode;
import AST.operator.binary.variable.Field;
import AST.variable.Variable;

import java.io.File;
import java.util.function.Function;

//TokenReader reads in the source file as raw tokens (only Groups, Tuples from semicolons, loops, id, Literals, and function calls are returned)
//read methods take in an additional pre-read argument
public class Parser {
    private final Tokenizer source;

    public Parser(File source) {
        this.source = new Tokenizer(source);
    }

    public SyntaxNode get() {
        if(eof())
            return null;

        Token nextToken = source.get();
        String next = nextToken.getValue();

        SyntaxNode ret;
        if(GroupUtil.isStartDelimiter(next))
            ret = readGroup(nextToken);
        else if(ControlUtil.isControl(next))
            ret = readControl(nextToken);
        else if(ControlUtil.isStatement(next))
            ret = readStatement(nextToken);
        else if(OperatorUtil.isPrefix(next))
            ret = OperatorUtil.decodePrefix(nextToken).with(v->v.addChild(getOperatorBefore(get(), nextToken)));
        else if(LiteralUtil.isLiteral(next))
            ret = readLiteral(nextToken);
        else
            ret = readIdentifier(nextToken);

        while(!eof() && !OperatorUtil.isOperator(source.peekValue()))
            ret = new Field(ret, get());

        ret.setLine(nextToken.getLineNumber());
        return ret;
    }

    /**
     * @param oper an operator that trails a value, so it can be either an infix or a postfix
     * @return whether oper is a postfix
     */
    private boolean isPostfix(String oper) {
        return OperatorUtil.isPostfix(oper) && (
                eof() || !OperatorUtil.isInfix(oper) || GroupUtil.isEndDelimiter(source.peekValue()) || (
                        OperatorUtil.isInfix(source.peekValue()) &&
                                (!OperatorUtil.isPrefix(source.peekValue()) || OperatorUtil.isAfter(source.peekValue(), oper))
                )
        );
    }

    //takes in a SymbolReader (starting right after the operator) and generates nodes with consideration to chaining and precedence
    private SyntaxNode readOperator(SyntaxNode predecessor, Token operToken) {
        String oper = operToken.getValue();

        //group -- call
        if(GroupUtil.isStartDelimiter(oper) && !OperatorUtil.isInfix(oper)) {   //isInfix checks the special case of '|'
            String startDelim = oper;
            SyntaxNode args = null;
            if(!source.eof() && !GroupUtil.isEndDelimiter(startDelim, source.peekValue()))
                args = getOperatorFor(get(), v->!GroupUtil.isEndDelimiter(startDelim, v.getValue()));
            String endDelim = source.eof() ? "EOF" : source.getValue();

            return OperatorUtil.decodeCall(predecessor, operToken, new Token(endDelim, -1), args).with(v->v.setLine(operToken.getLineNumber()));
        }

        if(isPostfix(oper)) {
            SyntaxNode ret = OperatorUtil.decodePostfix(operToken);
            ret.addChild(oper, predecessor);
            ret.setLine(operToken.getLineNumber());
            return ret;
        }
        else if(OperatorUtil.isInfix(oper)){
            SyntaxNode ret = OperatorUtil.decodeInfix(operToken);
            ret.addChild(oper, predecessor);
            ret.setLine(operToken.getLineNumber());

            SyntaxNode next = get();

            while (!eof()) {
                if (OperatorUtil.isBefore(source.peekValue(), oper))
                    next = getOperator(next);
                else if(OperatorUtil.isChainable(oper, source.peekValue())) {
                    ret.addChild(source.getValue(), next);

                    if(isPostfix(oper))    //handles special case for ';'
                        return ret;
                    else
                        next = get();
                }
                else break;
            }
            ret.addChild(null, next);
            return OperatorUtil.verify(ret);  //check for chained =,> or =,< operators
        }
        else{
            throw new Error("Syntax error at line " + operToken.getLineNumber()+": operator " + oper + " is not an infix");
        }
    }
    public SyntaxNode getOperator(SyntaxNode predecessor) {
        return readOperator(predecessor, source.get());
    }
    // reads all operators with greater precedence than reference
    private SyntaxNode getOperatorBefore(SyntaxNode predecessor, Token reference) {
        while (!eof() && OperatorUtil.isOperator(source.peekValue()) &&
                OperatorUtil.isBefore(source.peekValue(), reference.getValue()))
            predecessor = getOperator(predecessor);
        return predecessor;
    }
    private SyntaxNode getOperatorFor(SyntaxNode predecessor, Function<Token, Boolean> passFilter) {
        while (!eof() && OperatorUtil.isOperator(source.peekValue()) &&
                passFilter.apply(source.peek()))
            predecessor = getOperator(predecessor);
        return predecessor;
    }

    public SyntaxNode readSection(Token stop) {
        SyntaxNode ret = null;
        if(!source.eof() && !stop.equals(source.peek())) {
            ret = get();
            while (!source.eof() && !stop.equals(source.peek()))
                ret = getOperator(ret);
        }
        source.get();   //clear endDelim

        ret.setLine(stop.getLineNumber());
        return ret;
    }

    //takes in the statement
    public SyntaxNode readStatement(Token token) {
        String value = token.getValue();
        if(!OperatorUtil.isOperator(source.peekValue()) || OperatorUtil.isPrefix(source.peekValue())) {
            SyntaxNode body = getOperatorBefore(get(), token);
            return ControlUtil.decodeStatement(value, body).with(v->v.setLine(token.getLineNumber()));
        }
        return ControlUtil.decodeStatement(value).with(v->v.setLine(token.getLineNumber()));
    }

    //takes in first part of a literal
    public SyntaxNode readLiteral(Token token) {
        SyntaxNode ret;
        String value = token.getValue();

        if(value.equals("\"") || value.equals("\'")){  //string
            String delim = value;
            value = source.getValue();
            if(LiteralUtil.isSuffix(source.peekValue()))
                ret = LiteralUtil.decodeStringWithSuffix(value, source.getValue());
            else
                ret = LiteralUtil.decodeString(value, delim);
        }
        else if(Character.isDigit(value.charAt(0))) {    //number
            if(source.peekValue().equals(".")) {
                source.get();   //remove '.'
                if(Character.isDigit(source.peekValue().charAt(0)))
                    ret = LiteralUtil.decodeNumber(value + "." + source.getValue());
                else
                    ret = LiteralUtil.decodeNumber(value + ".");
            }
            else
                ret = LiteralUtil.decodeNumber(value);
        }
        else
            throw new Error("Syntax error at line " + token.getLineNumber() + ": invalid literal " + value);

        ret.setLine(token.getLineNumber());
        return ret;
    }

    public SyntaxNode readIdentifier(Token id) {
        return new Variable(id.getValue()).with(v->v.setLine(id.getLineNumber()));
    }


    public SyntaxNode readControl(Token token) {
        Control ret;

        String id = token.getValue();

        //initial condition & body
        {
            SyntaxNode control = readSection(new Token(":", token.getLineNumber()));
            SyntaxNode body = getOperatorBefore(get(), token);
            ret = (Control)ControlUtil.decode(id, control, body).with(v->v.setLine(token.getLineNumber()));
        }

        //chained else/also
        while(ControlUtil.isCase(source.peekValue()) || source.peekValue().equals(";")) {
            Token caseToken = source.get();

            if(caseToken.equals(";")){
                Token semicolon = source.get();
                if(!ControlUtil.isCase(source.peekValue()))   //end of chain
                    return readOperator(ret, semicolon);
                else
                    caseToken.setValue(";"+source.getValue());  //;else/;also
            }
            String name = caseToken.getValue();     //else/also/;else/;also

            SyntaxNode control = readSection(new Token(":", caseToken.getLineNumber()));
            SyntaxNode body = getOperatorBefore(get(), token);
            ret.addChild(name, control, body);
        }

        ret.setLine(token.getLineNumber());
        return ret;
    }

    //takes in a SymbolReader (starting right after the starting delimiter) and generates nodes for which it constructs a local up to the next delimiter
    public SyntaxNode readGroup(Token startToken) {
        String startDelim = startToken.getValue();
        SyntaxNode body = null;
        if(!source.eof() && !GroupUtil.isEndDelimiter(startDelim, source.peekValue()))
            body = getOperatorFor(get(), v->!GroupUtil.isEndDelimiter(startDelim, v.getValue()));
        String endDelim = source.eof() ? "EOF" : source.getValue();
        if(LiteralUtil.isSuffix(source.peek().getValue()))
            return GroupUtil.decodeWithSuffix(body, startDelim, endDelim, source.getValue()).with(v->v.setLine(startToken.getLineNumber()));
        else
            return GroupUtil.decode(body, startDelim, endDelim).with(v->v.setLine(startToken.getLineNumber()));
    }

    public boolean eof() {
        return source.eof();
    }
}