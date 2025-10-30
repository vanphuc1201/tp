package seedu.address.logic.commands.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.commands.group.ShowDashboardCommand.MESSAGE_SHOW_DASHBOARD_SUCCESS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_GROUP;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_GROUP;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.event.ModelStubWithGroupList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.testutil.GroupBuilder;

public class ShowDashboardCommandTest {
    private Group groupToShow = new GroupBuilder().withName("CS2103T").build();
    private Model model = new ModelStubWithGroupList().withGroups(groupToShow);

    @Test
    public void execute_showDashboard_success() {
        ShowDashboardCommand cmd = new ShowDashboardCommand(INDEX_FIRST_GROUP);
        try {
            assertEquals(String.format(MESSAGE_SHOW_DASHBOARD_SUCCESS, groupToShow.getNameAsString()),
                    cmd.execute(model).getFeedbackToUser());
        } catch (CommandException e) {
            fail("Command should have been executed but was not");
        }
    }

    @Test
    public void execute_invalidGroupIndex_throwsCommandException() {
        ShowDashboardCommand cmd = new ShowDashboardCommand(INDEX_SECOND_GROUP);

        assertThrows(CommandException.class,
                Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX, () -> cmd.execute(model));
    }

    @Test
    public void equalsTest() {
        ShowDashboardCommand cmd1 = new ShowDashboardCommand(INDEX_FIRST_GROUP);
        ShowDashboardCommand cmd2 = new ShowDashboardCommand(INDEX_SECOND_GROUP);

        // same object -> returns true
        assertTrue(cmd1.equals(cmd1));

        // same values -> returns true
        ShowDashboardCommand cmd3 = new ShowDashboardCommand(INDEX_FIRST_GROUP);
        assertTrue(cmd1.equals(cmd3));

        // different types -> returns false
        assertFalse(cmd1.equals(1));

        // null -> returns false
        assertFalse(cmd1.equals(null));

        // different group -> returns false
        assertFalse(cmd1.equals(cmd2));
    }

    @Test
    public void toStringTest() {
        Index targetIndex = Index.fromOneBased(1);
        ShowDashboardCommand showDashboardCommand = new ShowDashboardCommand(targetIndex);
        String expected = ShowDashboardCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, showDashboardCommand.toString());
    }

}
