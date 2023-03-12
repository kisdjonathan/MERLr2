package parser;

import java.io.*;
import java.util.*;

public class Tokenizer {
    /**
     * characters that can be chained together (ie for identifiers and numbers)
     */
    private static final boolean[] textChar = new boolean[256];
    static{
        //all identifier chars
        for(char i = '0'; i <= '9'; ++i)
            textChar[i] = true;
        for(char i = 'A'; i <= 'Z'; ++i)
            textChar[i] = true;
        for(char i = 'a'; i <= 'z'; ++i)
            textChar[i] = true;
        textChar['$'] = textChar['?'] = textChar['_'] = true;
    }
    private static boolean isIdentifierChar(char charAt) {
        return textChar[charAt];
    }
    /**
     * stores symbols that will not result in an added semicolon(;) when followed by a newline
     */
    private static Set<String> punctuation = new HashSet<>(List.of(",",";","(","{","["));


    private BufferedReader source;
    private final LinkedList<Token> lineBuffer = new LinkedList<>();  //lineBuffer is always full unless eof
    private boolean eof = false;
    private int indention = 0;
    private int lineNumber = 0;

    private String line;
    private int cind = 0;

    public Tokenizer(File source){
        try {
            this.source = new BufferedReader(new FileReader(source));
            loadBuffer(); //fill buffer
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
    }


    private static boolean stringIn(String val, String container, int index) {
        if(index + val.length() > container.length())
            return false;
        for(int i = 0; i < val.length(); ++i) {
            if(val.charAt(i) != container.charAt(index + i))
                return false;
        }
        return true;
    }

    private void loadBuffer() {
        while (lineBuffer.isEmpty() && !eof)
            loadLine();
    }

    private String nextLine() {
        try {
            line = source.readLine();
            cind = 0;
            ++lineNumber;
            if(line == null)
                eof = true;
            return line;
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(-3);
            return null;
        }
    }

    private boolean stringInNext(String val) {
        return stringIn(val, line, cind);
    }
    private int nextMatchingStringLength(Collection<String> options) {
        for(String option : options)
            if(stringInNext(option))
                return option.length();
        return -1;
    }

    private void loadLine() {
        nextLine();
        clearSpaces();
        indention = cind;
        if(line == null)
            return;

        while (!eof && cind < line.length()) {
            int len = 1;
            switch (line.charAt(cind)) {
                //length 1 symbols
                case '@':	//@
                case '#':	//#
                case '%':	//%
                case '^':	//^
                case '&':	//&
                case ',':	//,
                case ';':   //;
                case '\\':  //\
                case '(':	//(
                case ')':	//)
                case '{':	//{
                case '}':	//}
                case '[':	//[
                case ']':	//]
                    break;
                case ' ':	//ignore
                case '\t':	//ignore
                    ++cind;
                    continue;

                //longer symbols
                case '~': 	len = nextMatchingStringLength(List.of("~=", "~")); break;
                case '!':	len = nextMatchingStringLength(List.of("!=", "!")); break;
                case '=':   len = nextMatchingStringLength(List.of("==", "=", "=?")); break;
                case ':':   len = nextMatchingStringLength(List.of(":=", "::", ":")); break;
                case '>':	len = nextMatchingStringLength(List.of("><", ">>", ">=", ">")); break;
                case '<':	len = nextMatchingStringLength(List.of("<=>", "<<", "<=", "<")); break;
                case '-':	len = nextMatchingStringLength(List.of("->", "--", "-")); break;
                case '*':	len = nextMatchingStringLength(List.of("**", "*/", "*")); break;
                case '|':	len = nextMatchingStringLength(List.of("||", "|")); break;
                case '+':	len = nextMatchingStringLength(List.of("++", "+")); break;

                //special symbols
                case '/': {	//multi-line comment(///), comment(//) and divide(/)
                    if (stringIn("//", line, cind + 1)){
                        cind += 3;
                        while(!stringIn("///", line, cind)) {
                            if(++cind >= line.length())
                                line = nextLine();
                        }
                        cind += 3;
                        continue;
                    }
                    else if (stringIn("/", line, cind + 1))
                        return; //finish buffering tokens of this line
                    break;
                }

                case '\'':	//'...', '''...'''
                case '"':{	//"...", """..."""
                    char c = line.charAt(cind);
                    String val;
                    if (stringIn(String.valueOf(c).repeat(3), line, cind)) {
                        cind += 3;
                        val = getStringLiteral(line.substring(cind - 3, cind), true);
                    } else {
                        cind -= 1;
                        val = getStringLiteral(line.substring(cind - 1, cind), false);
                    }
                    lineBuffer.add(new Token(String.valueOf(c), lineNumber));
                    lineBuffer.add(new Token(val, lineNumber));
                    continue;
                }

                case '.':   //., ...
                    if(stringIn("...", line, cind)) {
                        cind += 3;
                        if(cind >= line.length())
                            nextLine();
                        else
                            lineBuffer.add(new Token("...", lineNumber));
                        continue;
                    }
                default:	//id
                    while(cind + len < line.length() &&
                            isIdentifierChar(line.charAt(cind + len)))
                        ++len;
            }

            lineBuffer.add(new Token(line.substring(cind, cind + len), lineNumber));
            cind += len;
        }

        if(!eof() && ! punctuation.contains(lineBuffer.getLast().getValue()))
            lineBuffer.add(new Token(";", lineNumber));
    }

    private void clearSpaces(){
        while(!eof) {
            if(line.charAt(cind) == ' ' || line.charAt(cind) == '\t')
                ++cind;
            else break;

            if(cind >= line.length())
                nextLine();
        }
    }

    private String getStringLiteral(String endDelim, boolean removeSpaceAfterNewline) {
        StringBuilder ret = new StringBuilder();
        while (!stringIn(endDelim, line, cind))
        {
            if(line.charAt(cind) == '\n') {
                ret.append('\n');
                nextLine();
                if(removeSpaceAfterNewline)
                    clearSpaces();
                continue;
            }
            else if(line.charAt(cind) == '\\' && cind + 1 == line.length()) {
                nextLine();
                if(removeSpaceAfterNewline)
                    clearSpaces();
                continue;
            }
            else if (line.charAt(cind) == '\\') {
                ++cind;
                char temp = line.charAt(cind);
                switch (temp) {
                    case 'q' -> ret.append((char) 5);
                    case 'a' -> ret.append((char) 7);
                    case 'b' -> ret.append((char) 8);
                    case 't' -> ret.append((char) 9);
                    case 'n' -> ret.append((char) 10);
                    case 'v' -> ret.append((char) 11);
                    case 'r' -> ret.append((char) 13);
                    case '\\' -> ret.append('\\');
                    case '\'' -> ret.append('\'');
                    case '\"' -> ret.append('\"');
                    case 'x' -> {
                        ++cind;
                        if(cind + 2 >= line.length())
                            throw new Error("Hexadecimal escape character requires 2 digits (line " + lineNumber +
                                    " " + line.substring(cind-2));   //-2 accounting for escape sequence
                        ret.append((char) Integer.parseInt(line.substring(cind, cind + 2), 16));
                        cind += 2;
                        continue;
                    }
                    default -> {
                        if(cind + 3 >= line.length())
                            throw new Error("Octal escape character requires 3 digits (line " + lineNumber +
                                    " " + line.substring(cind-1));   //-1 accounting for escape sequence
                        ret.append((char) Integer.parseInt(line.substring(cind, cind + 3), 8));
                        cind += 3;
                        continue;
                    }
                }
            }
            else
                ret.append(line.charAt(cind));
            ++cind;
        }
        ++cind; //end delimiter

        return ret.toString();
    }

    //strings are split into (",value)
    //numbers are split into (num[, ., num][, e, num])
    public Token get() {
        Token ret = lineBuffer.remove();
        if(lineBuffer.isEmpty())
            loadBuffer();
        return ret;
    }
    public String getValue() {
        return get().getValue();
    }

    public Token peek() {
        return lineBuffer.peek();
    }
    public String peekValue() {
        return peek().getValue();
    }

    public boolean eof() {
        return eof && lineBuffer.isEmpty();
    }
}
