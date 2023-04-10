package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.SPECIALTY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SPECIALTY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.YOE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.YOE_DESC_BOB;
import static seedu.address.testutil.TypicalDoctors.ALICE;
import static seedu.address.testutil.TypicalDoctors.AMY;
import static seedu.address.testutil.TypicalDoctors.BOB;
import static seedu.address.testutil.TypicalDoctors.CARL;
import static seedu.address.testutil.TypicalDoctors.ELLA;
import static seedu.address.testutil.TypicalDoctors.FIONA;
import static seedu.address.testutil.TypicalDoctors.KEYWORD_MATCHING_MEIER;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddDoctorCommand;
import seedu.address.model.Model;
import seedu.address.model.person.doctor.Doctor;
import seedu.address.testutil.DoctorBuilder;
import seedu.address.testutil.DoctorUtil;

public class AddDoctorCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void add() {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a person without tags to a non-empty address book, command with leading spaces and trailing spaces
         * -> added
         */
        Doctor toAdd = AMY;
        String command = "   " + AddDoctorCommand.COMMAND_WORD + " " + NAME_DESC_AMY + " " + PHONE_DESC_AMY + " "
                + EMAIL_DESC_AMY + " " + SPECIALTY_DESC_AMY + " " + YOE_DESC_AMY + " " + TAG_DESC_FRIEND + "   ";
        assertCommandSuccess(command, toAdd);


        /* Case: add a person with all fields same as another person in the address book except name -> added */
        toAdd = new DoctorBuilder(AMY).withName(VALID_NAME_BOB).build();
        command = AddDoctorCommand.COMMAND_WORD + " " + NAME_DESC_BOB + " " + PHONE_DESC_AMY + " "
                + EMAIL_DESC_AMY + " " + SPECIALTY_DESC_AMY + " " + YOE_DESC_AMY + " " + TAG_DESC_FRIEND + " ";
        assertCommandSuccess(command, toAdd);


        /* Case: add to empty address book -> added */
        deleteAllDoctors();
        assertCommandSuccess(ALICE);

        /* Case: add a person with tags, command with parameters in random order -> added */
        toAdd = BOB;
        command = AddDoctorCommand.COMMAND_WORD + TAG_DESC_FRIEND + YOE_DESC_BOB + PHONE_DESC_BOB + SPECIALTY_DESC_BOB + NAME_DESC_BOB
                + TAG_DESC_HUSBAND + EMAIL_DESC_BOB;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person, missing tags -> added */
        assertCommandSuccess(ELLA);

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the person list before adding -> added */
        showDoctorsWithName(KEYWORD_MATCHING_MEIER);
        assertCommandSuccess(FIONA);

        /* ------------------------ Perform add operation while a person card is selected --------------------------- */

        /* Case: selects first card in the doctors list, add a doctor -> added */
        selectDoctor(Index.fromOneBased(1));
        assertCommandSuccess(CARL);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate person -> rejected */
        command = DoctorUtil.getAddDoctorCommand(ELLA);
        assertCommandFailure(command, AddDoctorCommand.getMessageDuplicateDoctor());


        /* Case: missing name -> rejected */
        command = AddDoctorCommand.COMMAND_WORD + PHONE_DESC_AMY + EMAIL_DESC_AMY + SPECIALTY_DESC_AMY + YOE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDoctorCommand.getCommandUsage()));

        /* Case: missing phone -> rejected */
        command = AddDoctorCommand.COMMAND_WORD + NAME_DESC_AMY + EMAIL_DESC_AMY + SPECIALTY_DESC_AMY + YOE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDoctorCommand.getCommandUsage()));

        /* Case: missing email -> rejected */
        command = AddDoctorCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + SPECIALTY_DESC_AMY + YOE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDoctorCommand.getCommandUsage()));

        /* Case: missing specialty -> rejected */
        command = AddDoctorCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + YOE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDoctorCommand.getCommandUsage()));

        /* Case: missing years of experience -> rejected */
        command = AddDoctorCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + SPECIALTY_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDoctorCommand.getCommandUsage()));

        /* Case: invalid keyword -> rejected */
        command = "add--dOC " + DoctorUtil.getDoctorDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

    }

    private void assertCommandSuccess(Doctor doctor) {
        assertCommandSuccess(DoctorUtil.getAddDoctorCommand(doctor), doctor);
    }

    private void assertCommandSuccess(String command, Doctor toAdd) {
        Model expectedModel = getModel();
        expectedModel.addDoctor(toAdd);
        String expectedResultMessage = String.format(AddDoctorCommand.getMessageSuccess(), toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
