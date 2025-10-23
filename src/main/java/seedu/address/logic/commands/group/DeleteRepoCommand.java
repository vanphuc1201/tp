package seedu.address.logic.commands.group;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.group.RepoLink;

/**
 * Deletes repository link for the group identified by index number used in the displayed groups list.
 */
public class DeleteRepoCommand extends Command {

    public static final String COMMAND_WORD = "delete-repo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Delete repository link for the group identified "
            + "by index number used in the displayed groups \n"
            + "Parameters: GROUP_INDEX (must be a positive integer) \n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Group %1$s repository link is deleted";

    private final Index groupIndex;

    /**
     * Creates a DeleteRepoCommand to delete the repository link of specified {@code Group} by groupIndex.
     */
    public DeleteRepoCommand(Index groupIndex) {
        requireNonNull(groupIndex);

        this.groupIndex = groupIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Group> lastShownGroupList = model.getFilteredGroupList();

        if (groupIndex.getZeroBased() >= lastShownGroupList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
        }

        Group groupRepoToDelete = lastShownGroupList.get(groupIndex.getZeroBased());
        model.setGroupRepo(groupRepoToDelete, new RepoLink()); // rest repolink to default value none

        return new CommandResult(String.format(MESSAGE_SUCCESS, groupRepoToDelete.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteRepoCommand)) {
            return false;
        }

        DeleteRepoCommand otherDeleteRepoCommand = (DeleteRepoCommand) other;
        return groupIndex.equals(otherDeleteRepoCommand.groupIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("groupIndex", groupIndex)
                .toString();
    }
}
