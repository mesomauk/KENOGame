import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class KenoApp extends Application {
    private Stage primaryStage;
    private Scene mainScene;
    private Scene selectionScene;
    private Scene playScene;
    private Game game;

    public void  start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.game = new Game(new Bettings(), new Drawing());

        MainMenuPage mainMenuPage = new MainMenuPage(this);
        SelectionPage selectionPage = new SelectionPage(this, game);

        mainScene = mainMenuPage.buildScene();
        selectionScene = selectionPage.buildScene();

        primaryStage.setTitle("Keno");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public void navigateMainPage() {
        resetGame();
        primaryStage.setScene(mainScene);
    }

    public void navigateSelectionPage() {
        resetGame();
        SelectionPage selectionPage = new SelectionPage(this, game);
        selectionScene = selectionPage.buildScene();
        primaryStage.setScene(selectionScene);
    }

    public void navigatePlayPage() {
        PlayPage playPage = new PlayPage(this, game);
        playScene = playPage.buildScene();
        primaryStage.setScene(playScene);
    }

    public void navigateFinishPage() {
        FinishPage finishPage = new FinishPage(this, game);
        Scene finishScene = finishPage.buildScene();
        primaryStage.setScene(finishScene);
    }

    public void resetGame() {
        this.game = new Game(new Bettings(), new Drawing());
    }

    public static void main(String[] args) {
        launch(args);
    }
}