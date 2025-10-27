// Simulates a transition for a turing machine
public class Transition {

    private String fromState;
    private char readSymbol;
    private char writeSymbol;
    private char move;
    private String toState;


    public Transition(String fromState, char readSymbol, char writeSymbol, char move, String toState) {
        this.fromState = fromState;
        this.readSymbol = readSymbol;
        this.writeSymbol = writeSymbol;
        this.move = move;
        this.toState = toState;
    }

    // Getters for transition data
    public String getFromState() {
        return fromState;
    }

    public char getReadSymbol() {
        return readSymbol;
    }

    public char getWriteSymbol() {
        return writeSymbol;
    }

    public char getMove() {
        return move;
    }

    public String getToState() {
        return toState;
    }

    @Override
    public String toString() {
        return "From State: " + fromState + ", Read Symbol: " + readSymbol + ", Write Symbol: " + writeSymbol +
                ", To State: " + toState;
    }


}
