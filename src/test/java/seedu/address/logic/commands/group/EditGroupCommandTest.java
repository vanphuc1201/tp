package seedu.address.logic.commands.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CS2101_CA1;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_NAME_CS2101_CA1;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showGroupAtIndex;
import static seedu.address.testutil.TypicalGroups.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_GROUP;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_GROUP;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.group.EditGroupCommand.EditGroupDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.Group;
import seedu.address.testutil.EditGroupDescriptorBuilder;
import seedu.address.testutil.GroupBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditGroupCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Group editedGroup = new GroupBuilder().build();
        EditGroupDescriptor descriptor = new EditGroupDescriptorBuilder(editedGroup).build();
        EditGroupCommand editGroupCommand = new EditGroupCommand(INDEX_SECOND_GROUP, descriptor);

        //setting up expected model and message
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setGroup(model.getFilteredGroupList().get(1), editedGroup);

        String expectedMessage = String.format(EditGroupCommand.MESSAGE_EDIT_GROUP_SUCCESS,
                Messages.format(editedGroup));

        assertCommandSuccess(editGroupCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditGroupCommand editCommand = new EditGroupCommand(INDEX_FIRST_GROUP, new EditGroupDescriptor());
        Group editedGroup = model.getFilteredGroupList().get(INDEX_FIRST_GROUP.getZeroBased());

        //setting up expected model and message
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        String expectedMessage = String.format(EditGroupCommand.MESSAGE_EDIT_GROUP_SUCCESS,
                Messages.format(editedGroup));

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showGroupAtIndex(model, INDEX_FIRST_GROUP);

        Group groupInFilteredList = model.getFilteredGroupList().get(INDEX_FIRST_GROUP.getZeroBased());
        Group editedGroup = new GroupBuilder(groupInFilteredList).withName(VALID_GROUP_NAME_CS2101_CA1).build();

        EditGroupDescriptor editGroupDescriptor = new EditGroupDescriptorBuilder()
                .withName(VALID_GROUP_NAME_CS2101_CA1).build();
        EditGroupCommand editCommand = new EditGroupCommand(INDEX_FIRST_GROUP, editGroupDescriptor);

        //setting up expected model and message
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setGroup(model.getFilteredGroupList().get(0), editedGroup);

        String expectedMessage = String.format(EditGroupCommand.MESSAGE_EDIT_GROUP_SUCCESS,
                Messages.format(editedGroup));

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateGroupUnfilteredList_failure() {
        Group firstGroup = model.getFilteredGroupList().get(INDEX_FIRST_GROUP.getZeroBased());
        EditGroupDescriptor descriptor = new EditGroupDescriptorBuilder(firstGroup).build();
        EditGroupCommand editCommand = new EditGroupCommand(INDEX_SECOND_GROUP, descriptor);

        assertCommandFailure(editCommand, model, EditGroupCommand.MESSAGE_DUPLICATE_GROUP);
    }

    @Test
    public void execute_duplicateGroupFilteredList_failure() {
        showGroupAtIndex(model, INDEX_FIRST_GROUP);

        // edit group in filtered list into a duplicate in address book
        Group groupInList = model.getAddressBook().getGroupList().get(INDEX_SECOND_GROUP.getZeroBased());
        EditGroupCommand editCommand = new EditGroupCommand(INDEX_FIRST_GROUP,
                new EditGroupDescriptorBuilder(groupInList).build());

        assertCommandFailure(editCommand, model, EditGroupCommand.MESSAGE_DUPLICATE_GROUP);
    }

    @Test
    public void execute_invalidGroupIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredGroupList().size() + 1);
        EditGroupDescriptor descriptor = new EditGroupDescriptorBuilder().withName(VALID_GROUP_NAME_CS2101_CA1).build();
        EditGroupCommand editCommand = new EditGroupCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidGroupIndexFilteredList_failure() {
        showGroupAtIndex(model, INDEX_FIRST_GROUP);
        Index outOfBoundIndex = INDEX_SECOND_GROUP;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getGroupList().size());

        EditGroupDescriptor descriptor = new EditGroupDescriptorBuilder().withName(VALID_GROUP_NAME_CS2101_CA1).build();
        EditGroupCommand editCommand = new EditGroupCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditGroupCommand standardCommand = new EditGroupCommand(INDEX_FIRST_GROUP, DESC_CS2103T);

        // same values -> returns true
        EditGroupDescriptor copyDescriptor = new EditGroupDescriptor(DESC_CS2103T);
        EditGroupCommand commandWithSameValues = new EditGroupCommand(INDEX_FIRST_GROUP, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditGroupCommand(INDEX_SECOND_GROUP, DESC_CS2103T)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditGroupCommand(INDEX_FIRST_GROUP, DESC_CS2101_CA1)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditGroupDescriptor editGroupDescriptor = new EditGroupDescriptor();
        EditGroupCommand editGroupCommand = new EditGroupCommand(index, editGroupDescriptor);
        String expected = EditGroupCommand.class.getCanonicalName() + "{index=" + index + ", editGroupDescriptor="
                + editGroupDescriptor + "}";
        assertEquals(expected, editGroupCommand.toString());
    }

}
