package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.TypicalDoctors.getTypicalDoctors;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysDoctor;
import static seedu.address.ui.testutil.GuiTestAssert.assertDoctorCardEquals;

import java.io.File;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import guitests.guihandles.DoctorCardHandle;
import guitests.guihandles.DoctorListPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.LogicManager;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.doctor.Doctor;
import seedu.address.model.person.doctor.Specialty;
import seedu.address.model.person.doctor.Yoe;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;

public class DoctorListPanelTest extends GuiUnitTest {
    private static final ObservableList<Doctor> TYPICAL_DOCTORS =
            FXCollections.observableList(getTypicalDoctors());

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private final SimpleObjectProperty<Doctor> selectedDoctor = new SimpleObjectProperty<>();
    private DoctorListPanelHandle doctorListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_DOCTORS);

        for (int i = 0; i < TYPICAL_DOCTORS.size(); i++) {
            doctorListPanelHandle.navigateToCard(TYPICAL_DOCTORS.get(i));
            Doctor expectedDoctor = TYPICAL_DOCTORS.get(i);
            DoctorCardHandle actualCard = doctorListPanelHandle.getDoctorCardHandle(i);

            assertCardDisplaysDoctor(expectedDoctor, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    /**
     * Verifies that creating and deleting large number of persons in {@code DoctorListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<Doctor> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of person cards exceeded time limit");
    }

    /**
     * Returns a list of doctors containing {@code doctorCount} persons that is used to populate the
     * {@code DoctorListPanel}.
     */
    private ObservableList<Doctor> createBackingList(int doctorCount) {
        ObservableList<Doctor> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < doctorCount; i++) {
            Name name = new Name(String.format("%da", i));
            Phone phone = new Phone("000");
            Email email = new Email("a@aa");
            Specialty specialty = new Specialty("a");
            Yoe yoe = new Yoe("1");
            Doctor doctor = new Doctor(name, phone, email, specialty, yoe, Collections.emptySet());
            backingList.add(doctor);
        }
        return backingList;
    }

    /**
     * Initializes {@code doctorListPanelHandle} with a {@code DoctorListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code DoctorListPanel}.
     */
    private void initUi(ObservableList<Doctor> backingList) {
        DoctorListPanel doctorListPanel =
                new DoctorListPanel(backingList, new ContactDisplay(new LogicManager(
                        new ModelManager(), new StorageManager(new JsonAddressBookStorage(
                                new File("data/docedex.json").toPath()), new JsonUserPrefsStorage(
                                        new File("userPrefs.json").toPath())))));
        uiPartExtension.setUiPart(doctorListPanel);

        doctorListPanelHandle = new DoctorListPanelHandle(getChildNode(doctorListPanel.getRoot(),
                DoctorListPanelHandle.DOCTOR_LIST_VIEW_ID));
    }
}
