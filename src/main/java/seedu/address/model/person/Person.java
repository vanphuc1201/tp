package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.group.GroupName;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    private final Set<GroupName> groups;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Set<GroupName> groups) {
        requireAllNonNull(name, phone, email, groups);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.groups = Set.copyOf(groups);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Set<GroupName> getGroups() {
        return Collections.unmodifiableSet(groups);
    }

    /**
     * Returns a new {@code Person} instance with the specified {@code GroupName} added.
     * <p>
     * This method does not modify the existing {@code Person} object. Instead, it creates
     * a new {@code Person} containing all the current groups together with the added group.
     * </p>
     *
     * @param groupName the group to be added to this person's group set
     * @return a new {@code Person} instance with the specified group added
     */
    public Person addGroup(GroupName groupName) {
        Set<GroupName> newGroups = new HashSet<>(groups);
        newGroups.add(groupName);
        return new Person(name, phone, email, newGroups);
    }

    /**
     * Returns a new {@code Person} instance with the specified {@code GroupName} removed.
     * <p>
     * This method does not modify the existing {@code Person} object. Instead, it creates
     * a new {@code Person} containing all the current groups without removed group.
     * </p>
     *
     * @param groupName the group to be removed from this person's group set
     * @return a new {@code Person} instance with the specified group removed
     */
    public Person removeGroup(GroupName groupName) {
        Set<GroupName> newGroups = new HashSet<>(groups);
        newGroups.remove(groupName);
        return new Person(name, phone, email, newGroups);
    }

    /**
     * Returns the persons' name as a String
     */
    public String getNameAsString() {
        return getName().toString();
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && groups.equals(otherPerson.groups);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, groups);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("groups", groups)
                .toString();
    }

}
