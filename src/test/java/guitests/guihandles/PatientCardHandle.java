package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.person.patient.Patient;
import seedu.address.model.tag.Tag;

/**
 * Provides a handle to a patient card in the patient list panel.
 */
public class PatientCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label phoneLabel;
    private final Label emailLabel;
    private final List<Label> tagLabels;

    /**
     * Constructs a {@code PatientCardHandle} with the given {@code cardNode}.
     * @param cardNode
     */
    public PatientCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        phoneLabel = getChildNode(PHONE_FIELD_ID);
        emailLabel = getChildNode(EMAIL_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }



    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code patient}.
     */
    public boolean equals(Patient patient) {
        return getName().equals(patient.getName().getValue())
                && getPhone().equals(patient.getPhone().getValue())
                && getEmail().equals(patient.getEmail().getValue())
                && getTags().equals(patient.getTags().stream()
                .map(Tag::getTagName)
                .sorted()
                .collect(Collectors.toList()));
    }
}

