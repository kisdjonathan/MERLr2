package lexer;

import java.util.Arrays;
import java.util.HashSet;

public class ControlReader {
    private static final HashSet<String> controls = new HashSet<>(Arrays.asList(
            "if", "repeat", "while", "for"
    ));
    private static final HashSet<String> cases = new HashSet<>(Arrays.asList(
            "else", "nelse"
    ));
    public static boolean isControl(String s) {
        return controls.contains(s);
    }
    public static boolean isCase(String s) {
        return cases.contains(s);
    }
}
