package lexer;

import AST.abstractNode.SyntaxNode;
import AST.baseTypes.*;
import AST.baseTypes.advanced.*;
import AST.operations.arithmetic.AbsoluteValue;

public class GroupReader {
    /**
     * fills value with body and returns value
     */
    private static Range fillRange(Range value, SyntaxNode body) {
        Tuple elements = Tuple.asTuple(body);
        switch (elements.size()) {
            case 1:
                value.setStop(elements.getChild(0));
                break;
            case 2:
                value.setStart(elements.getChild(0));
                value.setStop(elements.getChild(1));
            case 3:
                value.setStep(elements.getChild(2));
            default:
                throw new Error("too many values in range");
        }
        return value;
    }
    private static Range decodeRange(SyntaxNode body, String startdelim, String enddelim) {
        return switch (startdelim + enddelim) {
            case "[]" -> fillRange(new RangeII(), body);
            case "()" -> fillRange(new RangeEE(), body);
            case "[)" -> fillRange(new RangeIE(), body);
            case "(]" -> fillRange(new RangeEI(), body);
            default   -> throw new Error("invalid braces for range " + startdelim + enddelim);
        };
    }

    public static SyntaxNode decodeWithSuffix(SyntaxNode body, String startdelim, String enddelim, String suffix) {
        return switch (suffix) {
            case "l"        ->  new DynamicArray(Tuple.asTuple(body));
            case "v"        -> new FixedArray(Tuple.asTuple(body));
            case "s", "us"  ->
                    (startdelim + enddelim).equals("[]") ?
                            null:   // multiset
                            new UnorderedSet(Tuple.asTuple(body));
            case "os"       ->  null;
            case "m", "um"  ->
                    (startdelim + enddelim).equals("[]") ?
                            null:   // multimap
                            new UnorderedMap(Tuple.asTuple(body));
            case "om"       ->  null;
            case "r"        -> decodeRange(body, startdelim, enddelim);
            default         ->  throw new Error("invalid group suffix " + suffix);
        };
    }
    public static SyntaxNode decode(SyntaxNode body, String startdelim, String enddelim) {
        return switch (startdelim+enddelim) {
            case "()"   -> body;
            case "(]"   -> fillRange(new RangeEI(), body);
            case "[)"   -> fillRange(new RangeIE(), body);
            case "[]"   -> new DynamicArray(Tuple.asTuple(body));
            case "{}"   -> new UnorderedSet(Tuple.asTuple(body));
            case "||"   -> new AbsoluteValue(body);
            case "EOF"  -> body;    //TODO this is the entire file, so more analysis should be performed
            default     -> throw new Error("invalid combination of start and end delimiters " + startdelim + enddelim);
        };
    }

    public static boolean isStartDelimiter(String s) {
        return s.length() == 1 && switch(s.charAt(0)){
            case '[', '{', '(', '|' ->true;
            default -> false;
        };
    }
    public static boolean isEndDelimiter(String s) {
        return s.length() == 1 && switch(s.charAt(0)){
            case ']', '}', ')', '|' ->true;
            default -> false;
        };
    }
}
