package seedu.address.testutil;

import seedu.address.model.event.UniqueEventList;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.group.RepoLink;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * A utility class to help with building Group objects.
 */
public class GroupBuilder {

    public static final String DEFAULT_GROUP_NAME = "CS2103T";

    private GroupName name;
    private UniqueEventList events;
    private UniquePersonList persons;
    private RepoLink repoLink;

    /**
     * Creates a {@code GroupBuilder} with the default details.
     */
    public GroupBuilder() {
        name = new GroupName(DEFAULT_GROUP_NAME);
        events = new UniqueEventList();
        persons = new UniquePersonList();
        repoLink = new RepoLink();
    }

    /**
     * Initializes the GroupBuilder with the data of {@code groupToCopy}.
     */
    public GroupBuilder(Group groupToCopy) {
        name = groupToCopy.getName();
        events = groupToCopy.getEvents();
        this.persons = new UniquePersonList();
        for (Person p : groupToCopy.getPersons()) {
            this.persons.add(new PersonBuilder(p).build());
        }
        repoLink = groupToCopy.getRepoLink();
    }

    /**
     * Sets the {@code GroupName} of the {@code Group} that we are building.
     */
    public GroupBuilder withName(String name) {
        this.name = new GroupName(name);
        return this;
    }

    /**
     * Sets the {@code GroupEvent} of the {@code Group} that we are building.
     */
    public GroupBuilder withEvents(UniqueEventList events) {
        this.events = events;
        return this;
    }

    /**
     * Sets the {@code GroupName} of the {@code Group} that we are building.
     */
    public GroupBuilder withPersons(Person... persons) {
        this.persons = new UniquePersonList();
        for (Person person : persons) {
            this.persons.add(person);
        }
        return this;
    }

    /**
     * Sets the {@code RepoLink} of the {@code Group} that we are building.
     */
    public GroupBuilder withRepoLink(RepoLink repoLink) {
        this.repoLink = repoLink;
        return this;
    }

    public Group build() {
        return Group.fromStorage(name, events, persons, repoLink);
    }

}
