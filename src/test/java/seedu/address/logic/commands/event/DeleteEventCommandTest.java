package seedu.address.logic.commands.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.model.event.EventTest.VALID_EVENT;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.testutil.GroupBuilder;

public class DeleteEventCommandTest {
    private static final Index indexZero = Index.fromZeroBased(0);
    private static final Index indexOne = Index.fromZeroBased(1);

    private final Group groupWithOneEvent = new GroupBuilder().withEvents(VALID_EVENT).build();
    private final Model modelStub = new ModelStubWithGroupList().withGroups(groupWithOneEvent);

    // TODO: test with: no group, multiple groups, empty events, multiple events.

    @Test
    public void constructor_nullParams_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteEventCommand(null, null));
        assertThrows(NullPointerException.class, () -> new DeleteEventCommand(null, indexZero));
        assertThrows(NullPointerException.class, () -> new DeleteEventCommand(indexZero, null));
    }

    @Test
    public void execute_validIndex_eventDeletedSuccessfully() throws Exception {
        DeleteEventCommand command = new DeleteEventCommand(indexZero, indexZero);

        CommandResult result = command.execute(modelStub);

        // Check success message contains event and group info
        String expectedMessage = String.format(DeleteEventCommand.MESSAGE_SUCCESS,
                VALID_EVENT, groupWithOneEvent.getName());
        assertEquals(expectedMessage, result.getFeedbackToUser());

        // Check the event was actually removed from the group in the model stub
        assertFalse(groupWithOneEvent.getEvents().contains(VALID_EVENT));
    }

    @Test
    public void execute_invalidGroupIndex_throwsCommandException() {
        DeleteEventCommand command = new DeleteEventCommand(indexOne, indexZero); // only one group at index 0
        assertThrows(CommandException.class,
                Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX, () -> command.execute(modelStub));
    }

    @Test
    public void execute_invalidEventIndex_throwsCommandException() {
        DeleteEventCommand command = new DeleteEventCommand(indexZero, indexOne); // only one event at index 0

        assertThrows(CommandException.class,
                DeleteEventCommand.MESSAGE_EVENT_NOT_FOUND, () -> command.execute(modelStub));
    }

    @Test
    public void equals() {
        DeleteEventCommand deleteCommand = new DeleteEventCommand(indexZero, indexZero);

        // same object -> returns true
        assertEquals(deleteCommand, deleteCommand);

        // same values -> returns true
        DeleteEventCommand deleteCommandCopy = new DeleteEventCommand(indexZero, indexZero);
        assertEquals(deleteCommand, deleteCommandCopy);

        // different types -> returns false
        assertFalse(deleteCommand.equals(5.0f));

        // null -> returns false
        assertFalse(deleteCommand.equals(null));

        // different group index -> returns false
        assertNotEquals(deleteCommand, new DeleteEventCommand(indexOne, indexZero));

        // different event -> returns false
        assertNotEquals(deleteCommand, new DeleteEventCommand(indexZero, indexOne));
    }

    @Test
    public void toStringTest() {
        DeleteEventCommand command = new DeleteEventCommand(indexZero, indexOne);
        String expected = DeleteEventCommand.class.getCanonicalName() + "{groupIndex=" + indexZero
                + ", eventIndex=" + indexOne + "}";
        assertEquals(expected, command.toString());
    }
}
