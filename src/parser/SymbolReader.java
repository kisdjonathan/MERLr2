package parser;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

//SymbolReader breaks the input into tokens as it is being read
public class SymbolReader {
    private static final boolean[] identifierChars = new boolean[256];
    static{
        //all identifier chars
        for(char i = '0'; i <= '9'; ++i)
            identifierChars[i] = true;
        for(char i = 'A'; i <= 'Z'; ++i)
            identifierChars[i] = true;
        for(char i = 'a'; i <= 'z'; ++i)
            identifierChars[i] = true;
        identifierChars['$'] = identifierChars['?'] = identifierChars['_'] = true;
    }

    private static class PostReadPosition {
        public String line;
        public int position;

        public PostReadPosition(){}
        public PostReadPosition(String line, int pos) {
            this.line = line;
            this.position = pos;
        }
    }

    private BufferedReader source;
    private final Queue<String> lineBuffer = new LinkedList<>();  //lineBuffer is always full unless eof
    private boolean eof = false;

    public SymbolReader(File source) {
        try {
            this.source = new BufferedReader(new FileReader(source));
            loadBuffer(); //fill buffer
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
    }

    private void loadLine() {
        String line = getLine(); //newline is used to prevent error when peeking at the next char
        if(line == null)
            return;

        int cind = 0;
        while (!eof && cind + 1 < line.length()) {   //before newline
            int len = 1;
            switch (line.charAt(cind)) {
                case '~': 	//~=, ~
                case '!':	//!=, !
                case '?':   //?=
                case '=':	//=, ==
                case ':':	//:, :=
                    if (line.charAt(cind + 1) == '=')
                        ++len;
                    break;
                case '<':	//<=, <<, <
                    if (line.charAt(cind + 1) == '=' || line.charAt(cind + 1) == '<')
                        ++len;
                    break;
                case '>':	//>=, >>, >
                    if (line.charAt(cind + 1) == '=' || line.charAt(cind + 1) == '>')
                        ++len;
                    break;
                case '-':	//->, -
                    if (line.charAt(cind + 1) == '>')
                        ++len;
                    break;
                case '|':	//|
                    if (line.charAt(cind + 1) == '|')
                        ++len;
                    break;
                case '@':	//@
                case '#':	//#
                case '%':	//%
                case '^':	//^
                case '&':	//&
                case '+':	//+
                case '*':	//*, **
                    if (line.charAt(cind + 1) == '*')
                        ++len;
                    break;
                case ';': {    //;, ;else, ;nelse
                    PostReadPosition temp = clearSpaces(line, cind+1);
                    line = temp.line;
                    cind = temp.position;
                    if(!eof) {
                        if (stringIn("else", line, cind) && !isIdentifierChar(line.charAt(cind + 4))) {
                            lineBuffer.add(";else");
                            cind += 4;
                        } else if (stringIn("nelse", line, cind) && !isIdentifierChar(line.charAt(cind + 5))) {
                            lineBuffer.add(";nelse");
                            cind += 5;
                        }
                    }
                    lineBuffer.add(";");
                    continue;
                }
                case '.':   //., ...
                    if(stringIn("...", line, cind)) {
                        lineBuffer.add("...");
                        cind += 3;
                        continue;
                    }
                case ',':	//,
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

                case '/': {	////,/*,/
                    if (line.charAt(cind + 1) == '/')
                        return;
                    else if (line.charAt(cind + 1) == '*') {
                        while(line.charAt(cind) != '*' || line.charAt(cind + 1) != '/') {
                            ++cind;
                            if(cind >= line.length()) {
                                line = getLine();
                                cind = 0;
                            }
                        }
                        cind+=2;
                        continue;
                    }
                    break;
                }

                case '\'':	//'...', '''...'''
                case '"':{	//"...", """..."""
                    char c = line.charAt(cind);
                    StringBuilder val = new StringBuilder();
                    PostReadPosition temp;
                    if (line.length() > cind + 2 && line.charAt(cind + 1) == c && line.charAt(cind + 2) == c)
                        temp = getStringLiteral(line, val, cind + 3, line.substring(cind, cind + 3), true);
                    else
                        temp = getStringLiteral(line, val, cind + 1, line.substring(cind, cind + 1), false);
                    line = temp.line;
                    cind = temp.position;
                    lineBuffer.add(String.valueOf(c));
                    lineBuffer.add(val.toString());
                    continue;
                }

                default:	//id
                    while(isIdentifierChar(line.charAt(cind + len)))
                        ++len;
            }

            lineBuffer.add(line.substring(cind, cind + len));
            cind += len;
        }
    }

    private void loadBuffer() {
        while (lineBuffer.isEmpty() && !eof)
            loadLine();
    }

    private boolean isIdentifierChar(char charAt) {
        return identifierChars[charAt];
    }

    private String getLine() {
        try {
            String ret = source.readLine();
            if(ret != null)
                return ret + '\n';
            eof = true;
            return null;
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(-3);
            return null;
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

    private PostReadPosition clearSpaces(String line, int cind){
        while(!eof && cind < line.length()) {
            if(line.charAt(cind) == ' ' || line.charAt(cind) == '\t')
                ++cind;
            else if(line.charAt(cind) == '\n') {
                line = getLine();
                cind = 0;
            }
            else break;
        }
        return new PostReadPosition(line, cind);
    }

    private PostReadPosition getStringLiteral(String line, StringBuilder ret, int cind, String endDelim, boolean replaceSpaceTrailingNewline) {
        while (!stringIn(endDelim, line, cind))
        {
            if(line.charAt(cind) == '\n') {
                ret.append('\n');
                line = getLine();
                if(replaceSpaceTrailingNewline) {
                    PostReadPosition r = clearSpaces(line, cind);
                    line = r.line;
                    cind = r.position;
                }
                continue;
            } else if (line.charAt(cind) == '\\')
            {
                char temp = line.charAt(++cind);
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
                    case '\n' -> {
                        line = getLine();
                        cind = 0;
                        if(replaceSpaceTrailingNewline) {
                            PostReadPosition r = clearSpaces(line, cind);
                            line = r.line;
                            cind = r.position;
                        }
                        ret.append(' ');
                        continue;
                    }
                    case 'x' -> {
                        ++cind;
                        ret.append((char) Integer.parseInt(line.substring(cind, cind + 2), 16));
                        cind += 2;
                        continue;
                    }
                    default -> {
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

        return new PostReadPosition(line, cind);
    }

    //strings are split into (",value)
    //numbers are split into (num[, ., num][, e, num])
    public String get() {
        String ret = lineBuffer.remove();
        if(lineBuffer.isEmpty())
            loadBuffer();
        return ret;
    }

    public String peek() {
        return lineBuffer.peek();
    }

    public boolean eof() {
        return eof && lineBuffer.isEmpty();
    }
}
