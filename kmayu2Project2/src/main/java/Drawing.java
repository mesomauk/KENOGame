import java.util.Random;
import java.util.ArrayList;

// Purpose: Handles the drawing of the 20 random numbers for each round.
public class Drawing {
    private ArrayList<Integer> drawnNumbers; // array to store 20 randomly drawn numbers

    // Constructor for class.
    Drawing() {
        drawnNumbers = new ArrayList<>();
    }

    // Randomly draws 20 numbers to be used for a game's single round.
    public void drawNumbers() {
        drawnNumbers.clear(); // clear all current numbers from drawnNumbers list
        Random rand = new Random(); // initialize a random number generator

        // while loop necessary until we reach drawn numbers equal to 20
        while (drawnNumbers.size() < 20) {
            int num = rand.nextInt(80) + 1; // choose a random number between 1-80

            if (!drawnNumbers.contains(num)) {
                drawnNumbers.add(num); // if not already added to drawnNumbers then add it to ensure uniqueness
            }
        }
    }

    // Getter for drawn numbers list.
    public ArrayList<Integer> getDrawnNumbers() {
        return drawnNumbers;
    }

    // Getter for drawn numbers list size.
    public int getNumDrawnNumbers() {
        return drawnNumbers.size();
    }

    // Clears all numbers currently stored in drawn numbers list.
    public void clearDrawnNumbers() {
        drawnNumbers.clear();
    }
}
