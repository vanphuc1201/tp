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


/**
 * Shows the user the dashboard of a specified group
 */
public class ShowDashboardCommand extends Command {
    public static final String COMMAND_WORD = "show-dashboard";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Brings up the dashboard for the group specified by index number\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SHOW_DASHBOARD_SUCCESS = "Showing dashboard for group: %1$s";

    private final Index targetIndex;

    /**
     * Creates a DeleteGroupCommand to delete the specified {@code Group}
     */
    public ShowDashboardCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Group> lastShownGroupList = model.getFilteredGroupList();

        if (targetIndex.getZeroBased() >= lastShownGroupList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
        }

        Group groupToShow = lastShownGroupList.get(targetIndex.getZeroBased());

        return new CommandResult(String.format(MESSAGE_SHOW_DASHBOARD_SUCCESS, groupToShow.getNameAsString()),
                true, groupToShow);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ShowDashboardCommand)) {
            return false;
        }

        ShowDashboardCommand otherShowDashboardCommand = (ShowDashboardCommand) other;
        return targetIndex.equals(otherShowDashboardCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
