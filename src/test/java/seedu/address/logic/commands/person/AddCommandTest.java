package seedu.address.logic.commands.person;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ModelStub;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.person.Person;
import seedu.address.testutil.GroupBuilder;
import seedu.address.testutil.PersonBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null, new HashSet<>()));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        //set up valid arg for add command
        Person validPerson = new PersonBuilder().build();
        Group defaultGroup = new GroupBuilder().build();
        ModelStubForFullAddCommand modelStub = new ModelStubForFullAddCommand(null,defaultGroup);
        Set<Index> validGroupIndexes = Set.of(Index.fromOneBased(1));
        CommandResult commandResult = new AddCommand(validPerson, validGroupIndexes).execute(modelStub);

        //set up expected person with group added
        Person finalisePerson = validPerson.addGroup(new GroupName(defaultGroup.getName().toString()));

        //assertion of commandResult and model changes
        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(finalisePerson)),
                commandResult.getFeedbackToUser());
        assertEquals(List.of(finalisePerson), modelStub.persons);
        assertEquals(List.of(defaultGroup), modelStub.groups);
    }

    @Test
    public void execute_invalidGroupIndex_throwsCommandException() {
        Person validPerson = new PersonBuilder().build();
        ModelStub modelStub = new ModelStubForErrorCheck(null,null);

        // Assuming group list is empty, index 1 is invalid
        Set<Index> invalidGroupIndexes = new HashSet<>();
        invalidGroupIndexes.add(Index.fromOneBased(1));

        AddCommand addCommand = new AddCommand(validPerson, invalidGroupIndexes);

        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX, () -> addCommand
                .execute(modelStub));
    }

    @Test
    public void execute_personAlreadyInGroup_throwsCommandException() throws Exception {
        // setup model stub with one group
        Person validPerson = new PersonBuilder().build().addGroup(new GroupName("CS2103T"));
        Group groupWithValidPerson = new GroupBuilder().withName("CS2103T").withPersons(validPerson).build();
        ModelStubForErrorCheck modelStub = new ModelStubForErrorCheck(null, groupWithValidPerson);

        // Valid group index
        Set<Index> validGroupIndexes = Set.of(Index.fromOneBased(1));

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_PERSON_IN_GROUP, () ->
                new AddCommand(validPerson, validGroupIndexes).execute(modelStub));
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person validPerson = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validPerson, new HashSet<>());
        ModelStub modelStub = new ModelStubForErrorCheck(validPerson,null);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_PERSON, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice, Set.of(Index.fromOneBased(1)));
        AddCommand addBobCommand = new AddCommand(bob, new HashSet<>());

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice, Set.of(Index.fromOneBased(1)));
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));

        // different group index -> returns false
        AddCommand aliceWithDiffGroupIndex = new AddCommand(alice, Set.of(Index.fromOneBased(2)));
        assertFalse(addAliceCommand.equals(aliceWithDiffGroupIndex));
    }

    @Test
    public void toStringMethod() {
        Set<Index> groupsIndexes = new HashSet<>();
        groupsIndexes.add(Index.fromOneBased(1));

        AddCommand addCommand = new AddCommand(ALICE, groupsIndexes);
        String expected = AddCommand.class.getCanonicalName() + "{toAdd=" + ALICE + ", groupsIndexes="
                + groupsIndexes + "}";
        assertEquals(expected, addCommand.toString());
    }


    /**
     * A Model stub with default a single person and a single group.
     */
    private class ModelStubForErrorCheck extends ModelStub {
        final ArrayList<Person> persons = new ArrayList<>();
        final ArrayList<Group> groups = new ArrayList<>();

        ModelStubForErrorCheck(Person person, Group group) {
            if(person != null) {
                persons.add(person);
            }
            if(group != null) {
                groups.add(group);
            }
        }
        @Override
        public ObservableList<Group> getFilteredGroupList() {
            return FXCollections.observableList(groups);
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return persons.stream().anyMatch(person::isSamePerson);
        }
    }

    /**
     * A Model stub with default one group. Setup for add command execution.
     */
    private class ModelStubForFullAddCommand extends ModelStubForErrorCheck {

        ModelStubForFullAddCommand(Person person, Group group) {
            super(person, group);
        }

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

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            persons.add(person);
        }

    }

}
