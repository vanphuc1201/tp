package seedu.address.testutil;

import seedu.address.model.event.UniqueEventList;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.person.UniquePersonList;

/**
 * A utility class to help with building Group objects.
 */
public class GroupBuilder {

    public static final String DEFAULT_GROUP_NAME = "CS2103T";

    private GroupName name;
    private UniqueEventList events;
    private UniquePersonList persons;

    /**
     * Creates a {@code GroupBuilder} with the default details.
     */
    public GroupBuilder() {
        name = new GroupName(DEFAULT_GROUP_NAME);
        events = new UniqueEventList();
        persons = new UniquePersonList();

        persons.add(new PersonBuilder().withName("Van Phuc").build());
        persons.add(new PersonBuilder().withName("Gang Quan").build());
    }

    /**
     * Initializes the GroupBuilder with the data of {@code groupToCopy}.
     */
    public GroupBuilder(Group groupToCopy) {
        name = groupToCopy.getName();
        events = groupToCopy.getEvents();
        persons = groupToCopy.getPersons();
    }

    /**
     * Sets the {@code GroupName} of the {@code Group} that we are building.
     */
    public GroupBuilder withName(String name) {
        this.name = new GroupName(name);
        return this;
    }

    public Group build() {
        return new Group(name, events, persons);
    }

}
