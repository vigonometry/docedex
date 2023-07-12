package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.person.doctor.Doctor;

/**
 * Provides a handle for {@code DoctorListPanel} containing the list of {@code DoctorCard}.
 */
public class DoctorListPanelHandle extends NodeHandle<ListView<Doctor>> {
    public static final String DOCTOR_LIST_VIEW_ID = "#doctorListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Doctor> lastRememberedSelectedDoctorCard;

    public DoctorListPanelHandle(ListView<Doctor> doctorListPanelNode) {
        super(doctorListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code DoctorCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public DoctorCardHandle getHandleToSelectedCard() {
        List<Doctor> selectedDoctorList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedDoctorList.size() != 1) {
            throw new AssertionError("Doctor list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(DoctorCardHandle::new)
                .filter(handle -> handle.equals(selectedDoctorList.get(0)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<Doctor> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code Doctor}.
     */
    public void navigateToCard(Doctor doctor) {
        if (!getRootNode().getItems().contains(doctor)) {
            throw new IllegalArgumentException("Doctor does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(doctor);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Navigates the listview to {@code index}.
     */
    public void navigateToCard(int index) {
        if (index < 0 || index >= getRootNode().getItems().size()) {
            throw new IllegalArgumentException("Index is out of bounds.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(index);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Selects the {@code DoctorCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the Doctor card handle of a Doctor associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public DoctorCardHandle getDoctorCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(DoctorCardHandle::new)
                .filter(handle -> handle.equals(getDoctor(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Doctor getDoctor(int index) {
        return getRootNode().getItems().get(index);
    }

    /**
     * Returns all card nodes in the scene graph.
     * Card nodes that are visible in the listview are definitely in the scene graph, while some nodes that are not
     * visible in the listview may also be in the scene graph.
     */
    private Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    /**
     * Remembers the selected {@code DoctorCard} in the list.
     */
    public void rememberSelectedDoctorCard() {
        List<Doctor> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedDoctorCard = Optional.empty();
        } else {
            lastRememberedSelectedDoctorCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code DoctorCard} is different from the value remembered by the most recent
     * {@code rememberSelectedDoctorCard()} call.
     */
    public boolean isSelectedDoctorCardChanged() {
        List<Doctor> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedDoctorCard.isPresent();
        } else {
            return !lastRememberedSelectedDoctorCard.isPresent()
                    || !lastRememberedSelectedDoctorCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
