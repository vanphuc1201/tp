package seedu.address.logic.commands.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.model.group.RepoLinkTest.VALID_DIFFERENT_REPO;
import static seedu.address.model.group.RepoLinkTest.VALID_REPO;
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

public class GetRepoCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndex_success() throws CommandException {
        Group groupToRetrieve = model.getFilteredGroupList().get(INDEX_SECOND_GROUP.getZeroBased());
        GetRepoCommand getRepoCommand = new GetRepoCommand(INDEX_SECOND_GROUP);
        CommandResult result = getRepoCommand.execute(model);

        String expectedMessage = String.format(GetRepoCommand.MESSAGE_SUCCESS, groupToRetrieve.getName(), VALID_REPO);

        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_inValidIndex_success() throws CommandException {
        GetRepoCommand getRepoCommand =
                new GetRepoCommand(Index.fromZeroBased(model.getFilteredGroupList().size() + 1));

        assertCommandFailure(getRepoCommand, model, Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
    }

    @Test
    public void execute_defaultRepo_success() throws CommandException {
        Group groupToRetrieve = model.getFilteredGroupList().get(INDEX_FIRST_GROUP.getZeroBased());
        GetRepoCommand getRepoCommand = new GetRepoCommand(INDEX_FIRST_GROUP);

        String expectedMessage = String.format(GetRepoCommand.MESSAGE_GET_REPO_NOT_SET, groupToRetrieve.getName());

        assertCommandFailure(getRepoCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        GetRepoCommand firstGetCommand = new GetRepoCommand(INDEX_FIRST_GROUP);
        GetRepoCommand secondGetCommand = new GetRepoCommand(INDEX_SECOND_GROUP);

        // same object -> returns true
        assertEquals(firstGetCommand, firstGetCommand);

        // same values -> returns true
        GetRepoCommand firstGetCommandCopy = new GetRepoCommand(INDEX_FIRST_GROUP);
        assertEquals(firstGetCommand, firstGetCommandCopy);

        // different types -> returns false
        assertFalse(firstGetCommand.equals(5.0f));

        // null -> returns false
        assertFalse(firstGetCommand.equals(null));

        // different group index -> returns false
        assertNotEquals(firstGetCommand, secondGetCommand);

        // different index -> returns false
        assertNotEquals(firstGetCommand, new GetRepoCommand(INDEX_SECOND_GROUP));
    }

    @Test
    public void toStringTest() {
        GetRepoCommand command = new GetRepoCommand(INDEX_FIRST_GROUP);
        String expected = GetRepoCommand.class.getCanonicalName() + "{targetIndex=" + INDEX_FIRST_GROUP + "}";
        assertEquals(expected, command.toString());
    }
}
