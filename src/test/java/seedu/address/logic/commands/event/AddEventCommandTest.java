package seedu.address.logic.commands.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.event.EventTest.VALID_EVENT;
import static seedu.address.model.event.EventTest.VALID_EVENT_2;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.testutil.GroupBuilder;

public class AddEventCommandTest {
    private final Group groupZero = new GroupBuilder().build();
    private final Model modelStubWithGroupList = new ModelStubWithGroupList().withGroups(groupZero);
    private final Index indexZero = Index.fromZeroBased(0);
    private final Index indexOne = Index.fromZeroBased(1);

    @Test
    public void constructor_nullParams_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddEventCommand(null, null));
        assertThrows(NullPointerException.class, () -> new AddEventCommand(null, VALID_EVENT));
        assertThrows(NullPointerException.class, () -> new AddEventCommand(indexZero, null));
    }

    @Test
    public void execute_validIndex_eventAddedSuccessfully() throws Exception {
        AddEventCommand command = new AddEventCommand(indexZero, VALID_EVENT);

        CommandResult result = command.execute(modelStubWithGroupList);

        // Check success message contains event and group info
        String expectedMessage = String.format(AddEventCommand.MESSAGE_SUCCESS,
                VALID_EVENT, groupZero.getName());
        assertEquals(expectedMessage, result.getFeedbackToUser());

        // Check the event was actually added to the group in the model stub
        assertTrue(groupZero.getEvents().contains(VALID_EVENT));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        AddEventCommand command = new AddEventCommand(indexOne, VALID_EVENT); // only one group at index 0
        assertThrows(CommandException.class, () -> command.execute(modelStubWithGroupList));
    }

    @Test
    public void execute_duplicateEvent_throwsCommandException() {
        // Add the event first to simulate duplicate
        groupZero.addEvent(VALID_EVENT);

        AddEventCommand command = new AddEventCommand(indexZero, VALID_EVENT);

        assertThrows(CommandException.class,
                AddEventCommand.MESSAGE_DUPLICATE_EVENT, () -> command.execute(modelStubWithGroupList));
    }

    @Test
    public void equals() {
        AddEventCommand addFirstCommand = new AddEventCommand(indexZero, VALID_EVENT);
        AddEventCommand addSecondCommand = new AddEventCommand(indexOne, VALID_EVENT);

        // same object -> returns true
        assertEquals(addFirstCommand, addFirstCommand);

        // same values -> returns true
        AddEventCommand addFirstCommandCopy = new AddEventCommand(indexZero, VALID_EVENT);
        assertEquals(addFirstCommand, addFirstCommandCopy);

        // different types -> returns false
        assertFalse(addFirstCommand.equals(5.0f));

        // null -> returns false
        assertFalse(addFirstCommand.equals(null));

        // different group index -> returns false
        assertNotEquals(addFirstCommand, addSecondCommand);

        // different event -> returns false
        assertNotEquals(addFirstCommand, new AddEventCommand(indexZero, VALID_EVENT_2));
    }

    @Test
    public void toStringTest() {
        AddEventCommand command = new AddEventCommand(indexZero, VALID_EVENT);
        String expected = AddEventCommand.class.getCanonicalName() + "{groupIndex=" + indexZero
                + ", toAdd=" + VALID_EVENT + "}";
        assertEquals(expected, command.toString());
    }
}
