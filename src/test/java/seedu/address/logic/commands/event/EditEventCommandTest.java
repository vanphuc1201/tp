package seedu.address.logic.commands.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.event.DescriptionTest.VALID_DESCRIPTION;
import static seedu.address.model.event.DescriptionTest.VALID_DESCRIPTION_2;
import static seedu.address.model.event.EventTest.VALID_EVENT;
import static seedu.address.model.event.EventTest.VALID_EVENT_2;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.testutil.GroupBuilder;

public class EditEventCommandTest {
    private static final Index indexZero = Index.fromZeroBased(0);
    private static final Index indexOne = Index.fromZeroBased(1);

    private final Group groupWithOneEvent = new GroupBuilder().withEvents(VALID_EVENT).build();
    private final Model modelStub = new ModelStubWithGroupList().withGroups(groupWithOneEvent);

    // TODO: test with: no group, multiple groups, empty events, multiple events.

    @Test
    public void constructor_nullParams_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new EditEventCommand(null, null, null));
        assertThrows(NullPointerException.class, () ->
                new EditEventCommand(null, indexZero, VALID_DESCRIPTION));
        assertThrows(NullPointerException.class, () ->
                new EditEventCommand(indexZero, null, VALID_DESCRIPTION));
        assertThrows(NullPointerException.class, () ->
                new EditEventCommand(indexZero, indexZero, null));
    }

    @Test
    public void execute_validIndex_eventEditedSuccessfully() throws Exception {
        EditEventCommand command = new EditEventCommand(indexZero, indexZero, VALID_DESCRIPTION_2);

        CommandResult result = command.execute(modelStub);

        // Check success message contains event and group info
        String expectedMessage = String.format(EditEventCommand.MESSAGE_SUCCESS,
                VALID_EVENT, groupWithOneEvent.getName());
        assertEquals(expectedMessage, result.getFeedbackToUser());

        // Check the event was actually removed from the group in the model stub
        assertTrue(groupWithOneEvent.getEvents().contains(VALID_EVENT_2));
    }

    @Test
    public void execute_invalidGroupIndex_throwsCommandException() {
        // Only one group exists, at index 0
        EditEventCommand command = new EditEventCommand(indexOne, indexZero, VALID_DESCRIPTION);
        assertThrows(CommandException.class,
                Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX, () -> command.execute(modelStub));
    }

    @Test
    public void execute_invalidEventIndex_throwsCommandException() {
        // only one event exists, at index 0
        EditEventCommand command = new EditEventCommand(indexZero, indexOne, VALID_DESCRIPTION);

        assertThrows(CommandException.class,
                EditEventCommand.MESSAGE_EVENT_NOT_FOUND, () -> command.execute(modelStub));
    }

    @Test
    public void equals() {
        EditEventCommand deleteCommand = new EditEventCommand(indexZero, indexZero, VALID_DESCRIPTION);

        // same object -> returns true
        assertEquals(deleteCommand, deleteCommand);

        // same values -> returns true
        EditEventCommand deleteCommandCopy = new EditEventCommand(indexZero, indexZero, VALID_DESCRIPTION);
        assertEquals(deleteCommand, deleteCommandCopy);

        // different types -> returns false
        assertFalse(deleteCommand.equals(5.0f));

        // null -> returns false
        assertFalse(deleteCommand.equals(null));

        // different group index -> returns false
        assertNotEquals(deleteCommand, new EditEventCommand(indexOne, indexZero, VALID_DESCRIPTION));

        // different event index -> returns false
        assertNotEquals(deleteCommand, new EditEventCommand(indexZero, indexOne, VALID_DESCRIPTION));

        // different description -> returns false
        assertNotEquals(deleteCommand, new EditEventCommand(indexZero, indexZero, VALID_DESCRIPTION_2));
    }

    @Test
    public void toStringTest() {
        EditEventCommand command = new EditEventCommand(indexZero, indexOne, VALID_DESCRIPTION);
        String expected = EditEventCommand.class.getCanonicalName() + "{groupIndex=" + indexZero
                + ", eventIndex=" + indexOne + ", description=" + VALID_DESCRIPTION + "}";
        assertEquals(expected, command.toString());
    }
}
