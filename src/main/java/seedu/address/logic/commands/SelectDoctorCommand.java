package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DOCTORS;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.doctor.Doctor;

import java.util.List;

/**
 * Lists a specific doctor in Docedex to the user.
 */
public class SelectDoctorCommand extends Command {

    public static final String COMMAND_WORD = "sd";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the doctor identified by the index number used in the displayed doctor list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_DOCTOR_SUCCESS = "Selected doctor %1$s";

    private Index targetIndex;

    public SelectDoctorCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Doctor> lastShownList = model.getFilteredDoctorList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DOCTOR_DISPLAYED_INDEX);
        }
        Doctor doctorToShow = lastShownList.get(targetIndex.getZeroBased());

        return new CommandResult(String.format(MESSAGE_SELECT_DOCTOR_SUCCESS, doctorToShow),
                true, doctorToShow);
    }
}
