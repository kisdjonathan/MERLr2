package type;

import type.numerical.Numerical;

public abstract class Longable extends Type {
    private boolean extended = false;
    public boolean isLong() {
        return extended;
    }
    public void setLong(boolean v) {
        extended = v;
    }
}
