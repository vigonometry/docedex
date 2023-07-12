package guitests.guihandles;


import guitests.GuiRobot;
import javafx.stage.Stage;
import seedu.address.ui.HelpWindow;

/**
 * A handler for the {@code HelpWindow} of the UI.
 */
public class HelpWindowHandle extends StageHandle {

    private static final String HELP_WINDOW_TITLE = "Help";

    public HelpWindowHandle(Stage stage) {
        super(stage);
    }

    public String getUrl() {
        return HelpWindow.USERGUIDE_URL;
    }

    public String getHelpMessage() {
        return HelpWindow.HELP_MESSAGE;
    }

    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(HELP_WINDOW_TITLE);
    }

}
