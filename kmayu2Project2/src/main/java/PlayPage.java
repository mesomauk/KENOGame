import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Iterator;

public class PlayPage {
    private KenoApp app; // handles navigation between pages
    private Game game;  // current game session

    // UI elements for labels and buttons
    Label mainMenuTitle;
    Button rulesButton;
    Button oddsButton;
    Button exitButton;
    Button layoutButton;

    // score and stats displays
    Label roundMatchesValue;
    TitledPane roundMatchesPane;

    Label roundEarningsValue;
    TitledPane roundEarningsPane;

    Label totalPrizesValue;
    TitledPane totalPrizesPane;

    Label currRoundValue;
    TitledPane currRoundPane;

    TitledPane spotsPane;

    Button continueButton;

    GridPane numberGrid;

    VBox buttonChoices;
    HBox buttonChoicesAndGrid;
    HBox bottomRow;
    VBox vBox;

    Label matchesLabel;
    TitledPane matchesPane;

    public PlayPage(KenoApp app, Game game) {
        this.app = app;
        this.game = game;
    }

    public Scene buildScene() {
        mainMenuTitle = new Label("KENO");
        mainMenuTitle.setStyle("-fx-font-size: 28px");

        // main control buttons
        rulesButton = new Button("Rules");
        oddsButton = new Button("Odds");
        exitButton = new Button("Exit");
        layoutButton = new Button("Layout");

        // label for continue button changes depending on draw count
        if (game.getTotalDraws() == 1) {
            continueButton = new Button("Play");
        }
        else {
            continueButton = new Button("Start");
        }

        numberGrid = new GridPane(); // grid setup

        // base styles for buttons and labels
        String buttonStyle = "-fx-background-color: linear-gradient(to bottom, #cb5a31, #c32121); -fx-text-fill: #ffffff;-fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 8;-fx-border-radius: 8;";
        String labelStyle = "-fx-text-fill: #ffffff; -fx-font-size: 22px; -fx-font-weight: bolder;";
        continueButton.setPrefSize(100, 40);

        // round-level stats initialized using current game state
        roundMatchesValue = new Label(String.valueOf(game.getCurrRoundMatches()));
        roundMatchesPane = new TitledPane("Round Matches", roundMatchesValue);

        roundEarningsValue = new Label("$" + game.getCurrRoundEarnings());
        roundEarningsPane = new TitledPane("Round Earnings", roundEarningsValue);

        currRoundValue = new Label(game.getCurrRound() + "/" + game.getTotalDraws());
        currRoundPane = new TitledPane("Round Number", currRoundValue);

        totalPrizesValue = new Label("$" + game.getTotalWinnings());
        totalPrizesPane = new TitledPane("Total Prizes", totalPrizesValue);

        // panel showing list of drawn numbers
        matchesLabel = new Label("Drawings");
        matchesPane = new TitledPane("Matches", matchesLabel);
        matchesPane.setCollapsible(false);

        // build prize chart for chosen number of spots
        Label spotsLabel = new Label("Spots");
        spotsLabel.setAlignment(Pos.CENTER);
        spotsLabel.setStyle(labelStyle);
        VBox spotsPane = new VBox(7, spotsLabel, buildSpotTable(game.getBettings().getNumSpots()));
        spotsPane.setStyle(buttonStyle);
        spotsPane.setPadding(new Insets(10));
        spotsPane.setAlignment(Pos.CENTER);

        // set button sizes
        double buttonWidth = 100;
        double buttonHeight = 40;
        rulesButton.setPrefSize(buttonWidth, buttonHeight);
        oddsButton.setPrefSize(buttonWidth, buttonHeight);
        exitButton.setPrefSize(buttonWidth, buttonHeight);
        layoutButton.setPrefSize(buttonWidth, buttonHeight);

        // build the grid of 80 toggle buttons and color the selected ones
        ArrayList<Integer> selectedNumbers = game.getBettings().getSelectedNumbers();
        int number = 1;
        for (int r = 0; r < 10; r++){
            for (int c = 0; c < 8; c++){
                ToggleButton numberButton = new ToggleButton(String.valueOf(number));
                numberButton.setPrefWidth(40);
                numberButton.setPrefHeight(40);
                numberButton.setDisable(false);
                numberButton.setStyle(" -fx-background-color: radial-gradient(center 50% 50%, radius 50%, #ffcdf0, #e250b7);");
                int num = number;

                // highlight numbers that player selected in previous screen
                if (selectedNumbers.contains(num)){
                    numberButton.setDisable(false);
                    numberButton.setStyle("-fx-background-color: #505ae2;");
                }
                numberGrid.add(numberButton, c, r);
                number++;
            }
        }
        numberGrid.setHgap(8);
        numberGrid.setVgap(8);

        // when player clicks continue/start/play button
        continueButton.setOnAction(e -> {
            onSelectContinueButton();
        });

        // exit returns to main menu and resets
        exitButton.setOnAction(e -> {
            resetPage();
            app.navigateMainPage();
        });

        // vertical list of menu buttons and stats
        buttonChoices = new VBox(
                rulesButton,
                oddsButton,
                exitButton,
                spotsPane
        );
        buttonChoices.setAlignment(Pos.CENTER_LEFT);
        buttonChoices.setAlignment(Pos.CENTER_LEFT);
        buttonChoices.setSpacing(10);

        // apply consistent style to each button in the list
        for (Node button : buttonChoices.getChildren()) {
            if(button instanceof Button ) {
                Button b =  (Button) button;
                b.setStyle(buttonStyle);
            }
        }
        continueButton.setStyle(buttonStyle);


        // combine side buttons, number grid, and matches panel
        buttonChoicesAndGrid = new HBox(buttonChoices, numberGrid, matchesPane);
        buttonChoicesAndGrid.setAlignment(Pos.CENTER);
        buttonChoicesAndGrid.setSpacing(10);

        // bottom stats and continue button row
        bottomRow = new HBox(totalPrizesPane, roundMatchesPane, roundEarningsPane, currRoundPane, continueButton);
        bottomRow.setPadding(new Insets(20));
        bottomRow.setSpacing(10.0);
        bottomRow.setAlignment(Pos.CENTER);

        // lock panels to consistent sizes
        roundMatchesPane.setCollapsible(false);
        roundMatchesPane.setMaxWidth(125);
        roundMatchesPane.setMaxHeight(125);

        roundEarningsPane.setCollapsible(false);
        roundEarningsPane.setMaxWidth(125);
        roundEarningsPane.setMaxHeight(125);

        matchesPane.setMaxWidth(125);
        matchesPane.setMinHeight(390);

        totalPrizesPane.setCollapsible(false);
        totalPrizesPane.setMaxWidth(125);
        totalPrizesPane.setMaxHeight(125);

        currRoundPane.setCollapsible(false);
        currRoundPane.setMaxWidth(125);
        currRoundPane.setMaxHeight(125);

        // main vertical layout of page
        vBox = new VBox(mainMenuTitle, buttonChoicesAndGrid, bottomRow);
        vBox.setAlignment(Pos.CENTER);

        // overlay used for dimming background when showing info popups
        Rectangle overlay = new Rectangle(400, 400, Color.rgb(0,0,0,0.4));
        overlay.setVisible(false);

        // root container with background color
        BorderPane root = new BorderPane(vBox);
        ThemeManager.applyTheme(root);

        // info popups
        VBox rules = createInfoBox("Rules");
        VBox odds = createInfoBox("Odds");
        StackPane stack = new StackPane(root, rules, odds);
        StackPane.setAlignment(rules, Pos.CENTER);
        StackPane.setAlignment(odds, Pos.CENTER);
        rules.setVisible(false);
        odds.setVisible(false);

        // open info panels on click
        rulesButton.setOnAction(e -> showInfo(rules, overlay));
        oddsButton.setOnAction(e -> showInfo(odds, overlay));

        // hide when overlay hovered
        overlay.setOnMouseEntered(e -> {
            hideInfo(rules, overlay);
            hideInfo(odds, overlay);
        });

        return new Scene(stack, 1100, 750);
    }

    // creates info box popup (rules or odds) with close button
    private VBox createInfoBox(String text) {
        Label info = new Label(text);
        info.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        Button clsoeButton = new Button("Close");

        TextFlow infoArea = new TextFlow();
        infoArea.setTextAlignment(TextAlignment.CENTER);


        // load different text based on which popup it is
        if (text.equals("Rules")) {
            Text t1 = new Text("Before Drawing: \n" +
                    "1. You must choose whether you want to select 1, 4, 8, or 10 spots. This cannot change once the first round begins.\n" +
                    "2. You must choose how many drawings you will bet your card. This cannot change once the first round begins.\n" +
                    "3. You cannot select duplicate numbers or less or more than the number of spots that you have selected.\nYou can edit your selections as many times as you would like before playing.\n You may also have the computer choose your numbers for you.\n" +
                    "4. Press Start to begin playing!\n\n" + "Once Drawing:\n" +
                    "1. Spots: Shows what prizes will be given for how many spots matched\n" +
                    "2. Press Continue to move to the next drawing.\n" +
                    "3. Rounds: Shows how many rounds have been played.\n");
            t1.setStyle("-fx-font-size: 14px;");
            infoArea.getChildren().addAll(t1);
        }
        else if (text.equals("Odds")) {
            Text t1 = new Text("Spots\t\tOdds\n\n");
            t1.setStyle("-fx-font-weight: bold;");

            Text t2 = new Text(
                    "1\t\t 1 in 4.00\n" +
                            "4\t\t 1 in 3.86\n" +
                            "8\t\t 1 in 9.77\n" +
                            "10\t\t 1 in 9.05\n");
            t2.setStyle("-fx-font-size: 14px;");
            infoArea.getChildren().addAll(t1, t2);
        }

        infoArea.setPrefSize(300,200);

        VBox infoBox = new VBox(10, info, infoArea, clsoeButton);
        infoBox.setPadding(new Insets(20));
        infoBox.setAlignment(Pos.CENTER);
        infoBox.setStyle("-fx-background-color: radial-gradient(center 50% 50%, radius 100%, #ffe100, #ff7700); -fx-border-radius: 5; -fx-padding: 15; -fx-alignment: center; -fx-effect: dropshadow(gaussian, #481807, 5, 0, 0, 1)");


                clsoeButton.setOnAction(e -> hideInfo(infoBox, null));
        return infoBox;
    }

    // fade-in animation for showing info popups
    private void showInfo(VBox info, Rectangle overlay) {
        if (overlay != null) {
            overlay.setVisible(true);
        }
        info.setVisible(true);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), info);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        if (overlay != null) {
            FadeTransition fadeInOverlay = new FadeTransition(Duration.millis(300), overlay);
            fadeInOverlay.setFromValue(0);
            fadeInOverlay.setToValue(1);
            fadeInOverlay.play();
        }
        fadeIn.play();
    }

    // fade-out animation for hiding info popups
    private void hideInfo(VBox info, Rectangle overlay) {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), info);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(ev -> info.setVisible(false));
        fadeOut.play();

        if (overlay != null) {
            FadeTransition fadeOutOverlay = new FadeTransition(Duration.millis(300), overlay);
            fadeOutOverlay.setFromValue(1);
            fadeOutOverlay.setToValue(0);
            fadeOutOverlay.setOnFinished(ev -> overlay.setVisible(false));
            fadeOutOverlay.play();
        }
    }

    // builds prize chart table depending on number of spots selected
    private HBox buildSpotTable(int numSpots) {
        HBox spotTable;
        VBox matchColumn;
        VBox prizeColumn;
        Label matchTitle;
        Label prizeTitle;
        ArrayList<Integer> matches;
        ArrayList<Integer> prizes;
        int i;

        spotTable = new HBox();
        spotTable.setAlignment(Pos.CENTER);
        spotTable.setSpacing(50);

        matchColumn = new VBox();
        prizeColumn = new VBox();

        matchColumn.setAlignment(Pos.CENTER);
        prizeColumn.setAlignment(Pos.CENTER);
        matchColumn.setSpacing(5);
        prizeColumn.setSpacing(5);

        // headers for columns
        matchTitle = new Label("MATCH");
        matchTitle.setStyle("-fx-text-fill: white; -fx-font-size: 18px;");
        prizeTitle = new Label("PRIZE");
        prizeTitle.setStyle("-fx-text-fill: white; -fx-font-size: 18px;");

        matchColumn.getChildren().add(matchTitle);
        prizeColumn.getChildren().add(prizeTitle);

        // fill match-prize pairs based on number of spots
        matches = new ArrayList<Integer>();
        prizes = new ArrayList<Integer>();

        // create label rows for each match and prize value
        if (numSpots == 1) {
            matches.add(1);
            prizes.add(2);
        }
        else if (numSpots == 4) {
            matches.add(2); prizes.add(1);
            matches.add(3); prizes.add(5);
            matches.add(4); prizes.add(75);
        }
        else if (numSpots == 8) {
            matches.add(4); prizes.add(2);
            matches.add(5); prizes.add(12);
            matches.add(6); prizes.add(50);
            matches.add(7); prizes.add(750);
            matches.add(8); prizes.add(10000);
        }
        else {
            matches.add(0); prizes.add(5);
            matches.add(5); prizes.add(2);
            matches.add(6); prizes.add(15);
            matches.add(7); prizes.add(40);
            matches.add(8); prizes.add(450);
            matches.add(9); prizes.add(4250);
            matches.add(10); prizes.add(100000);
        }

        for (i = 0; i < matches.size(); i++) {
            Label matchLabel;
            Label prizeLabel;
            matchLabel = new Label(String.valueOf(matches.get(i)));
            prizeLabel = new Label("$" + prizes.get(i));
            matchLabel.setStyle("-fx-text-fill: white;");
            prizeLabel.setStyle("-fx-text-fill: white;");
            matchColumn.getChildren().add(matchLabel);
            prizeColumn.getChildren().add(prizeLabel);
        }

        spotTable.getChildren().addAll(matchColumn, prizeColumn);
        return spotTable;
    }

    // builds vertical list showing numbers drawn with color highlighting
    private VBox buildDrawnNumbersTable(ArrayList<Integer> drawnNumbers) {
        VBox drawnBox = new VBox();
        drawnBox.setAlignment(Pos.TOP_CENTER);
        PauseTransition pause = new PauseTransition(Duration.seconds(0.25));
        Iterator<Integer> iterator  = drawnNumbers.iterator();
        ArrayList<Integer> selectedNumbers = game.getBettings().getSelectedNumbers();

        // reveal drawn numbers gradually using small delay
        pause.setOnFinished(event -> {
            if (iterator.hasNext()) {
                Integer num =  iterator.next();
                Label numberLabel = new Label(String.valueOf(num));

                // color green if player picked that number
                if (selectedNumbers.contains(num)) {
                    numberLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #ff7b00;");
                } else {
                    numberLabel.setStyle("-fx-text-fill: black;");
                }
                drawnBox.getChildren().add(numberLabel);
                pause.play();
            }
        });

        pause.play();

        return drawnBox;
    }

    // animates grid color changes as drawn numbers appear
    private void updateDrawnAndMatchedNumbers(ArrayList<Integer> drawnNumbers, ArrayList<Integer> selectedNumbers) {
        Iterator<Integer> iterator = drawnNumbers.iterator();

        PauseTransition pause = new PauseTransition(Duration.seconds(0.25));

        pause.setOnFinished(event -> {
            if (iterator.hasNext()) {
                int num = iterator.next();

                // loop through 80 grid buttons
                for (javafx.scene.Node node : numberGrid.getChildren()) {
                    if (node instanceof ToggleButton) {
                        ToggleButton button = (ToggleButton) node;
                        int buttonNum = Integer.parseInt(button.getText());

                        // color drawn numbers teal, matched numbers bright green
                        if (buttonNum == num) {
                            button.setStyle("-fx-background-color: #6cdfb7; -fx-font-weight: bold;");
                            if (selectedNumbers.contains(num)) {
                                button.setStyle("-fx-background-color: #3fb90a; -fx-font-weight: bold;");
                            }
                        }
                    }
                }

                PauseTransition nextPause = new PauseTransition(Duration.seconds(0.25));
                nextPause.setOnFinished(event2 -> pause.getOnFinished().handle(null));
                nextPause.play();
            }
        });

        pause.play();
    }

    // resets button colors to default pink or blue for selected ones
    private void resetButtonColors() {
        for (javafx.scene.Node node : numberGrid.getChildren()) {
            if (node instanceof ToggleButton) {
                ToggleButton button = (ToggleButton) node;
                button.setStyle(" -fx-background-color: radial-gradient(center 50% 50%, radius 50%, #ffcdf0, #e250b7);");

                int num = Integer.parseInt(button.getText());
                if (game.getBettings().getSelectedNumbers().contains(num)) {
                    button.setStyle("-fx-background-color: #505ae2; -fx-font-weight: bold;");
                }
            }
        }
    }

    // updates continue/start/finish button text based on round progression
    private void updateActionButtonLabel() {
        int total = game.getTotalDraws();
        int current = game.getCurrRound();

        // logic table mapping draw progress to button text
        if (total == 1) {
            if (current == 0) {
                continueButton.setText("Play");
            }
            else {
                continueButton.setText("Summary");
            }
        }
        else if (total == 2) {
            if (current == 0) {
                continueButton.setText("Start");
            }
            else if (current == 1) {
                continueButton.setText("Finish");
            }
            else {
                continueButton.setText("Summary");
            }
        }
        else if (total == 3) {
            if (current == 0) {
                continueButton.setText("Start");
            }
            else if (current == 1) {
                continueButton.setText("Continue");
            }
            else if (current == 2) {
                continueButton.setText("Finish");
            }
            else {
                continueButton.setText("Summary");
            }
        }
        else {
            if (current == 0) {
                continueButton.setText("Start");
            }
            else if (current == 1 || current == 2) {
                continueButton.setText("Continue");
            }
            else if (current == 3) {
                continueButton.setText("Finish");
            }
            else {
                continueButton.setText("Summary");
            }
        }
    }

    // handles what happens when the player clicks continue/start/play
    private void onSelectContinueButton() {
        continueButton.setDisable(true); // disable the button immediately to prevent spam clicking
        PauseTransition cooldown = new PauseTransition(Duration.seconds(5.5)); // create a small cooldown timer so the button can’t be pressed again too fast

        // after cooldown, re-enable the button so player can click again
        cooldown.setOnFinished(event -> {
            continueButton.setDisable(false);
        });
        cooldown.play();

        // if the button currently says "summary", it means the game has finished all rounds
        // so navigate to the final results / summary page instead of running another draw
        if (continueButton.getText().equals("Summary")) {
            app.navigateFinishPage();
            return; // exit early, do not proceed to drawing logic
        }

        // only run a new drawing if the current round is less than total rounds allowed
        if (game.getCurrRound() < game.getTotalDraws()) {
            resetButtonColors(); // reset colors of number buttons before showing new results
            game.runDrawing(); // run the drawing logic to generate 20 new numbers and calculate matches
            updateScoreDisplay(); // refresh ui elements with updated scores, prizes, and round values
        }
    }

    // updates all UI labels and grids after each round or drawing
    private void updateScoreDisplay() {
        // wait 5.5 seconds before revealing new score information (matches, earnings, etc.)
        PauseTransition delay = new PauseTransition(Duration.seconds(5.5));

        currRoundValue.setText(game.getCurrRound() + "/" + game.getTotalDraws()); // update current round index display
        matchesPane.setContent(buildDrawnNumbersTable(game.getDrawing().getDrawnNumbers())); // rebuild the drawn numbers list visually inside matches pane
        updateDrawnAndMatchedNumbers(game.getDrawing().getDrawnNumbers(), game.getBettings().getSelectedNumbers()); // highlight drawn and matched numbers on the 80-number grid
        updateActionButtonLabel(); // update continue/start/finish/summary button label text
        roundMatchesValue.setText("0");
        roundEarningsValue.setText("$0");

        // after delay, update round statistics and total prizes
        delay.setOnFinished(event -> {
            roundMatchesValue.setText(String.valueOf(game.getCurrRoundMatches())); // update how many numbers matched this round
            roundEarningsValue.setText("$" + game.getCurrRoundEarnings()); // update prize earned this round
            totalPrizesValue.setText("$" + game.getTotalWinnings()); // update total winnings across all rounds
        });

        delay.play(); // start the delay timer
    }

    // resets all ui components and game data when exiting play page
    private void resetPage() {
        game = new Game(new Bettings(), new Drawing()); // create a fresh empty game object so old data doesn’t persist

        // loop through all grid buttons (1–80)
        for (javafx.scene.Node node : numberGrid.getChildren()) {
            if (node instanceof ToggleButton) {
                ToggleButton button = (ToggleButton) node;
                button.setSelected(false); // clear button state so nothing remains selected
                button.setDisable(true); // disable the buttons so user can’t interact until a new game starts
            }
        }
    }
}