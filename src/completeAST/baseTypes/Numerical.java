package completeAST.baseTypes;

public abstract class Numerical extends BasicType {
    private boolean extended = false;
    private boolean unsigned = false;

    public boolean isLong() {
        return extended;
    }
    public Numerical setLong(boolean v) {
        extended = v;
        return this;
    }

    public boolean isUnsigned() {
        return unsigned;
    }
    public Numerical setUnsigned(boolean v) {
        unsigned = v;
        return this;
    }

    public boolean isNumeric() {
        return true;
    }

    protected abstract int defaultByteSize();

    public TypeSize getByteSize() {
        return new TypeSize((extended ? 2 : 1) * defaultByteSize());
    }
}
