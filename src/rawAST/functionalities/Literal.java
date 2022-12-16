package rawAST.functionalities;

import completeAST.abstractNode.FunctionalNode;
import completeAST.baseTypes.*;
import completeAST.baseTypes.Float;
import rawAST.abstractNode.SyntaxNode;

import java.util.Arrays;
import java.util.HashSet;

//baseAST.Literal stores the type and name of a literal (numbers and strings)
public class Literal extends SyntaxNode {
    private String name;
    private String type;

    public Literal(String name, String type){
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }
    public String getType() {return type;}
    public String getUse() {
        return "Literal";
    }
    public int getSize() {
        return 0;
    }

    public FunctionalNode evaluated() {
        return switch (type) {
            case "d"    -> new Int(getName());
            case "ud"   -> new Int(getName()).setUnsigned(true);
            case "ld"   -> new Int(getName()).setLong(true);
            case "uld"  -> new Int(getName()).setUnsigned(true).setLong(true);

            case "c"    -> new Char(getName());
            case "uc"   -> new Char(getName()).setUnsigned(true);
            case "lc"   -> new Char(getName()).setLong(true);
            case "ulc"  -> new Char(getName()).setUnsigned(true).setLong(true);

            case "f"    -> new Float(getName());
            case "uf"   -> new Float(getName()).setUnsigned(true);
            case "lf"   -> new Float(getName()).setLong(true);
            case "ulf"  -> new Float(getName()).setUnsigned(true).setLong(true);

            case "cl"   ->

            default     -> throw new Error("Invalid suffix " + type);
        };
    }

    public static boolean isLiteral(String s) {
        return s.equals("\"") || Character.isDigit(s.charAt(0));
    }
    private static HashSet<String> suffixes = new HashSet<>(Arrays.asList(
            "d", "ud", "ld", "uld",
            "c", "uc", "lc", "ulc",
            "f", "uf", "lf", "ulf",

            "cl", "ucl", "lcl", "ulcl", "sl", //dynamic string
            "cv", "ucv", "lcv", "ulcv", "s", "sv", //static string

            "l", "v",   //static and dynamic
            "s", "os", "us",  //ordered and unordered set
            "m", "om", "um",  //ordered and unordered map
            "r"         //range
    ));
    public static boolean isSuffix(String suffix) {
        return suffixes.contains(suffix);
    }
}
