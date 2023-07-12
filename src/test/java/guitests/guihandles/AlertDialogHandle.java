package guitests.guihandles;

import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import seedu.address.ui.UiManager;

/**
 * A handle for the {@code AlertDialog} of the UI.
 */
public class AlertDialogHandle extends StageHandle {
    private final DialogPane dialogPane;

    /**
     * Constructs a {@code AlertDialogHandle} with the given {@code stage}.
     */
    public AlertDialogHandle(Stage stage) {
        super(stage);

        dialogPane = getChildNode("#" + UiManager.getAlertDialogPaneFieldId());
    }

    /**
     * Returns the text of the header in the {@code AlertDialog}.
     */
    public String getHeaderText() {
        return dialogPane.getHeaderText();
    }

    /**
     * Returns the text of the content in the {@code AlertDialog}.
     */
    public String getContentText() {
        return dialogPane.getContentText();
    }
}
