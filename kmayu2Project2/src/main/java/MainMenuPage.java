import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class MainMenuPage {
    private KenoApp app; // handles navigation between pages

    public MainMenuPage(KenoApp app) {
        this.app = app;
    }
    // logo
    Image logoImage = new Image(getClass().getResourceAsStream("keno_logo.png"));
    ImageView logoView = new ImageView(logoImage);


    // initialize all menu actions
    Button rulesButton;
    Button oddsButton;
    Button exitButton;
    Button playButton;

    // labels for title and subtitle
    Label mainMenuTitle;
    Label mainMenuSubTitle;

    // layout containers
    HBox buttonChoices; // holds Rules, Odds, and Exit horizontally
    VBox vBox; // main vertical layout for all components

    public Scene buildScene() {
        rulesButton = new Button("Rules");
        oddsButton = new Button("Odds");
        exitButton = new Button("Exit");
        playButton = new Button("Play");

        // shared styling for red gradient buttons
        String buttonStyle = "-fx-background-color: linear-gradient(to bottom, #cb5a31, #c32121); -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 8;-fx-border-radius: 8;-fx-padding: 8 16 8 16; -fx-background-radius: 20; -fx-border-radius: 20;";

        // horizontal layout for smaller buttons
        buttonChoices = new HBox(rulesButton, oddsButton, exitButton);
        buttonChoices.setAlignment(Pos.CENTER);
        buttonChoices.setSpacing(30);
        buttonChoices.setPadding(new Insets(10));

        // apply styles to buttons
        rulesButton.setStyle(buttonStyle);
        oddsButton.setStyle(buttonStyle);
        exitButton.setStyle(buttonStyle);

        // special style for the play button (purple gradient)
        playButton.setStyle("-fx-background-color: linear-gradient(to bottom, #8a85ef, #231d8f); -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 16px; -fx-background-radius: 8;-fx-border-radius: 8;-fx-padding: 8 16 8 16; -fx-background-radius: 20; -fx-border-radius: 20;");

        // set button widths for consistency
        double buttonWidth = 120;
        rulesButton.setPrefWidth(buttonWidth);
        oddsButton.setPrefWidth(buttonWidth);
        exitButton.setPrefWidth(buttonWidth);
        playButton.setPrefWidth(170);


        // main vertical container for title, subtitle, button row, and play button
        logoView.setFitHeight(200);
        logoView.setFitWidth(200);
        logoView.setPreserveRatio(true);
        vBox = new VBox(logoView, buttonChoices, playButton);
        vBox.setAlignment(Pos.CENTER);

        // semi-transparent overlay used when rules/odds info boxes are visible
        Rectangle overlay = new Rectangle(400, 400, Color.rgb(0,0,0,0.4));
        overlay.setVisible(false);
        BorderPane root = new BorderPane(vBox);
        root.setStyle("-fx-background-color: radial-gradient(center 50% 50%, radius 100%, #ffe100, #ff7700);");

        // create hidden info boxes for rules and odds pop-ups
        VBox rules = createInfoBox("Rules");
        VBox odds = createInfoBox("Odds");
        StackPane stack = new StackPane(root, rules, odds);
        StackPane.setAlignment(rules, Pos.CENTER);
        StackPane.setAlignment(odds, Pos.CENTER);
        rules.setVisible(false);
        odds.setVisible(false);

        // button behaviors
        rulesButton.setOnAction(e -> showInfo(rules, overlay));
        oddsButton.setOnAction(e -> showInfo(odds, overlay));
        exitButton.setOnAction(e -> {
            System.exit(0);
        });
        playButton.setOnAction(e -> {
            app.navigateSelectionPage();
        });

        overlay.setOnMouseEntered(e -> {
            hideInfo(rules, overlay);
            hideInfo(odds, overlay);
        });

        return new Scene(stack, 1100, 750);
    }

    // helper to create an info box with a close button
    private VBox createInfoBox(String text) {
        Label info = new Label(text);
        info.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
        Button clsoeButton = new Button("Close");

        TextFlow infoArea = new TextFlow();
        infoArea.setTextAlignment(TextAlignment.CENTER);

        // fill in the content based on whether it’s Rules or Odds
        if(text.equals("Rules")) {
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

        infoArea.setPrefSize(400,300);

        // wrap title, text area, and close button in a vertical layout
        VBox infoBox = new VBox(10, info, infoArea, clsoeButton);
        infoBox.setPadding(new Insets(20));
        infoBox.setAlignment(Pos.CENTER);
        infoBox.setStyle("-fx-background-color: radial-gradient(center 50% 50%, radius 100%, #ffe100, #ff7700); -fx-border-radius: 5; -fx-padding: 15; -fx-alignment: center; -fx-effect: dropshadow(gaussian, #481807, 5, 0, 0, 1)");

        // close button hides the info box (handled below)
        clsoeButton.setOnAction(e -> hideInfo(infoBox, null));
        return infoBox;
    }

    // helper methods for fade in and fade out transition
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

}
