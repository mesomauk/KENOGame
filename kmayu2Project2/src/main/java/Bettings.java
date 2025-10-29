import java.util.ArrayList;
import java.util.Random;

// Purpose: Handles all user selections related to the bettings,
// which includes the number of spots the user selects (1, 4, 8, or 10),
// all the numbers the user selects (numbers from 1-80), and if the quick
// pick option has been selected.
public class Bettings {

    private int numSpots; // number of spots user selects
    private int numSelectedNumbers; // number of selected numbers user selects
    private ArrayList<Integer> selectedNumbers; // list of selected numbers user selects

    // Constructor for class.
    public Bettings() {
        selectedNumbers = new ArrayList<Integer>();
        numSelectedNumbers = 0;
        numSpots = 0;
    }

    // Selects number being passed in and adds it to selectedNumbers list. If user exceeds number of spots
    // they selected then the number will not be selected. If selectedNumbers already contains number being
    // added then it will be removed from selectedNumbers.
    public void selectNumber(int num) {
        if (selectedNumbers.size() < numSpots && !selectedNumbers.contains(num)) {
            selectedNumbers.add(num); // add number if it's not already been added (user clicks on number button once)
            numSelectedNumbers++; // increment number of selected numbers
        }
        else {
            removeNumber(num); // remove number if it's already been added (user clicks on number button again)
        }
    }

    // Removes number being passed in if it exists in selectedNumbers list.
    public void removeNumber(int num) {
        if (selectedNumbers.remove(Integer.valueOf(num))) {
            numSelectedNumbers--; // decrement number of selected numbers if number successfully removed
        }
    }

    // Clears all numbers in selectedNumbers list to reset it.
    public void clearNumbers() {
        selectedNumbers.clear(); // clears all current numbers from selectedNumbers list
        numSelectedNumbers = 0; // sets number of selected numbers to 0
    }

    // Randomly selects unique numbers equal to number of spots user chooses.
    public ArrayList<Integer> autoSelect() {
        selectedNumbers.clear(); // clears all current numbers from selectedNumbers list
        Random rand = new Random(); // initializes a random number generator

        // while loop necessary until we reach selected numbers equal to number of selected spots
        while (selectedNumbers.size() < numSpots) {
            int num = rand.nextInt(80) + 1; // choose a random number between 1-80

            if (!selectedNumbers.contains(num)) {
                selectedNumbers.add(num); // if not already added to selectedNumbers then add it to ensure uniqueness
            }
        }

        numSelectedNumbers = numSpots; // update member variable
        return selectedNumbers; // return list of auto-selected numbers
    }

    // Getter for selected numbers list.
    public ArrayList<Integer> getSelectedNumbers() {
        return selectedNumbers;
    }

    // Getter for number of spots.
    public int getNumSpots() {
        return numSpots;
    }

    // Setter for number of spots.
    public void setNumSpots(int numSpots) {
        this.numSpots = numSpots;
    }

    // Getter of number of selected numbers.
    public int getNumSelectedNumbers() {
        return numSelectedNumbers;
    }
}
