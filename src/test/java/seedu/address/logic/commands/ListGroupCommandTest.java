package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showGroupAtIndex;
import static seedu.address.testutil.TypicalGroups.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_GROUP;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.group.ListGroupCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListGroupCommand.
 */
public class ListGroupCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listGroupIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListGroupCommand(), model, ListGroupCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listGroupIsFiltered_showsEverything() {
        showGroupAtIndex(model, INDEX_FIRST_GROUP);
        assertCommandSuccess(new ListGroupCommand(), model, ListGroupCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
