package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_INDEX;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.group.Group;

/**
 * Edits an Event in the specified Group
 */
public class EditEventCommand extends Command {

    public static final String COMMAND_WORD = "edit-event";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits an event from the group identified "
            + "by the index number used in the displayed groups list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_EVENT_INDEX + "EVENT_INDEX (must be a positive integer) "
            + PREFIX_DESCRIPTION + "DESCRIPTION";

    public static final String MESSAGE_SUCCESS = "Edited Event: '%1$s' in Group: '%2$s'";
    public static final String MESSAGE_EVENT_NOT_FOUND = "There is no event at that index in the group";

    private final Index groupIndex;
    private final Index eventIndex;
    private final Description description;

    /**
     * Creates a Command to edit the specified {@code Event}
     */
    public EditEventCommand(Index groupIndex, Index eventIndex, Description description) {
        requireAllNonNull(groupIndex, eventIndex, description);
        this.groupIndex = groupIndex;
        this.eventIndex = eventIndex;
        this.description = description;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Group> lastShownList = model.getFilteredGroupList();

        if (groupIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
        }

        Group group = lastShownList.get(groupIndex.getZeroBased());

        if (eventIndex.getZeroBased() >= group.getEvents().size()) {
            throw new CommandException(MESSAGE_EVENT_NOT_FOUND);
        }

        Event target = group.getEvents().get(eventIndex.getZeroBased());
        Event edited = new Event(description);
        group.setEvent(target, edited);
        return new CommandResult(String.format(MESSAGE_SUCCESS, target.toString(), Messages.format(group)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditEventCommand otherEditEventCommand)) {
            return false;
        }

        return groupIndex.equals(otherEditEventCommand.groupIndex)
                && eventIndex.equals(otherEditEventCommand.eventIndex)
                && description.equals(otherEditEventCommand.description);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("groupIndex", groupIndex)
                .add("eventIndex", eventIndex)
                .add("description", description)
                .toString();
    }
}
