package seedu.address.logic.commands.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.commands.group.AddMemberCommand.MESSAGE_DUPLICATE_PERSON;
import static seedu.address.logic.commands.group.AddMemberCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_GROUP;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_GROUP;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.GEORGE_WITH_GROUP;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.ModelStub;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.person.Person;
import seedu.address.testutil.GroupBuilder;


public class AddMemberCommandTest {
    public static final Group CS2103T_WG = new GroupBuilder().withName("CS2103T")
            .withPersons(ALICE, BENSON, DANIEL, CARL, FIONA, GEORGE_WITH_GROUP)
            .build();

    public static final Group CS2103T = new GroupBuilder().withName("CS2103T")
            .withPersons(ALICE, BENSON, DANIEL, CARL, FIONA)
            .build();

    @Test
    public void constructor_nullGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddMemberCommand(null, INDEX_FIRST_PERSON));

        assertThrows(NullPointerException.class, () -> new AddMemberCommand(INDEX_FIRST_GROUP, null));
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        ModelStub modelStub = new ModelStubWithPersonAndGroup();
        Index invalidPerson = Index.fromOneBased(5);
        AddMemberCommand cmd = new AddMemberCommand(INDEX_FIRST_GROUP, invalidPerson);

        assertThrows(CommandException.class,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, () -> cmd.execute(modelStub));
    }

    @Test
    public void execute_invalidGroupIndex_throwsCommandException() {
        ModelStub modelStub = new ModelStubWithPersonAndGroup();
        Index invalidGroup = Index.fromOneBased(5);
        AddMemberCommand cmd = new AddMemberCommand(invalidGroup, INDEX_FIRST_PERSON);

        assertThrows(CommandException.class,
                Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX, () -> cmd.execute(modelStub));
    }

    @Test
    public void execute_duplicateGroupMember_throwsCommandException() {
        ModelStub modelStub = new ModelStubAcceptingAddMember();

        AddMemberCommand cmd = new AddMemberCommand(INDEX_FIRST_GROUP, INDEX_FIRST_PERSON);

        assertThrows(CommandException.class,
                MESSAGE_DUPLICATE_PERSON, () -> cmd.execute(modelStub));

    }

    @Test
    public void execute_memberAddedToGroup_success() throws CommandException {
        ModelStub modelStub = new ModelStubAcceptingAddMember();
        AddMemberCommand cmd = new AddMemberCommand(INDEX_FIRST_GROUP, INDEX_SECOND_PERSON);

        GroupName targetGroupName = modelStub.getFilteredGroupList().get(INDEX_FIRST_GROUP.getZeroBased()).getName();
        assertEquals(String.format(MESSAGE_SUCCESS,
                Messages.format(modelStub.getFilteredPersonList().get(1).addGroup(targetGroupName)),
                targetGroupName), cmd.execute(modelStub).getFeedbackToUser());

        assertEquals(CS2103T_WG, modelStub.getFilteredGroupList().get(0));

    }

    @Test
    public void equalsTest() {
        AddMemberCommand cmd1 = new AddMemberCommand(INDEX_FIRST_GROUP, INDEX_SECOND_PERSON);
        AddMemberCommand cmd2 = new AddMemberCommand(INDEX_FIRST_GROUP, INDEX_SECOND_PERSON);
        AddMemberCommand cmd3 = new AddMemberCommand(INDEX_SECOND_GROUP, INDEX_FIRST_PERSON);
        AddMemberCommand cmd4 = new AddMemberCommand(INDEX_FIRST_GROUP, INDEX_THIRD_PERSON);

        assertTrue(cmd1.equals(cmd2));
        assertTrue(cmd1.equals(cmd1));
        assertFalse(cmd1.equals(cmd3));
        assertFalse(cmd1.equals(cmd4));
        assertFalse(cmd1.equals(null));
        assertFalse(cmd1.equals(1));
    }

    @Test
    public void toStringTest() {
        AddMemberCommand cmd1 = new AddMemberCommand(INDEX_FIRST_GROUP, INDEX_SECOND_PERSON);
        String expected = AddMemberCommand.class.getCanonicalName() + "{groupIndex=" + INDEX_FIRST_GROUP + ", "
                + "contactIndex=" + INDEX_SECOND_PERSON + "}";
        assertEquals(expected, cmd1.toString());
    }




    private class ModelStubWithPersonAndGroup extends ModelStub {
        private final List<Person> persons = new ArrayList<>(List.of(ALICE, GEORGE));
        private final List<Group> groups = new ArrayList<>(List.of(CS2103T));

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return FXCollections.observableArrayList(persons);
        }

        @Override
        public ObservableList<Group> getFilteredGroupList() {
            return FXCollections.observableArrayList(groups);
        }
    }

    private class ModelStubAcceptingAddMember extends ModelStubWithPersonAndGroup {
        @Override
        public void setPerson(Person target, Person editedPerson) {}

        @Override
        public Person addPersonToGroups(Set<Index> targetGroupIndex, Person toAdd) {
            requireAllNonNull(targetGroupIndex, toAdd);

            //create person with groups from targetGroupIndex
            Person personToAdd = toAdd;
            for (Index index : targetGroupIndex) {
                Group groupToAddTo = getFilteredGroupList().get(index.getZeroBased());
                personToAdd = personToAdd.addGroup(new GroupName(groupToAddTo.getName().toString()));
            }

            //add person to group member list
            for (Index index : targetGroupIndex) {
                Group groupToAddTo = getFilteredGroupList().get(index.getZeroBased());
                groupToAddTo.addPerson(personToAdd);
            }

            return personToAdd;

        }
    }
}
