package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showGroupAtIndex;
import static seedu.address.testutil.TypicalGroups.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_GROUP;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_GROUP;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.groups.DeleteGroupCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.Group;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteGroupCommand}.
 */
public class DeleteGroupCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Group groupToDelete = model.getFilteredGroupList().get(INDEX_FIRST_GROUP.getZeroBased());
        DeleteGroupCommand deleteGroupCommand = new DeleteGroupCommand(INDEX_FIRST_GROUP);

        String expectedMessage = String.format(DeleteGroupCommand.MESSAGE_DELETE_GROUP_SUCCESS,
                Messages.format(groupToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteGroup(groupToDelete);

        assertCommandSuccess(deleteGroupCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredGroupList().size() + 1);
        DeleteGroupCommand deleteGroupCommand = new DeleteGroupCommand(outOfBoundIndex);

        assertCommandFailure(deleteGroupCommand, model, Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showGroupAtIndex(model, INDEX_FIRST_GROUP);

        Group groupToDelete = model.getFilteredGroupList().get(INDEX_FIRST_GROUP.getZeroBased());
        DeleteGroupCommand deleteGroupCommand = new DeleteGroupCommand(INDEX_FIRST_GROUP);

        String expectedMessage = String.format(DeleteGroupCommand.MESSAGE_DELETE_GROUP_SUCCESS,
                Messages.format(groupToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteGroup(groupToDelete);
        showNoGroup(expectedModel);

        assertCommandSuccess(deleteGroupCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showGroupAtIndex(model, INDEX_FIRST_GROUP);

        Index outOfBoundIndex = INDEX_SECOND_GROUP;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getGroupList().size());

        DeleteGroupCommand deleteCommand = new DeleteGroupCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteGroupCommand deleteGroupFirstCommand = new DeleteGroupCommand(INDEX_FIRST_GROUP);
        DeleteGroupCommand deleteGroupSecondCommand = new DeleteGroupCommand(INDEX_SECOND_GROUP);

        // same object -> returns true
        assertTrue(deleteGroupFirstCommand.equals(deleteGroupFirstCommand));

        // same values -> returns true
        DeleteGroupCommand deleteGroupFirstCommandCopy = new DeleteGroupCommand(INDEX_FIRST_GROUP);
        assertTrue(deleteGroupFirstCommand.equals(deleteGroupFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteGroupFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteGroupFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteGroupFirstCommand.equals(deleteGroupSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteGroupCommand deleteGroupCommand = new DeleteGroupCommand(targetIndex);
        String expected = DeleteGroupCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteGroupCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no groups.
     */
    private void showNoGroup(Model model) {
        model.updateFilteredGroupList(group -> false);

        assertTrue(model.getFilteredGroupList().isEmpty());
    }

}
