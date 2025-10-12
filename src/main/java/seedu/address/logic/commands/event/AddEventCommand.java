package seedu.address.logic.commands.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Adds an Event to the specified Group
 */
public class AddEventCommand extends Command {

    public static final String COMMAND_WORD = "add-event";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to the group identified "
            + "by the index number used in the displayed groups list. "
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DESCRIPTION + "DESCRIPTION";

    public static final String MESSAGE_SUCCESS = "New event: '%1$s' added to group: %2$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the group";

    private final Index groupIndex;
    private final Event toAdd;

    /**
     * Creates an AddEvent to add the specified {@code Event}
     */
    public AddEventCommand(Index index, Event event) {
        requireAllNonNull(index, event);
        groupIndex = index;
        toAdd = event;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult("Hello from AddEventCommand");
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddEventCommand otherAddEventCommand)) {
            return false;
        }

        return toAdd.equals(otherAddEventCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("groupIndex", groupIndex)
                .add("toAdd", toAdd)
                .toString();
    }
}
