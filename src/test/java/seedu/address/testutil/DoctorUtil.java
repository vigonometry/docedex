package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIALTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YOE;

import java.util.Set;

import seedu.address.logic.commands.AddDoctorCommand;
import seedu.address.logic.commands.EditDoctorCommand.EditDoctorDescriptor;
import seedu.address.model.person.doctor.Doctor;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Person.
 */
public class DoctorUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddDoctorCommand(Doctor doctor) {
        return AddDoctorCommand.COMMAND_WORD + " " + getDoctorDetails(doctor);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getDoctorDetails(Doctor doctor) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + doctor.getName().getValue() + " ");
        sb.append(PREFIX_PHONE + doctor.getPhone().getValue() + " ");
        sb.append(PREFIX_EMAIL + doctor.getEmail().getValue() + " ");
        sb.append(PREFIX_SPECIALTY + doctor.getSpecialty().getValue() + " ");
        sb.append(PREFIX_YOE + doctor.getYoe().getValue() + " ");
        doctor.getTags().forEach(
                s -> sb.append(PREFIX_TAG + s.getTagName() + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditDoctorDescriptorDetails(EditDoctorDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.getValue()).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.getValue()).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.getValue()).append(" "));
        descriptor.getSpecialty().ifPresent(specialty -> sb.append(PREFIX_SPECIALTY).append(specialty.getValue()).append(" "));
        descriptor.getYoe().ifPresent(yoe -> sb.append(PREFIX_YOE).append(yoe.getValue()).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.getTagName()).append(" "));
            }
        }
        return sb.toString();
    }
}
