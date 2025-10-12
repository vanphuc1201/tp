package seedu.address.model.group;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.event.Event;
import seedu.address.model.event.UniqueEventList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * Represents a Group in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Group {

    // Identity fields
    private final GroupName name;

    // Data fields
    private final UniqueEventList events = new UniqueEventList();
    private final UniquePersonList persons = new UniquePersonList();

    /**
     * Every field must be present and not null.
     */
    public Group(GroupName name) {
        requireNonNull(name);
        this.name = name;
    }

    public GroupName getName() {
        return name;
    }

    public UniqueEventList getEvents() {
        return events;
    }

    public UniquePersonList getPersons() {
        return persons;
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public void addPerson(Person toAdd) {
        persons.add(toAdd);
    }

    public void removePerson(Person toRemove) {
        persons.remove(toRemove);
    }

    public void updatePersons(Person target, Person editedPerson) {
        persons.setPerson(target, editedPerson);
    }

    public void replacePersons(UniquePersonList replacement) {
        persons.setPersons(replacement);
    }

    public void replacePersons(List<Person> replacement) {
        persons.setPersons(replacement);
    }

    /**
     * Returns true if the group contains an equivalent person as the given argument.
     */
    public boolean containsPerson(Person personToCheck) {
        return persons.contains(personToCheck);
    }

    /**
     * Returns true if both groups have the same name.
     * This defines a weaker notion of equality between two groups.
     */
    public boolean isSameGroup(Group otherGroup) {
        if (otherGroup == this) {
            return true;
        }

        return otherGroup != null
                && otherGroup.getName().equals(getName());
    }

    /**
     * Returns true if both groups have the same identity and data fields.
     * This defines a stronger notion of equality between two groups.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Group)) {
            return false;
        }

        Group otherGroup = (Group) other;
        return name.equals(otherGroup.name)
                && events.equals(otherGroup.events)
                && persons.equals(otherGroup.persons);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, events, persons);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("events", events)
                .add("persons", persons)
                .toString();
    }

}
