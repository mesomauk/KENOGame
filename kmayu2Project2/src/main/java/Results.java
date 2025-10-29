import java.util.ArrayList;

// Purpose: Handles the outcomes of a single betting or drawing,
// which includes finding the matches between the user’s bettings
// and the actual draw and calculating the winnings from the matches.
public class Results {

    private int prizeMoney; // amount of prize money earned from round
    private int numMatches; // number of matches from round
    private ArrayList<Integer> matchedNumbers; // list of matched numbers from round

    // Constructor for class.
    Results() {
        this.prizeMoney = 0;
        this.numMatches = 0;
        this.matchedNumbers = new ArrayList<>();
    }

    // Finds any matches between user's bets list and generated draws list and adds them
    // to matchedNumbers.
    public void findMatches(ArrayList<Integer> bets, ArrayList<Integer> draw) {
        matchedNumbers.clear(); // clear all current numbers from matchedNumbers list
        for (int selectedNumber : bets) {
            if (draw.contains(selectedNumber)) {
                matchedNumbers.add(selectedNumber); // for all user's selected numbers, if they are contained in draw then add to matchedNumbers
            }
        }

        numMatches = matchedNumbers.size(); // number of matches equal to size of matchedNumbers list
    }

    // Uses the North Carolina state lottery Spot 1, Spot 4, Spot 8 and Spot 10 winnings and odds to calculate prize money
    // earned by user for each outcome.
    public void calculatePrizeMoney(int spots, int matches) {
        if (spots == 1) {
            if (matches == 1) {
                prizeMoney = 2; // 1 spot and 1 match = $2
            }
            else {
                prizeMoney = 0; // Anything else = $0
            }
        }
        else if (spots == 4) {
            switch (matches) {
                case 2:
                    prizeMoney = 1; // 4 spots and 2 matches = $1
                    break;
                case 3:
                    prizeMoney = 5; // 4 spots and 3 matches = $5
                    break;
                case 4:
                    prizeMoney = 75; // 4 spots and 4 matches = $75
                    break;
                default:
                    prizeMoney = 0; // Anything else = $0
            }
        }
        else if (spots == 8) {
            switch (matches) {
                case 4:
                    prizeMoney = 2; // 8 spots and 4 matches = $2
                    break;
                case 5:
                    prizeMoney = 12; // 8 spots and 5 matches = $12
                    break;
                case 6:
                    prizeMoney = 50; // 8 spots and 6 matches = $50
                    break;
                case 7:
                    prizeMoney = 750; // 8 spots and 7 matches = $750
                    break;
                case 8:
                    prizeMoney = 10000; // 8 spots and 8 matches = $10000
                    break;
                default:
                    prizeMoney = 0; // Anything else = $0
            }
        }
        else {
            switch (matches) {
                case 0:
                    prizeMoney = 5; // 10 spots and 0 matches = $5
                    break;
                case 5:
                    prizeMoney = 2; // 10 spots and 5 matches = $5
                    break;
                case 6:
                    prizeMoney = 15; // 10 spots and 6 matches = $15
                    break;
                case 7:
                    prizeMoney = 40; // 10 spots and 7 matches = $40
                    break;
                case 8:
                    prizeMoney = 450; // 10 spots and 8 matches = $450
                    break;
                case 9:
                    prizeMoney = 4250; // 10 spots and 9 matches = $4250
                    break;
                case 10:
                    prizeMoney = 100000; // 10 spots and 10 matches = $100000
                    break;
                default:
                    prizeMoney = 0; // Anything else = $0
            }
        }
    }

    // Updates prize money, number of matches, and list of matched numbers by finding matches first
    // and then calculating the prize money based on those matches.
    public void updateResults(int spots, ArrayList<Integer> bets, ArrayList<Integer> draw) {
        findMatches(bets, draw); // call to find all matches between user's bets and generated draw
        calculatePrizeMoney(spots, numMatches); // calculates prize money based on number of matches found
    }

    // Getter for prize money.
    public int getPrizeMoney() {
        return prizeMoney;
    }

    // Setter for prize money.
    public void setPrizeMoney(int prizeMoney) {
        this.prizeMoney = prizeMoney;
    }

    // Getter for number of matches.
    public int getNumMatches() {
        return numMatches;
    }

    // Getter for list of matched numbers.
    public ArrayList<Integer> getMatchedNumbers() {
        return matchedNumbers;
    }
}
