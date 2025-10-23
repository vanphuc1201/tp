package seedu.address.logic.commands.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REPO;

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
 * Set repository link for the group identified by index number used in the displayed groups list.
 */
public class SetRepoCommand extends Command {

    public static final String COMMAND_WORD = "set-repo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Set repository link for the group identified "
            + "by index number used in the displayed groups list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_REPO + "REPOSITORY_LINK\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_REPO + "https://github.com/AY2526S1-CS2103T-F12-1/tp";

    public static final String MESSAGE_SUCCESS = "Group '%1$s' has set the repository link to '%2$s'";

    private final Index groupIndex;
    private final RepoLink repoLink;

    /**
     * Creates a SetRepoCommand to set the repository link of specified {@code Group}
     */
    public SetRepoCommand(Index groupIndex, RepoLink repoLink) {
        requireAllNonNull(groupIndex, repoLink);

        this.groupIndex = groupIndex;
        this.repoLink = repoLink;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Group> lastShownGroupList = model.getFilteredGroupList();

        if (groupIndex.getZeroBased() >= lastShownGroupList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
        }

        Group groupToSet = lastShownGroupList.get(groupIndex.getZeroBased());
        model.setGroupRepo(groupToSet, repoLink);

        return new CommandResult(String.format(MESSAGE_SUCCESS, groupToSet.getName(), repoLink));
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SetRepoCommand)) {
            return false;
        }

        SetRepoCommand otherSetRepoCommand = (SetRepoCommand) other;
        return groupIndex.equals(otherSetRepoCommand.groupIndex) && repoLink.equals(otherSetRepoCommand.repoLink);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("groupIndex", groupIndex)
                .add("repoLink", repoLink)
                .toString();
    }
}
