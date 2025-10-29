import javafx.animation.FadeTransition;
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

public class SelectionPage {
    private KenoApp app; // handles navigation between pages
    private Game game; // current game object holding data

    // UI components
    Label mainMenuTitle;

    Button rulesButton;
    Button oddsButton;
    Button exitButton;
    Button layoutButton;
    ComboBox<String> layoutDropdown;

    ComboBox<Integer> spotsDropdown;
    ComboBox<Integer> drawingsDropdown;

    Button autoPickButton;
    Button clearButton;
    Button startButton;

    GridPane numberGrid = new GridPane(); // grid of 80 toggle buttons

    VBox buttonChoices;
    HBox buttonChoicesAndGrid;
    VBox vBox;

    public SelectionPage(KenoApp kenoApp, Game game) {
        this.app = kenoApp;
        this.game = game;
    }

    public Scene buildScene() {
        mainMenuTitle = new Label("KENO");
        mainMenuTitle.setStyle("-fx-font-size: 28px");

        // initialize main labels and buttons
        rulesButton = new Button("Rules");
        oddsButton = new Button("Odds");
        exitButton = new Button("Exit");
        layoutButton = new Button("Layout");

        // combo boxes for spots, drawings, and layout theme
        spotsDropdown = new ComboBox<>();
        drawingsDropdown = new ComboBox<>();
        layoutDropdown = new ComboBox<>();

        // buttons for autopick, clear, and start
        autoPickButton = new Button("Auto Pick");
        clearButton = new Button("Clear");
        startButton = new Button("Ready");

        // define consistent button sizes
        double buttonWidth = 100;
        double buttonHeight = 40;

        rulesButton.setPrefSize(buttonWidth, buttonHeight);
        oddsButton.setPrefSize(buttonWidth, buttonHeight);
        exitButton.setPrefSize(buttonWidth, buttonHeight);
        layoutButton.setPrefSize(buttonWidth, buttonHeight);
        spotsDropdown.setPrefSize(buttonWidth, buttonHeight);
        drawingsDropdown.setPrefSize(buttonWidth, buttonHeight);
        layoutDropdown.setPrefSize(buttonWidth, buttonHeight);
        autoPickButton.setPrefSize(buttonWidth, buttonHeight);
        clearButton.setPrefSize(buttonWidth, buttonHeight);
        startButton.setPrefSize(buttonWidth + 20, buttonHeight + 5);

        // header label + dropdown for spots
        Label spotsHeader = new Label("Spots");
        spotsHeader.setAlignment(Pos.CENTER);
        spotsHeader.setStyle(
                "-fx-text-fill: #ffffff;" +
                        "-fx-font-weight: bolder;" +
                        "-fx-font-size: 16px;"
        );
        VBox spotsPane = new VBox(2, spotsHeader, spotsDropdown);
        spotsPane.setAlignment(Pos.CENTER);

        // header label + dropdown for drawings
        Label drawingsHeader = new Label("Drawings");
        drawingsHeader.setAlignment(Pos.CENTER);
        drawingsHeader.setStyle("-fx-text-fill: #ffffff; -fx-font-weight: bolder; -fx-font-size: 16px;");
        VBox drawingsPane = new VBox(2, drawingsHeader, drawingsDropdown);
        drawingsPane.setAlignment(Pos.CENTER);

        // header label + dropdown for layout theme
        Label layoutHeader = new Label("Layout");
        layoutHeader.setAlignment(Pos.CENTER);
        layoutHeader.setStyle("-fx-text-fill: #ffffff; -fx-font-weight: bolder; -fx-font-size: 16px;");
        VBox layoutPane = new VBox(2, layoutHeader, layoutDropdown);
        layoutPane.setAlignment(Pos.CENTER);

        // create 10x8 grid for number buttons (1–80)
        int number = 1;
        for (int r = 0; r < 10; r++){
            for (int c = 0; c < 8; c++){
                ToggleButton numberButton = new ToggleButton(String.valueOf(number));
                numberButton.setPrefWidth(40);
                numberButton.setPrefHeight(40);
                numberButton.setStyle(" -fx-background-color: radial-gradient(center 50% 50%, radius 50%, #ffcdf0, #e250b7);");
                numberButton.setDisable(true);
                int num = number;

                // attach event for when a number is clicked
                numberButton.setOnAction(event -> {
                    onSelectNumberButton(numberButton, num);
                });

                numberGrid.add(numberButton, c, r);
                number++;
            }
        }
        numberGrid.setHgap(8);
        numberGrid.setVgap(8);

        // event handlers for all interactive components
        exitButton.setOnAction(event -> {
            resetPage();
            app.navigateMainPage();
        });

        spotsDropdown.setOnAction(event -> {
            onSelectClearButton();
            onSelectSpotsDropDown();
        });

        drawingsDropdown.setOnAction(event -> {
            onSelectDrawingsDropDown();
        });

        autoPickButton.setOnAction(event -> {
            onSelectAutoPickButton();
        });

        clearButton.setOnAction(event -> {
            onSelectClearButton();
        });

        startButton.setOnAction(event -> {
            onSelectStartButton();
        });

        // populate dropdown options
        spotsDropdown.getItems().addAll(1, 4, 8, 10);
        drawingsDropdown.getItems().addAll(1, 2, 3, 4);
        layoutDropdown.getItems().addAll("Casino", "Halloween", "Christmas", "Thanksgiving", "Valentine's");

        startButton.setDisable(true); // initially disabled until valid selections

        // sidebar containing all control buttons
        buttonChoices = new VBox(
                rulesButton,
                oddsButton,
                exitButton,
                spotsPane,
                drawingsPane,
                layoutPane,
                autoPickButton,
                clearButton
        );

        // style for sidebar buttons
        String buttonStyle = "-fx-background-color: linear-gradient(to bottom, #cb5a31, #c32121); -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 8;-fx-border-radius: 8;";
        buttonChoices.setAlignment(Pos.CENTER_LEFT);
        buttonChoices.setSpacing(10);

        // apply same style to all buttons in sidebar
        for (Node button : buttonChoices.getChildren()) {
            if(button instanceof Button ) {
                Button b =  (Button) button;
                b.setStyle(buttonStyle);
            }
        }

        // apply same style to dropdowns
        spotsDropdown.setStyle(buttonStyle);
        drawingsDropdown.setStyle(buttonStyle);
        layoutDropdown.setStyle(buttonStyle);
        startButton.setStyle(buttonStyle);

        // layout for sidebar and number grid horizontally
        buttonChoicesAndGrid = new HBox(buttonChoices, numberGrid);
        buttonChoicesAndGrid.setAlignment(Pos.CENTER);
        buttonChoicesAndGrid.setSpacing(10);

        // main vertical layout containing title, grid section, and start button
        vBox = new VBox(mainMenuTitle, buttonChoicesAndGrid, startButton);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);

        // overlay background for info popup
        Rectangle overlay = new Rectangle(400, 400, Color.rgb(0,0,0,0.4));
        overlay.setVisible(false);

        // root container with gradient background
        BorderPane root = new BorderPane(vBox);
        layoutDropdown.setOnAction(event -> {
            onSelectLayoutButton(root);
        });
        root.setStyle("-fx-background-color: radial-gradient(center 50% 50%, radius 100%, #ffe100, #ff7700);");

        // info popup boxes for rules and odds
        VBox rules = createInfoBox("Rules");
        VBox odds = createInfoBox("Odds");
        StackPane stack = new StackPane(root, rules, odds);
        StackPane.setAlignment(rules, Pos.CENTER);
        StackPane.setAlignment(odds, Pos.CENTER);
        rules.setVisible(false);
        odds.setVisible(false);

        // show/hide info popups
        rulesButton.setOnAction(e -> showInfo(rules, overlay));
        oddsButton.setOnAction(e -> showInfo(odds, overlay));
        overlay.setOnMouseEntered(e -> {
            hideInfo(rules, overlay);
            hideInfo(odds, overlay);
        });

        return new Scene(stack, 1100, 750);
    }

    // helper to create info popup for rules or odds
    private VBox createInfoBox(String text) {
        Label info = new Label(text);
        info.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        Button clsoeButton = new Button("Close");

        TextFlow infoArea = new TextFlow();
        infoArea.setTextAlignment(TextAlignment.CENTER);

        // rules text content
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
        // odds text content
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


                // wrap title, text area, and close button
        clsoeButton.setOnAction(e -> hideInfo(infoBox, null));
        return infoBox;
    }

    // helper for fade in animation
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

    // helper for fade out animation
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

    // changes color scheme when layout theme is selected
    private void onSelectLayoutButton(BorderPane root) {
        String theme = layoutDropdown.getValue();

        String buttonStyle;
        String bgStyle;

        if (theme.equals("Christmas")) {
            buttonStyle = "-fx-background-color: linear-gradient(to bottom, #0e3610, #185c06); -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 8;-fx-border-radius: 8;";
            bgStyle = "-fx-background-color: radial-gradient(center 50% 50%, radius 100%, #de3535, #350a10)";
        }
        else if (theme.equals("Halloween")) {
            buttonStyle = "-fx-background-color: linear-gradient(to bottom, #ff6f00, #573c11); -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 8;-fx-border-radius: 8;";
            bgStyle = "-fx-background-color: radial-gradient(center 50% 50%, radius 100%, #89169b, #330f35)";

        }
        else if (theme.equals("Thanksgiving")) {
            buttonStyle = "-fx-background-color: linear-gradient(to bottom, #481807, #ffc300); -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 8;-fx-border-radius: 8;";
            bgStyle = "-fx-background-color: radial-gradient(center 50% 50%, radius 100%, #e76237, #6d2106)";

        }
        else if (theme.equals("Valentine's")) {
            buttonStyle = "-fx-background-color: linear-gradient(to bottom, #991212, #ff0066); -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 8;-fx-border-radius: 8;";
            bgStyle = "-fx-background-color: radial-gradient(center 50% 50%, radius 100%, #e47cd7, #640840)";

        }
        else {
            buttonStyle = "-fx-background-color: linear-gradient(to bottom, #cb5a31, #c32121); -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 8;-fx-border-radius: 8;";
            bgStyle = "-fx-background-color: radial-gradient(center 50% 50%, radius 100%, #ffe100, #ff7700)";

        }


        ThemeManager.setButtonStyle(buttonStyle);
        ThemeManager.setBackgroundStyle(bgStyle);

        // Apply immediately to current page
        ThemeManager.applyTheme(root);

    }


    // handles player clicking a number on the grid
    private void onSelectNumberButton(ToggleButton button, int num) {
        // check if the button was just selected
        if (button.isSelected()) {
            // only allow adding if user hasn’t exceeded chosen number of spots
            if (game.getBettings().getNumSelectedNumbers() < game.getBettings().getNumSpots()) {
                button.setStyle("-fx-background-color: #505ae2;"); // change color to indicate this number is chosen
                game.getBettings().selectNumber(num); // add this number to the player's selected list

            }
            else {
                button.setSelected(false); // if player already picked the max amount of spots, undo
            }
        }
        else {
            game.getBettings().removeNumber(num); // if the button was deselected (user clicked again remove this number from selected list
            button.setStyle(" -fx-background-color: radial-gradient(center 50% 50%, radius 50%, #ffcdf0, #e250b7);"); // reset button color back to original pink gradient

        }
        startButton.setDisable(!updateStartButtonState()); // update start button availability (disable if setup incomplete)
    }

    // handles when the player selects how many spots they want to play (1, 4, 8, or 10)
    private void onSelectSpotsDropDown() {
        for (javafx.scene.Node node : numberGrid.getChildren()) {
            node.setDisable(false); // enable all grid buttons after a valid spots number is chosen
        }
        game.getBettings().setNumSpots(spotsDropdown.getValue()); // store selected number of spots into game state
        startButton.setDisable(!updateStartButtonState()); // check again if all game setup requirements are met before enabling start
    }

    // handles when player chooses how many total drawings they want (1–4)
    private void onSelectDrawingsDropDown() {
        game.setTotalDraws(drawingsDropdown.getValue()); // save total number of drawings into game state
        startButton.setDisable(!updateStartButtonState()); // check if start button can be enabled (spots, drawings, and numbers selected)
    }

    // fills random numbers automatically for the player using autopick
    private void onSelectAutoPickButton() {
        ArrayList<Integer> autoSelectedNumbers = game.getBettings().autoSelect(); // call autopick from bettings class to randomly select unique numbers
        for (javafx.scene.Node node : numberGrid.getChildren()) {
            if (node instanceof ToggleButton) {
                ToggleButton button = (ToggleButton) node;
                button.setStyle(" -fx-background-color: radial-gradient(center 50% 50%, radius 50%, #ffcdf0, #e250b7);"); // reset color of all buttons before highlighting picked ones
                int buttonNum = Integer.parseInt(button.getText()); // convert button text (1–80) to integer to compare
                if (autoSelectedNumbers.contains(buttonNum)) {
                    button.setSelected(true);
                    button.setStyle("-fx-background-color: #505ae2;"); // if this number is part of the random auto-picked list mark it as selected and color it blue
                }
            }
        }
        startButton.setDisable(!updateStartButtonState()); // recheck if all setup conditions are met so start can enable

    }

    // clears all selected numbers on the grid
    private void onSelectClearButton() {
        game.getBettings().clearNumbers(); // remove all chosen numbers from bettings list

        // reset each button visually and logically
        for (javafx.scene.Node node : numberGrid.getChildren()) {
            if (node instanceof ToggleButton) {
                ToggleButton button = (ToggleButton) node;
                button.setStyle(" -fx-background-color: radial-gradient(center 50% 50%, radius 50%, #ffcdf0, #e250b7);"); // restore pink background (unselected)
                button.setSelected(false); // unselect toggle state
            }
        }
        startButton.setDisable(true); // disable start button again since there are no valid numbers selected
    }

    // starts the actual game screen if all setup is valid
    private void onSelectStartButton() {
        app.navigatePlayPage(); // tell main app to switch to play page scene
    }

    // checks if all three conditions (spots, drawings, numbers) are ready
    private boolean updateStartButtonState() {
        boolean selectedSpots = game.getBettings().getNumSpots() > 0; // player must have chosen at least one valid spot count
        boolean selectedDrawings = game.getTotalDraws() > 0; // player must have chosen total number of drawings
        boolean selectedNumbers = game.getBettings().getNumSelectedNumbers() == game.getBettings().getNumSpots(); // player must have filled all their available spots with numbers

        return selectedSpots && selectedDrawings && selectedNumbers; // only when all are true will start button become active
    }

    // resets the selection page to its original state
    private void resetPage() {
        game = new Game(new Bettings(), new Drawing()); // create a fresh game object to reset all values

        // clear dropdown selections for spots and drawings
        spotsDropdown.setValue(null);
        drawingsDropdown.setValue(null);

        // reset all 80 grid buttons visually and logically
        for (javafx.scene.Node node : numberGrid.getChildren()) {
            if (node instanceof ToggleButton) {
                ToggleButton button = (ToggleButton) node;
                button.setSelected(false); // unselect any toggled button
                button.setDisable(true); // disable all buttons until user picks new spot amount
            }
        }
        startButton.setDisable(true); // disable start button because setup is cleared
    }
}