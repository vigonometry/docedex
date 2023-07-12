package systemtests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.person.doctor.Doctor;
import seedu.address.model.person.patient.Patient;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {
    private static final Predicate<Doctor> PREDICATE_MATCHING_NO_DOCTORS = unused -> false;
    private static final Predicate<Patient> PREDICATE_MATCHING_NO_PATIENTS = unused -> false;

    /**
     * Updates {@code model}'s filtered Doctor list to display only {@code toDisplay}.
     */
    public static void setFilteredDoctorList(Model model, List<Doctor> toDisplay) {
        Optional<Predicate<Doctor>> predicate =
                toDisplay.stream().map(ModelHelper::getDoctorPredicateMatching).reduce(Predicate::or);
        model.updateFilteredDoctorList(predicate.orElse(PREDICATE_MATCHING_NO_DOCTORS));
    }

    /**
     * Updates {@code model}'s filtered Patient list to display only {@code toDisplay}.
     */
    public static void setFilteredPatientList(Model model, List<Patient> toDisplay) {
        Optional<Predicate<Patient>> predicate =
                toDisplay.stream().map(ModelHelper::getPatientPredicateMatching).reduce(Predicate::or);
        model.updateFilteredPatientList(predicate.orElse(PREDICATE_MATCHING_NO_PATIENTS));
    }

    /**
     * @see ModelHelper#setFilteredDoctorList(Model, List)
     */
    public static void setFilteredList(Model model, Doctor... toDisplay) {
        setFilteredDoctorList(model, Arrays.asList(toDisplay));
    }

    /**
     * @see ModelHelper#setFilteredPatientList(Model, List)
     */
    public static void setFilteredList(Model model, Patient... toDisplay) {
        setFilteredPatientList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Doctor} equals to {@code other}.
     */
    private static Predicate<Doctor> getDoctorPredicateMatching(Doctor other) {
        return doctor -> doctor.equals(other);
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Patient} equals to {@code other}.
     */
    private static Predicate<Patient> getPatientPredicateMatching(Patient other) {
        return patient -> patient.equals(other);
    }
}
