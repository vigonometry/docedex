package systemtests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertDoctorListMatching;
import static seedu.address.ui.testutil.GuiTestAssert.assertPatientListMatching;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.DoctorListPanelHandle;
import guitests.guihandles.MainMenuHandle;
import guitests.guihandles.MainWindowHandle;
import guitests.guihandles.PatientListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.StatusBarFooterHandle;
import seedu.address.TestApp;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.FindDoctorCommand;
import seedu.address.logic.commands.FindPatientCommand;
import seedu.address.logic.commands.ListDoctorCommand;
import seedu.address.logic.commands.ListPatientCommand;
import seedu.address.logic.commands.SelectDoctorCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.testutil.TestUtil;
import seedu.address.testutil.TypicalDoctors;
import seedu.address.ui.CommandBox;

/**
 * A system test class for AddressBook, which provides access to handles of GUI components and helper methods
 * for test verification.
 */
public abstract class AddressBookSystemTest {
    // TODO: Remove this workaround after using JavaFX version 13 or above
    // This is a workaround to solve headless test failure on Windows OS
    // Refer to https://github.com/javafxports/openjdk-jfx/issues/66 for more details.
    static {
        if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
            System.loadLibrary("WindowsCodecs");
        }
    }


    private static final Path SAVE_LOCATION_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("sampleData.json");
    private static final Path PREF_LOCATION_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("pref_testing.json");

    private static final List<String> COMMAND_BOX_DEFAULT_STYLE = Arrays.asList("text-input", "text-field");
    private static final List<String> COMMAND_BOX_ERROR_STYLE =
            Arrays.asList("text-input", "text-field", CommandBox.ERROR_STYLE_CLASS);

    private MainWindowHandle mainWindowHandle;
    private TestApp testApp;
    private SystemTestSetupHelper setupHelper;

    @BeforeAll
    public static void setupBeforeClass() {
        SystemTestSetupHelper.initialize();
    }

    @BeforeEach
    public void setUp() {
        setupHelper = new SystemTestSetupHelper();
        testApp = setupHelper.setupApplication(this::getInitialData, getDataFileLocation(),
                getPrefFileLocation());
        mainWindowHandle = setupHelper.setupMainWindowHandle();

        assertApplicationStartingStateIsCorrect();
    }

    @AfterEach
    public void tearDown() {
        setupHelper.tearDownStage();
    }

    /**
     * Returns the data to be loaded into the file in {@link #getDataFileLocation()}.
     */
    protected AddressBook getInitialData() {
        return TypicalDoctors.getTypicalAddressBook();
    }

    /**
     * Returns the directory of the data file.
     */
    protected Path getDataFileLocation() {
        return SAVE_LOCATION_FOR_TESTING;
    }

    protected Path getPrefFileLocation() {
        return PREF_LOCATION_FOR_TESTING;
    }

    public MainWindowHandle getMainWindowHandle() {
        return mainWindowHandle;
    }

    public CommandBoxHandle getCommandBox() {
        return mainWindowHandle.getCommandBox();
    }

    public DoctorListPanelHandle getDoctorListPanel() {
        return mainWindowHandle.getDoctorListPanel();
    }

    public PatientListPanelHandle getPatientListPanel() {
        return mainWindowHandle.getPatientListPanel();
    }

    public MainMenuHandle getMainMenu() {
        return mainWindowHandle.getMainMenu();
    }


    public StatusBarFooterHandle getStatusBarFooter() {
        return mainWindowHandle.getStatusBarFooter();
    }

    public ResultDisplayHandle getResultDisplay() {
        return mainWindowHandle.getResultDisplay();
    }

    /**
     * Executes {@code command} in the application's {@code CommandBox}.
     * Method returns after UI components have been updated.
     */
    protected void executeCommand(String command) {
        rememberStates();
        // Injects a fixed clock before executing a command so that the time stamp shown in the status bar
        // after each command is predictable and also different from the previous command.
        mainWindowHandle.getCommandBox().run(command);

    }

    /**
     * Displays all doctors in the address book.
     */
    protected void showAllDoctors() {
        executeCommand(ListDoctorCommand.COMMAND_WORD);
        assertEquals(getModel().getAddressBook().getDoctorList().size(), getModel().getFilteredDoctorList().size());
    }

    /**
     * Displays all patients in the address book.
     */
    protected void showAllPatients() {
        executeCommand(ListPatientCommand.COMMAND_WORD);
        assertEquals(getModel().getAddressBook().getPatientList().size(), getModel().getFilteredPatientList().size());
    }

    /**
     * Displays all doctors with any parts of their names matching {@code keyword} (case-insensitive).
     */
    protected void showDoctorsWithName(String keyword) {
        executeCommand(FindDoctorCommand.COMMAND_WORD + " " + keyword);
        assertTrue(getModel().getFilteredDoctorList().size() <= getModel().getAddressBook().getDoctorList().size());
    }

    /**
     * Displays all patients with any parts of their names matching {@code keyword} (case-insensitive).
     */
    protected void showPatientsWithName(String keyword) {
        executeCommand(FindPatientCommand.COMMAND_WORD + " " + keyword);
        assertTrue(getModel().getFilteredPatientList().size() <= getModel().getAddressBook().getPatientList().size());
    }

    /**
     * Selects the doctor at {@code index} of the displayed list.
     */
    protected void selectDoctor(Index index) {
        executeCommand(SelectDoctorCommand.COMMAND_WORD + " " + index.getOneBased());
        assertEquals(index.getZeroBased(), getDoctorListPanel().getSelectedCardIndex());
    }

    /**
     * Deletes all persons in the address book.
     */
    protected void deleteAllDoctors() {
        executeCommand(ClearCommand.COMMAND_WORD);
        assertEquals(0, getModel().getAddressBook().getDoctorList().size());
    }


    /**
     * Deletes all persons in the address book.
     */
    protected void deleteAllPatients() {
        executeCommand(ClearCommand.COMMAND_WORD);
        assertEquals(0, getModel().getAddressBook().getPatientList().size());
    }

    /**
     * Asserts that the {@code CommandBox} displays {@code expectedCommandInput}, the {@code ResultDisplay} displays
     * {@code expectedResultMessage}, the storage contains the same person objects as {@code expectedModel}
     * and the person list panel displays the persons in the model correctly.
     */
    protected void assertApplicationDisplaysExpected(String expectedCommandInput, String expectedResultMessage,
                                                     Model expectedModel) {
        assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());
        assertEquals(new AddressBook(expectedModel.getAddressBook()), testApp.readStorageAddressBook());
        assertDoctorListMatching(getDoctorListPanel(), expectedModel.getFilteredDoctorList());;
    }

    /**
     * Calls {@code BrowserPanelHandle}, {@code PersonListPanelHandle} and {@code StatusBarFooterHandle} to remember
     * their current state.
     */
    private void rememberStates() {
        StatusBarFooterHandle statusBarFooterHandle = getStatusBarFooter();
        statusBarFooterHandle.rememberSaveLocation();
        getDoctorListPanel().rememberSelectedDoctorCard();
    }

    /**
     * Asserts that the previously selected doctor card is now deselected.
     */
    protected void assertSelectedDoctorCardDeselected() {
        assertFalse(getDoctorListPanel().isAnyCardSelected());
    }

    /**
     * Asserts that the previously selected patient card is now deselected.
     */
    protected void assertSelectedPatientCardDeselected() {
        assertFalse(getPatientListPanel().isAnyCardSelected());
    }


    /**
     * Asserts that the command box's shows the default style.
     */
    protected void assertCommandBoxShowsDefaultStyle() {
        assertEquals(COMMAND_BOX_DEFAULT_STYLE, getCommandBox().getStyleClass());
    }

    /**
     * Asserts that the command box's shows the error style.
     */
    protected void assertCommandBoxShowsErrorStyle() {
        assertEquals(COMMAND_BOX_ERROR_STYLE, getCommandBox().getStyleClass());
    }

    /**
     * Asserts that the entire status bar remains the same.
     */
    protected void assertStatusBarUnchanged() {
        StatusBarFooterHandle handle = getStatusBarFooter();
        assertFalse(handle.isSaveLocationChanged());
    }


    /**
     * Asserts that the starting state of the application is correct.
     */
    private void assertApplicationStartingStateIsCorrect() {
        assertEquals("", getCommandBox().getInput());
        assertEquals("", getResultDisplay().getText());
        assertDoctorListMatching(getDoctorListPanel(), getModel().getFilteredDoctorList());
        assertPatientListMatching(getPatientListPanel(), getModel().getFilteredPatientList());
        assertEquals(Paths.get(".").resolve(testApp.getStorageSaveLocation()).toString(),
                getStatusBarFooter().getSaveLocation());
    }

    /**
     * Returns a defensive copy of the current model.
     */
    protected Model getModel() {
        return testApp.getModel();
    }
}
