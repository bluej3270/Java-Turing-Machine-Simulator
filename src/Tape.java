import java.util.ArrayList;

// Simulates the tape of a Turing Machine
public class Tape {

    private ArrayList<Character> cells;
    private int headPosition;

    public void initialize (String input) {
        cells = new ArrayList<Character>();
        for (char c : input.toCharArray()) {
            cells.add(c);
        }
        headPosition = 0;
    }

    // Move the head along the tape in the given direction ('L' for left, 'R' for right)
    public void move (char direction) {
        if (direction == 'L') {
            headPosition--;
        } else {
            headPosition++;
        }

        // The tape is infinite, but an arraylist isn't. If the head moves off the front of the tape, reset the head to
        // index zero and add a blank cell to the front of the tape.
        if (headPosition == -1) {
            headPosition++;
            cells.addFirst(' ');
        }

        // If the head moves off the end of the tape, add a bank cell to the end
        if (headPosition == cells.size()) {
            cells.add(' ');
        }
    }

    // Write the given character to the current cell
    public void write (char write) {
        cells.set(headPosition, write);
    }

    // Read the character at the current cell
    public char read() {
        return cells.get(headPosition);
    }
}
