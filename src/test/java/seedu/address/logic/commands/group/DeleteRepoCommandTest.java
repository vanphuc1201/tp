package seedu.address.logic.commands.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalGroups.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_GROUP;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_GROUP;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.Group;

public class DeleteRepoCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndex_success() throws CommandException {
        Group groupToRetrieve = model.getFilteredGroupList().get(INDEX_SECOND_GROUP.getZeroBased());
        DeleteRepoCommand deleteRepoCommand = new DeleteRepoCommand(INDEX_SECOND_GROUP);
        CommandResult result = deleteRepoCommand.execute(model);

        String expectedMessage = String.format(DeleteRepoCommand.MESSAGE_SUCCESS, groupToRetrieve.getName());

        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_inValidIndex_success() throws CommandException {
        DeleteRepoCommand deleteRepoCommand =
                new DeleteRepoCommand(Index.fromZeroBased(model.getFilteredGroupList().size() + 1));

        assertCommandFailure(deleteRepoCommand, model, Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteRepoCommand firstDeleteCommand = new DeleteRepoCommand(INDEX_FIRST_GROUP);
        DeleteRepoCommand secondDeleteCommand = new DeleteRepoCommand(INDEX_SECOND_GROUP);

        // same object -> returns true
        assertEquals(firstDeleteCommand, firstDeleteCommand);

        // same values -> returns true
        DeleteRepoCommand firstDeleteCommandCopy = new DeleteRepoCommand(INDEX_FIRST_GROUP);
        assertEquals(firstDeleteCommand, firstDeleteCommandCopy);

        // different types -> returns false
        assertFalse(firstDeleteCommand.equals(5.0f));

        // null -> returns false
        assertFalse(firstDeleteCommand.equals(null));

        // different group index -> returns false
        assertNotEquals(firstDeleteCommand, secondDeleteCommand);

        // different index -> returns false
        assertNotEquals(firstDeleteCommand, new DeleteRepoCommand(INDEX_SECOND_GROUP));
    }

    @Test
    public void toStringTest() {
        DeleteRepoCommand command = new DeleteRepoCommand(INDEX_FIRST_GROUP);
        String expected = DeleteRepoCommand.class.getCanonicalName() + "{groupIndex=" + INDEX_FIRST_GROUP + "}";
        assertEquals(expected, command.toString());
    }
}
