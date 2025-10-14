package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.UniqueEventList;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.person.Name;
import seedu.address.model.person.UniquePersonList;

/**
 * Jackson-friendly version of {@link Group}.
 */
class JsonAdaptedGroup {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Group's %s field is missing!";

    private final String groupName;
    private final UniquePersonList persons;
    private final UniqueEventList events;

    /**
     * Constructs a {@code JsonAdaptedGroup} with the given group details.
     */
    @JsonCreator
    public JsonAdaptedGroup(@JsonProperty("name") String groupName, @JsonProperty("persons") UniquePersonList persons,
                            @JsonProperty("events") UniqueEventList events) {
        this.groupName = groupName;
        this.persons = persons;
        this.events = events;
    }

    /**
     * Converts a given {@code Group} into this class for Jackson use.
     */
    public JsonAdaptedGroup(Group source) {
        groupName = source.getName().fullName;
        persons = source.getPersons();
        events = source.getEvents();
    }

    /**
     * Converts this Jackson-friendly adapted group object into the model's {@code group} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted group.
     */
    public Group toModelType() throws IllegalValueException {
        if (groupName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                                            GroupName.class.getSimpleName()));
        }
        if (!GroupName.isValidName(groupName)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final GroupName modelName = new GroupName(groupName);

        if (persons == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                                                        UniquePersonList.class.getSimpleName()));
        }

        final UniquePersonList modelPersons = new UniquePersonList();
        modelPersons.setPersons(persons);

        if (events == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                                                    UniqueEventList.class.getSimpleName()));
        }

        final UniqueEventList modelEvents = new UniqueEventList();
        modelEvents.setEvents(events);

        return Group.fromStorage(modelName, modelEvents, modelPersons);
    }

}
