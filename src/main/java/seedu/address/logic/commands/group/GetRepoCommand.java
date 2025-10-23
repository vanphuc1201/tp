package seedu.address.logic.commands.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ClipboardUtil;
import seedu.address.commons.util.ToStringBuilder;
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
            + "Parameters: GROUP_INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Group %1$s repository link: %2$s\n"
            + "This link is copied to your clipboard, you can paste it now";

    public static final String MESSAGE_GET_REPO_NOT_SET = "Group %1$s repository link is not setup yet \n"
            + "Please use 'set-repo' command to setup first before retrieving it";

    private final ClipboardUtil clipboardUtil;

    private final Index targetIndex;

    /**
     * Creates a GetRepoCommand to retrieve the specified {@code Group} repository link
     * and copied to user clipboard
     */
    public GetRepoCommand(Index targetIndex) {
        requireNonNull(targetIndex);

        this.targetIndex = targetIndex;
        clipboardUtil = new ClipboardUtil();
    }

    /**
     * Creates a {@code GetRepoCommand} with the specified group index and a custom {@link ClipboardUtil}.
     * <p>
     * This constructor is mainly used for testing to inject a mock or fake clipboard utility.
     *
     * @param targetIndex   Index of the group whose repository link is to be retrieved.
     * @param clipboardUtil Clipboard utility used to copy the repository link to the system clipboard.
     */
    public GetRepoCommand(Index targetIndex, ClipboardUtil clipboardUtil) {
        requireAllNonNull(targetIndex, clipboardUtil);
        this.targetIndex = targetIndex;
        this.clipboardUtil = clipboardUtil;
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

        if (!repoLink.isRepoSet()) {
            throw new CommandException(String.format(MESSAGE_GET_REPO_NOT_SET, targetGroup.getName()));
        }

        clipboardUtil.copyToClipboard(repoLink.toString());
        return new CommandResult(String.format(MESSAGE_SUCCESS, targetGroup.getName(), repoLink));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GetRepoCommand)) {
            return false;
        }

        GetRepoCommand otherGetRepoCommand = (GetRepoCommand) other;
        return targetIndex.equals(otherGetRepoCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
