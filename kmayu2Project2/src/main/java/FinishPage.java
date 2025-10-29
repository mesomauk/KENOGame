import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

// handles the final summary screen after all game rounds are complete
public class FinishPage {
    private KenoApp app; // reference to main app for navigation
    private Game game; // reference to current game data for results display

    public FinishPage(KenoApp app, Game game) {
        this.app = app;
        this.game = game;
    }

    // builds and returns the scene for the finish / summary page
    public Scene buildScene() {

        // create title label for page header
        Label title = new Label("Game Summary");

        // create vertical box to hold results for each round
        VBox summaryBox = new VBox(10);
        summaryBox.setAlignment(Pos.CENTER);

        // loop through each result from all game rounds
        for (int i = 0; i < game.getResults().size(); i++) {
            Results r = game.getResults().get(i);

            // create label for this round showing matches and prize won
            Label roundLabel = new Label(
                    "Round " + (i + 1) + ": " + r.getNumMatches() + " matches, $" + r.getPrizeMoney() + " won"
            );

            // add this round’s label to the summary container
            summaryBox.getChildren().add(roundLabel);
        }

        Label totalLabel = new Label("Total Winnings: $" + game.getTotalWinnings()); // label to show total winnings from all rounds combined

        Button mainMenuButton = new Button("Return to Main Menu"); // create button to return to main menu

        // set button appearance (red gradient background, white text, bold font)
        mainMenuButton.setStyle("-fx-background-color: linear-gradient(to bottom, #cb5a31, #c32121); -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 8;-fx-border-radius: 8;");
        mainMenuButton.setOnAction(e -> app.navigateMainPage()); // when button is clicked, go back to the main menu screen

        VBox root = new VBox(title, summaryBox, totalLabel, mainMenuButton);

        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: radial-gradient(center 50% 50%, radius 100%, #ffe100, #ff7700);"); // set background gradient (yellow to orange)

        return new Scene(root, 1100, 750); // return the scene object with width and height specified
    }
}
