package seedu.address.logic.commands.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.group.RepoLinkTest.VALID_DIFFERENT_REPO;
import static seedu.address.model.group.RepoLinkTest.VALID_REPO;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_GROUP;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_GROUP;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ModelStub;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.group.RepoLink;
import seedu.address.testutil.GroupBuilder;

public class SetRepoCommandTest {
    private final Model modelStubWithGroupList = new ModelStubWithGroupList();

    @Test
    public void constructor_nullParams_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SetRepoCommand(null, null));
        assertThrows(NullPointerException.class, () -> new SetRepoCommand(null, VALID_REPO));
        assertThrows(NullPointerException.class, () -> new SetRepoCommand(INDEX_FIRST_GROUP, null));
    }

    @Test
    public void execute_validIndex_success() throws Exception {
        Group firstGroup = modelStubWithGroupList.getFilteredGroupList().get(0);
        SetRepoCommand command = new SetRepoCommand(INDEX_FIRST_GROUP, VALID_REPO);

        CommandResult result = command.execute(modelStubWithGroupList);

        // Check success message contains the correct repo link
        String expectedMessage = String.format(SetRepoCommand.MESSAGE_SUCCESS,
                firstGroup.getName(), VALID_REPO);
        assertEquals(expectedMessage, result.getFeedbackToUser());

        Group resultGroup = modelStubWithGroupList.getFilteredGroupList().get(0);
        // Check the repo was actually added to the group in the model stub
        assertTrue(resultGroup.getRepoLink().equals(VALID_REPO));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        SetRepoCommand command = new SetRepoCommand(INDEX_SECOND_GROUP, VALID_REPO); // only one group at index 0
        assertThrows(CommandException.class, () -> command.execute(modelStubWithGroupList));
    }

    @Test
    public void equals() {
        SetRepoCommand setFirstRepoCommand = new SetRepoCommand(INDEX_FIRST_GROUP, VALID_REPO);
        SetRepoCommand setSecondRepoCommand = new SetRepoCommand(INDEX_SECOND_GROUP, VALID_REPO);

        // same object -> returns true
        assertEquals(setFirstRepoCommand, setFirstRepoCommand);

        // same values -> returns true
        SetRepoCommand setFirstRepoCommandCopy = new SetRepoCommand(INDEX_FIRST_GROUP, VALID_REPO);
        assertEquals(setFirstRepoCommand, setFirstRepoCommandCopy);

        // different types -> returns false
        assertFalse(setFirstRepoCommand.equals(5.0f));

        // null -> returns false
        assertFalse(setFirstRepoCommand.equals(null));

        // different group index -> returns false
        assertNotEquals(setFirstRepoCommand, setSecondRepoCommand);

        // different repo -> returns false
        assertNotEquals(setFirstRepoCommand, new SetRepoCommand(INDEX_FIRST_GROUP, VALID_DIFFERENT_REPO));
    }

    @Test
    public void toStringTest() {
        SetRepoCommand command = new SetRepoCommand(INDEX_FIRST_GROUP, VALID_REPO);
        String expected = SetRepoCommand.class.getCanonicalName() + "{groupIndex=" + INDEX_FIRST_GROUP
                + ", repoLink=" + VALID_REPO + "}";
        assertEquals(expected, command.toString());
    }

    /**
     * A default model stub that has all methods failing except getFilteredGroupList and setGroupRepo
     */
    private class ModelStubWithGroupList extends ModelStub {

        private final ArrayList<Group> groups;

        public ModelStubWithGroupList() {
            groups = new ArrayList<>(List.of(new GroupBuilder().build()));
        }

        @Override
        public ObservableList<Group> getFilteredGroupList() {
            return FXCollections.observableList(groups);
        }

        @Override
        public void setGroupRepo(Group group, RepoLink repoLink) {
            int index = groups.indexOf(group);
            groups.set(index, group.setRepoLink(repoLink));
        }
    }
}
