package seedu.address.ui.testutil;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.DoctorCardHandle;
import guitests.guihandles.DoctorListPanelHandle;
import guitests.guihandles.PatientCardHandle;
import guitests.guihandles.PatientListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.person.Person;
import seedu.address.model.person.doctor.Doctor;
import seedu.address.model.person.patient.Patient;
import seedu.address.model.tag.Tag;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualDoctorCard} displays the same values as {@code expectedDoctorCard}.
     */
    public static void assertDoctorCardEquals(DoctorCardHandle expectedDoctorCard, DoctorCardHandle actualDoctorCard) {
        assertEquals(expectedDoctorCard.getId(), actualDoctorCard.getId());
        assertEquals(expectedDoctorCard.getEmail(), actualDoctorCard.getEmail());
        assertEquals(expectedDoctorCard.getName(), actualDoctorCard.getName());
        assertEquals(expectedDoctorCard.getPhone(), actualDoctorCard.getPhone());
        assertEquals(expectedDoctorCard.getTags(), actualDoctorCard.getTags());
    }

    /**
     * Asserts that {@code actualDoctorCard} displays the details of {@code expectedDoctor}.
     */
    public static void assertCardDisplaysDoctor(Doctor expectedDoctor, DoctorCardHandle actualDoctorCard) {
        assertEquals(expectedDoctor.getName().getValue(), actualDoctorCard.getName());
        assertEquals(expectedDoctor.getPhone().getValue(), actualDoctorCard.getPhone());
        assertEquals(expectedDoctor.getEmail().getValue(), actualDoctorCard.getEmail());
        assertEquals(expectedDoctor.getTags().stream().map(Tag::getTagName).sorted().collect(Collectors.toList()),
                actualDoctorCard.getTags());
    }

    /**
     * Asserts that {@code actualPatientCard} displays the same values as {@code expectedPatientCard}.
     */
    public static void assertPatientCardEquals(PatientCardHandle expectedPatientCard,
                                               PatientCardHandle actualPatientCard) {
        assertEquals(expectedPatientCard.getId(), actualPatientCard.getId());
        assertEquals(expectedPatientCard.getEmail(), actualPatientCard.getEmail());
        assertEquals(expectedPatientCard.getName(), actualPatientCard.getName());
        assertEquals(expectedPatientCard.getPhone(), actualPatientCard.getPhone());
        assertEquals(expectedPatientCard.getTags(), actualPatientCard.getTags());
    }

    /**
     * Asserts that {@code actualPatientCard} displays the details of {@code expectedPatient}.
     */
    public static void assertCardDisplaysPatient(Person expectedPatient, PatientCardHandle actualPatientCard) {
        assertEquals(expectedPatient.getName().getValue(), actualPatientCard.getName());
        assertEquals(expectedPatient.getPhone().getValue(), actualPatientCard.getPhone());
        assertEquals(expectedPatient.getEmail().getValue(), actualPatientCard.getEmail());
        assertEquals(expectedPatient.getTags().stream().map(Tag::getTagName).sorted().collect(Collectors.toList()),
                actualPatientCard.getTags());
    }


    /**
     * Asserts that the list in {@code doctorListPanelHandle} displays the details of {@code doctors} correctly and
     * in the correct order.
     */
    public static void assertDoctorListMatching(DoctorListPanelHandle doctorListPanelHandle, Doctor... doctors) {
        for (int i = 0; i < doctors.length; i++) {
            doctorListPanelHandle.navigateToCard(i);
            assertCardDisplaysDoctor(doctors[i], doctorListPanelHandle.getDoctorCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code doctorListPanelHandle} displays the details of {@code doctors} correctly and
     * in the correct order.
     */
    public static void assertDoctorListMatching(DoctorListPanelHandle doctorListPanelHandle, List<Doctor> doctors) {
        assertDoctorListMatching(doctorListPanelHandle, doctors.toArray(new Doctor[0]));
    }

    /**
     * Asserts that the list in {@code patientListPanelHandle} displays the details of {@code patients} correctly and
     * in the correct order.
     */
    public static void assertPatientListMatching(PatientListPanelHandle patientListPanelHandle, Patient... patients) {
        if (patients.length != patientListPanelHandle.getListSize())
        for (int i = 0; i < patients.length; i++) {
            patientListPanelHandle.navigateToCard(i);
            assertCardDisplaysPatient(patients[i], patientListPanelHandle.getPatientCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code patientListPanelHandle} displays the details of {@code patients} correctly and
     * in the correct order.
     */
    public static void assertPatientListMatching(PatientListPanelHandle patientListPanelHandle, List<Patient> patients) {
        assertPatientListMatching(patientListPanelHandle, patients.toArray(new Patient[0]));
    }


    /**
     * Asserts the size of the list in {@code doctorListPanelHandle} equals to {@code size}.
     */
    public static void assertDoctorListSize(DoctorListPanelHandle doctorListPanelHandle, int size) {
        int numberOfDoctors = doctorListPanelHandle.getListSize();
        assertEquals(size, numberOfDoctors);
    }

    /**
     * Asserts the size of the list in {@code patientListPanelHandle} equals to {@code size}.
     */
    public static void assertPatientListSize(PatientListPanelHandle patientListPanelHandle, int size) {
        int numberOfPatients = patientListPanelHandle.getListSize();
        assertEquals(size, numberOfPatients);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
