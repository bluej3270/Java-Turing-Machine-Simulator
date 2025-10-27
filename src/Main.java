import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// Author: Kellan Clapp
// Description: Simulates a Turing Machine. Code was written for an assignment in my Computation Theory class. TM1.txt and TM2.txt are sample input files, included in the repository.


public class Main {
    public static void main(String[] args) {
        JFileChooser fileChooser = new JFileChooser();

        // Prompt for file
        int result = fileChooser.showOpenDialog(null);

        try {
            // If the user selected a file
            if (result == JFileChooser.APPROVE_OPTION){
                File input = new File(fileChooser.getSelectedFile().toURI());
                Scanner inputReader = new Scanner(input);
                String startState;
                String acceptState;
                String rejectState;
                ArrayList<Character> inputAlphabet;
                ArrayList<Character> tapeAlphabet = new ArrayList<>();
                ArrayList<String> states = new ArrayList<>();
                ArrayList<Transition> transitions = new ArrayList<>();

                //Read start state
                startState = inputReader.nextLine();

                //Read accept state
                acceptState = inputReader.nextLine();

                //Read reject state
                rejectState = inputReader.nextLine();

                //Read input alphabet
                inputAlphabet = new ArrayList<Character>();
                for (char c: inputReader.nextLine().toCharArray()) {
                    if (c != ',')
                        inputAlphabet.add(c);
                }

                // Rest of lines are transitions
                while (inputReader.hasNext()) {
                    String fromState;
                    char readSymbol;
                    char writeSymbol;
                    char move;
                    String toState;

                    //Read transition, split into components
                    String rawLine = inputReader.nextLine();
                    int firstSpace = rawLine.indexOf(' ');

                    fromState = rawLine.substring(0, firstSpace);
                    readSymbol = rawLine.substring(firstSpace + 2, firstSpace + 3).toCharArray()[0];
                    writeSymbol = rawLine.substring(firstSpace + 4, firstSpace + 5).toCharArray()[0];
                    move = rawLine.substring(firstSpace + 6, firstSpace + 7).toCharArray()[0];
                    toState = rawLine.substring(firstSpace + 9);

                    // Record states if not already recorded
                    if (!states.contains(fromState)) {
                        states.add(fromState);
                    }
                    if (!states.contains(toState)) {
                        states.add(toState);
                    }

                    // Record tape alphabet symbols, if not already recorded
                    if (!tapeAlphabet.contains(writeSymbol) && writeSymbol != ' ') {
                        tapeAlphabet.add(writeSymbol);
                    }

                    // Create new transition and add to array
                    transitions.add(new Transition(fromState, readSymbol, writeSymbol, move, toState));
                }

                // Build TuringMachine
                TuringMachine automata = new TuringMachine(startState, acceptState, rejectState, states, transitions);

                boolean again = true;
                do {
                    // Display alphabet to user
                    JOptionPane.showMessageDialog(null, "The input alphabet is: " + inputAlphabet
                            + " , tape alphabet is: " + tapeAlphabet, "Turing Machine Simulator", JOptionPane.INFORMATION_MESSAGE);

                    // Prompt user for input string
                    String inputString = JOptionPane.showInputDialog("Please enter input string:");

                    if (checkInputString(inputAlphabet, inputString)) {
                        // Run the automation and display the result to the user
                        Boolean accepted = automata.run(inputString);
                        if (accepted) {
                            JOptionPane.showMessageDialog(null, "Accepted", "Turing Machine Simulator",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Rejected", "Turing Machine Simulator",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }

                        // Ask the user if they would like to run another input string
                        Object[] options = {"Yes", "No"};
                        int choice = JOptionPane.showOptionDialog(null, "Would you like to try another input string?",
                                "Turing Machine Simulator", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                        again = choice == JOptionPane.YES_OPTION;


                    } else {
                        // User entered a character that wasn't in the alphabet
                        JOptionPane.showMessageDialog(null, "Please only use characters from the input alphabet for your input string!",
                                "Turing Machine Simulator", JOptionPane.INFORMATION_MESSAGE);
                    }

                } while (again);

            }

        } catch (FileNotFoundException e) {
            System.out.println("Error finding selected file");
            e.printStackTrace();
        }
    }

    // Verify characters in the input string are in the input alphabet
    private static boolean checkInputString(ArrayList<Character> alphabet, String inputString) {
        for (char symbol : inputString.toCharArray()) {
            if (!alphabet.contains(symbol)) {
                return false;
            }
        }

        return true;
    }
}