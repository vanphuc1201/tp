package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.group.Group;

/**
 * Deletes an Event from the specified Group
 */
public class DeleteEventCommand extends Command {

    public static final String COMMAND_WORD = "delete-event";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes an event from the group identified "
            + "by the index number used in the displayed groups list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DESCRIPTION + "DESCRIPTION";

    public static final String MESSAGE_SUCCESS = "Event: '%1$s' deleted from group: %2$s";
    public static final String MESSAGE_EVENT_NOT_FOUND = "There is no event at that index in the group";

    private final Index groupIndex;
    private final Index eventIndex;

    /**
     * Creates a Command to delete the specified {@code Event}
     */
    public DeleteEventCommand(Index groupIndex, Index eventIndex) {
        requireAllNonNull(groupIndex, eventIndex);
        this.groupIndex = groupIndex;
        this.eventIndex = eventIndex;
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

        Event toRemove = group.getEvents().get(eventIndex.getZeroBased());
        group.removeEvent(toRemove);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toRemove.toString(), Messages.format(group)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteEventCommand otherDeleteEventCommand)) {
            return false;
        }

        return groupIndex.equals(otherDeleteEventCommand.groupIndex)
                && eventIndex.equals(otherDeleteEventCommand.eventIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("groupIndex", groupIndex)
                .add("eventIndex", eventIndex)
                .toString();
    }
}
