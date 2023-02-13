package AST.baseTypes.flagTypes;

/**
 * positions at which control changes may occur:
 *  Tuples
 *  Control body *will be in the form of a Tuple if break is present, otherwise no change will be necessary
 *  Function body ^
 *  With/Without *will finish execution regardless of whether break is present
 */

public class ControlCode extends InternalMessage{
    public static final int BREAK = 0;
    public static final int CONTINUE = 1;
    public static final int RETURN = 2;

    private int choice = 0;
    private int layers = 1;

    public ControlCode(int choice, int layers) {
        this.choice = choice;
        this.layers = layers;
    }
    public ControlCode(int choice) {
        this.choice = choice;
    }


    public int getChoice() {
        return choice;
    }

    public void setChoice(int choice) {
        this.choice = choice;
    }

    public int getLayers() {
        return layers;
    }

    public void setLayers(int layers) {
        this.layers = layers;
    }

    public ControlCode reduced() {
        --this.layers;
        return this;
    }

    public ControlCode clone() {
        return new ControlCode(choice, layers);
    }
}
