package parser;

import AST.abstractNode.SyntaxNode;
import type.numerical.Char;
import type.numerical.Float;
import type.numerical.Int;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Arrays;
import java.util.HashSet;

public class LiteralUtil {
    private static final int BITSPERBYTE = 8;

    private static char characterSumChar(String value) {
        return value.charAt(value.length()-1);
    }
    private static short characterSumLongChar(String value) {
        short ret = (short)value.charAt(0);
        for(int i = 1; i < value.length(); ++i) {
            ret = (short) ((ret << BITSPERBYTE) + value.charAt(i));
        }
        return ret;
    }

    public static SyntaxNode decodeStringWithSuffix(String value, String suffix) {
        return switch (suffix) {
            case "c"    -> new Char(characterSumChar        (value));
            case "uc"   -> null;
            case "lc"   -> null;
            case "ulc"  -> null;
            case "d"    -> null;
            case "ud"   -> null;
            case "ld"   -> null;
            case "uld"  -> null;
            case "f"    -> null;
            case "uf"   -> null;
            case "lf"   -> null;
            case "ulf"  -> null;

            case "cv"   -> null;
            case "ucv"  -> null;
            case "lcv"  -> null;
            case "ulcv" -> null;
            case "dv"   -> null;
            case "udv"  -> null;
            case "ldv"  -> null;
            case "uldv" -> null;
            case "cl"   -> new Str(value);
            case "ucl"  -> null;
            case "lcl"  -> null;
            case "ulcl" -> null;
            case "dl"   -> null;
            case "udl"  -> null;
            case "ldl"  -> null;
            case "uldl" -> null;
            default     -> throw new Error("invalid string literal " + suffix);
        };
    }
    public static SyntaxNode decodeString(String value, String delim) {
        return delim.equals("'") ? new Char(characterSumChar(value)) : new Str(value);
    }
    public static SyntaxNode decodeNumber(String value) {
        value = value.toUpperCase();

        ParsePosition pos = new ParsePosition(0);
        if(value.length() > 1 && value.charAt(0) == '0') {
            if(value.charAt(1) == 'X')
                value = String.valueOf(Long.parseLong(value.substring(2), 16));
            else if(value.charAt(1) == 'B')
                value = String.valueOf(Long.parseLong(value.substring(2), 2));
            else if(value.charAt(1) == 'O')
                value = String.valueOf(Long.parseLong(value.substring(2), 8));
            else if(value.charAt(1) == 'Q')
                value = String.valueOf(Long.parseLong(value.substring(2), 4));
        }
        Number n = NumberFormat.getInstance().parse(value, pos);
        String suffix = value.substring(pos.getIndex());
        if(suffix.equals("")) {
            if(value.contains(".") || value.contains("E"))
                return new Float(n.doubleValue());
            else
                return new Int(n.intValue());
        }
        else {
            return switch (suffix) {
                case "d"    -> new Int  (n.intValue());
                case "ud"   -> null;
                case "ld"   -> null;
                case "uld"  -> null;
                case "c"    -> new Char ((char)n.byteValue());
                case "uc"   -> null;
                case "lc"   -> null;
                case "ulc"  -> null;
                case "f"    -> new Float(n.doubleValue());
                case "uf"   -> null;
                case "lf"   -> null;
                case "ulf"  -> null;
                default     -> throw new Error("invalid numerical suffix " + suffix);
            };
        }
    }

    public static boolean isLiteral(String s) {
        return s.equals("\"") || s.equals("\'") || Character.isDigit(s.charAt(0));
    }
    private static HashSet<String> suffixes = new HashSet<>(Arrays.asList(
            "d", "ud", "ld", "uld",
            "c", "uc", "lc", "ulc",
            "f", "uf", "lf", "ulf",

            "cv", "ucv", "lcv", "ulcv", //static string
            "dv", "udv", "ldv", "uldv", //static string
            "cl", "ucl", "lcl", "ulcl", //dynamic string
            "dl", "udl", "ldl", "uldl", //dynamic string

            "l", "v",   //dynamic and static lists
            "s", "os", "us",  //sorted and unsorted set
            "m", "om", "um",  //sorted and unsorted map
            "r"         //range
    ));
    public static boolean isSuffix(String suffix) {
        return suffixes.contains(suffix);
    }
}