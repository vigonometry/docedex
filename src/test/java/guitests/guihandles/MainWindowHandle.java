package guitests.guihandles;

import javafx.stage.Stage;

/**
 * Provides a handle for {@code MainWindow}.
 */
public class MainWindowHandle extends StageHandle {

    private final DoctorListPanelHandle doctorListPanel;
    private final PatientListPanelHandle patientListPanel;
    private final ResultDisplayHandle resultDisplay;
    private final CommandBoxHandle commandBox;
    private final StatusBarFooterHandle statusBarFooter;
    private final MainMenuHandle mainMenu;

    /**
     * Constructs a {@code MainWindowHandle} with the given {@code stage}.
     * @param stage
     */
    public MainWindowHandle(Stage stage) {
        super(stage);

        doctorListPanel = new DoctorListPanelHandle(getChildNode(DoctorListPanelHandle.DOCTOR_LIST_VIEW_ID));
        patientListPanel = new PatientListPanelHandle(getChildNode(PatientListPanelHandle.PATIENT_LIST_VIEW_ID));
        resultDisplay = new ResultDisplayHandle(getChildNode(ResultDisplayHandle.RESULT_DISPLAY_ID));
        commandBox = new CommandBoxHandle(getChildNode(CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        statusBarFooter = new StatusBarFooterHandle(getChildNode(StatusBarFooterHandle.STATUS_BAR_PLACEHOLDER));
        mainMenu = new MainMenuHandle(getChildNode(MainMenuHandle.MENU_BAR_ID));
    }

    public DoctorListPanelHandle getDoctorListPanel() {
        return doctorListPanel;
    }

    public PatientListPanelHandle getPatientListPanel() {
        return patientListPanel;
    }

    public ResultDisplayHandle getResultDisplay() {
        return resultDisplay;
    }

    public CommandBoxHandle getCommandBox() {
        return commandBox;
    }

    public StatusBarFooterHandle getStatusBarFooter() {
        return statusBarFooter;
    }

    public MainMenuHandle getMainMenu() {
        return mainMenu;
    }

}
