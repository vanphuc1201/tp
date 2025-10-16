package seedu.address.logic.commands.person;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ModelStub;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.Group;
import seedu.address.model.group.UniqueGroupList;
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
        // setup model stub with one group
        ModelStubForFullAddCommand modelStub = new ModelStubForFullAddCommand();
        Group group = new GroupBuilder().build();
        modelStub.addGroup(group);

        // Valid group index
        Set<Index> validGroupIndexes = new HashSet<>();
        validGroupIndexes.add(Index.fromOneBased(1));

        Person validPerson = new PersonBuilder().build();
        CommandResult commandResult = new AddCommand(validPerson, validGroupIndexes).execute(modelStub);
        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
    }

    @Test
    public void execute_invalidGroupIndex_throwsCommandException() {
        Person validPerson = new PersonBuilder().build();
        ModelStub modelStub = new ModelStubWithPerson(validPerson);

        // Assuming group list is empty, index 1 is invalid
        Set<Index> invalidGroupIndexes = new HashSet<>();
        invalidGroupIndexes.add(Index.fromOneBased(1));

        AddCommand addCommand = new AddCommand(validPerson, invalidGroupIndexes);

        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX, () -> addCommand
                .execute(modelStub));
    }

    @Test
    public void execute_personAlreadyInGroup_addSuccessful() throws Exception {
        // setup model stub with one group
        ModelStubForFullAddCommand modelStub = new ModelStubForFullAddCommand();
        Person validPerson = new PersonBuilder().build();
        Group group = new GroupBuilder().withPersons(validPerson).build();
        modelStub.addGroup(group);

        // Valid group index
        Set<Index> validGroupIndexes = new HashSet<>();
        validGroupIndexes.add(Index.fromOneBased(1));

        CommandResult commandResult = new AddCommand(validPerson, validGroupIndexes).execute(modelStub);
        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person validPerson = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validPerson, new HashSet<>());
        ModelStub modelStub = new ModelStubWithPerson(validPerson);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_PERSON, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice, new HashSet<>());
        AddCommand addBobCommand = new AddCommand(bob, new HashSet<>());

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice, new HashSet<>());
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddCommand addCommand = new AddCommand(ALICE, new HashSet<>());
        String expected = AddCommand.class.getCanonicalName() + "{toAdd=" + ALICE + ", groupsIndexes="
                + ALICE.getGroups() + "}";
        assertEquals(expected, addCommand.toString());
    }


    /**
     * A Model stub that contains a single person with getFilteredGroups.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Person person;
        private final FilteredList<Group> filteredGroups = new FilteredList<>(new UniqueGroupList()
                .asUnmodifiableObservableList());

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
        }
        @Override
        public ObservableList<Group> getFilteredGroupList() {
            return filteredGroups;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }
    }

    /**
     * A Model stub that allow methods required for a complete AddCommand.
     */
    private class ModelStubForFullAddCommand extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();
        private UniqueGroupList groups = new UniqueGroupList();

        private final FilteredList<Group> filteredGroups = new FilteredList<>(groups.asUnmodifiableObservableList());

        @Override
        public ObservableList<Group> getFilteredGroupList() {
            return filteredGroups;
        }

        @Override
        public void addGroup(Group group) {
            requireNonNull(group);
            groups.add(group);
        }

        @Override
        public void addPersonToGroup(Group targetGroup, Person toAdd) {
            requireAllNonNull(targetGroup, toAdd);
            groups.addPersonToGroup(targetGroup, toAdd);
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

    }

}
