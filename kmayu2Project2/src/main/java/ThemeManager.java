import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

// manages theme styles for background and button colors across app
public class ThemeManager {
    private static String buttonStyle = "";
    private static String backgroundStyle = "";

    // returns the current button style string
    public static String getButtonStyle() {
        return buttonStyle;
    }

    // updates the button style string
    public static void setButtonStyle(String style) {
        buttonStyle = style;
    }

    // updates the background style string
    public static void setBackgroundStyle(String style) {
        backgroundStyle = style;
    }

    // applies the stored theme styles to a given pane and its children
    public static void applyTheme(Pane root) {
        if (root == null) return;

        // apply background style if defined
        if (!backgroundStyle.isEmpty()) {
            root.setStyle(backgroundStyle);
        }

        // only apply button style to button nodes
        for (Node node : root.getChildren()) {
            if (node instanceof Button) {
                Button btn = (Button) node;
                if (!buttonStyle.isEmpty()) {
                    btn.setStyle(buttonStyle);
                }
            }
        }
    }
}
