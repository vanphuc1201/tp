package seedu.address.logic.commands.group;

import static java.util.Objects.requireNonNull;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.group.RepoLink;

/**
 * Retrieves the repository link of the group identified by the index number in the displayed group list.
 * The retrieved link is copied to user clipboard.
 */
public class GetRepoCommand extends Command {

    public static final String COMMAND_WORD = "get-repo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Retrieves the repository link of the group identified"
            + " by the index number in the displayed group list.\n"
            + "The retrieved link is copied to your clipboard.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_GET_REPO_SUCCESS = "Group %1$s repository link: %2$s\n"
            + "This link is copied to your clipboard, you can paste it now";

    public static final String MESSAGE_GET_REPO_NOT_SET = "Group %1$s repository link is not setup yet \n"
            + "Please use 'set-repo' command to setup first before retrieving it";

    private final Index targetIndex;

    /**
     * Creates a GetRepoCommand to retrieve the specified {@code Group} repository link
     * and copied to user clipboard
     */
    public GetRepoCommand(Index targetIndex) {
        requireNonNull(targetIndex);

        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Group> lastShownGroupList = model.getFilteredGroupList();

        if (targetIndex.getZeroBased() >= lastShownGroupList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
        }

        Group targetGroup = lastShownGroupList.get(targetIndex.getZeroBased());
        RepoLink repoLink = targetGroup.getRepoLink();
        if (!repoLink.isRepoSet()){
            throw new CommandException(String.format(MESSAGE_GET_REPO_NOT_SET, targetGroup.getName()));
        }
        Toolkit.getDefaultToolkit()
                .getSystemClipboard()
                .setContents(new StringSelection(repoLink.toString()), null);
        return new CommandResult(String.format(MESSAGE_GET_REPO_SUCCESS, targetGroup.getName(), repoLink));
    }
}
