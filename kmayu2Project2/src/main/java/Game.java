import java.util.ArrayList;

// Handles the procedures of the entire game session, including betting, drawing, calculating winnings
// across the total number of drawings, and determining when the game should start and end.
public class Game {

    private Bettings betting; // instance of user's betting selections
    private Drawing drawing; // instance of generated drawing
    private ArrayList<Results> results; // list of results for each round
    private int drawIndex; // index of current draw
    private int totalDraws; // number of total draws
    private int totalWinnings; // total amount of winnings

    // Constructor for class.
    public Game(Bettings betting, Drawing drawing) {
        this.betting = betting;
        this.drawing = drawing;
        this.results = new ArrayList<>();
    }

    // Initializes the game by setting current draw index to 0, total winnings to $0, clearing any results, and total draws
    // to however many the user selected.
    public void startGame(int totalDraws) {
        this.drawIndex = 0;
        this.totalDraws = totalDraws;
        this.totalWinnings = 0;
        this.results.clear();
    }

    // Performs a single round and calculates matches and earnings from that single round.
    public void runDrawing() {
        if (drawIndex < totalDraws) { // round is only performed if we have draws remaining
            drawing.drawNumbers(); // first draw numbers
            Results result = new Results(); // initialize a new instance of results
            result.updateResults(betting.getNumSpots(), betting.getSelectedNumbers(), drawing.getDrawnNumbers()); // update results
            results.add(result); // add current results to list of results
            updateWinnings(result.getPrizeMoney()); // update winnings based on prize money from round
            drawIndex++;
        }
    }

    // Adds prize money to total winnings.
    public void updateWinnings(int prizeMoney) {
        totalWinnings += prizeMoney;
    }

    // Getter for total number of draws.
    public int getTotalDraws() {
        return totalDraws;
    }

    // Setter for total number of draws.
    public void setTotalDraws(int totalDraws) {
        this.totalDraws = totalDraws;
    }

    // Getter for total amount of winnings.
    public int getTotalWinnings() {
        return totalWinnings;
    }

    // Getter for list of result instances.
    public ArrayList<Results> getResults() {
        return results;
    }

    // Getter for current round index.
    public int getCurrRound() {
        return drawIndex;
    }

    // Getter for current round's number of matches.
    public int getCurrRoundMatches() {
        if (results.isEmpty()) {
            return 0;
        }
        return results.get(results.size() - 1).getNumMatches();
    }

    // Getter for current round's amount of earnings.
    public int getCurrRoundEarnings() {
        if (results.isEmpty()) {
            return 0;
        }
        return results.get(results.size() - 1).getPrizeMoney();
    }

    // Getter for instance of user's bettings.
    public Bettings getBettings() {
        return betting;
    }

    // Getter for instance of generated drawings.
    public Drawing getDrawing() {
        return drawing;
    }

    // Getter for current draw index.
    public int getDrawIndex() {
        return drawIndex;
    }
}
