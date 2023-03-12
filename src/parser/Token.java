package parser;

public class Token {
    private String value;
    private int lineNumber;

    public Token(String value, int lineNumber) {
        this.value = value;
        this.lineNumber = lineNumber;
    }

    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }

    public int getLineNumber() {
        return lineNumber;
    }
    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token that = (Token) o;

        return value.equals(that.value);
    }
}
