import java.util.ArrayList;

// Simulates a Turing Machine
public class TuringMachine {

    private String startState;
    private String acceptState;
    private String rejectState;
    private ArrayList<String> states;
    private Tape tape;
    private ArrayList<Transition> transitions;

    public TuringMachine(String startState, String acceptState, String rejectState, ArrayList<String> states,
                         ArrayList<Transition> transitions) {
        this.startState = startState;
        this.acceptState = acceptState;
        this.rejectState = rejectState;
        this.states = states;
        this.tape = new Tape();
        this.transitions = transitions;
    }

    // Run the Turing Machine with given input string, containing characters from the input alphabet. Returns true for
    // an Accepted string, and False for a rejected string.
    public Boolean run (String input) {
        // Write input string to tape
        tape.initialize(input);

        String curState = startState; // Which state we are at (start at the start)

        // Loop until reaching accept or reject
        while (!(curState.equals(acceptState) || curState.equals(rejectState))) {

            // Look for a transition with fromState matching curState, and readSymbol matching the current place on the tape
            Transition match = findTransition(curState);

            // If a matching transition was found, move along it
            if (match != null) {
                curState = runTransition(match);

            }
            // Else no matching transition was found, reject input string
            else {
                return false;
            }
        }

        return (curState.equals(acceptState));
    }

    // Return a matching transition if found, and null otherwise
    private Transition findTransition (String state) {
        for (Transition transition: transitions) {
            if (transition.getFromState().equals(state) && (transition.getReadSymbol() == tape.read())) {
                return transition;
            }
        }

        // No matches
        return null;
    }

    // Move along a given transition by writing its write symbol to the tape, then moving the head in the transition's direction, and return its toState
    private String runTransition (Transition match) {
        //Write to tape
        tape.write(match.getWriteSymbol());

        //Move head on tape
        tape.move(match.getMove());

        //Move along transition in state machine
        return match.getToState();
    }

}
