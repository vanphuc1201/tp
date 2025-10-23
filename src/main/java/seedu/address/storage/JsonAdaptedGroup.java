package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.group.RepoLink;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.storage.event.JsonAdaptedEvent;

/**
 * Jackson-friendly version of {@link Group}.
 */
class JsonAdaptedGroup {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Group's %s field is missing!";
    public static final String MESSAGE_DUPLICATE_PERSON = "Group %s contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_EVENT = "Group %s contains duplicate event(s).";
    public static final String MESSAGE_INVALID_GROUP_REPO = "Group %s repository link is broken.";

    private final String groupName;
    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedEvent> events = new ArrayList<>();
    private final String repoLink;

    /**
     * Constructs a {@code JsonAdaptedGroup} with the given group details.
     */
    @JsonCreator
    public JsonAdaptedGroup(@JsonProperty("name") String groupName,
                            @JsonProperty("persons") List<JsonAdaptedPerson> persons,
                            @JsonProperty("events") List<JsonAdaptedEvent> events,
                            @JsonProperty("repoLink") String repoLink) {
        this.groupName = groupName;
        this.persons.addAll(persons);
        this.events.addAll(events);
        this.repoLink = repoLink;
    }

    /**
     * Converts a given {@code Group} into this class for Jackson use.
     */
    public JsonAdaptedGroup(Group source) {
        groupName = source.getName().fullName;
        persons.addAll(source.getPersons()
                .stream().map(JsonAdaptedPerson::new).toList());
        events.addAll(source.getEvents()
                .stream().map(JsonAdaptedEvent::new).toList());
        repoLink = source.getRepoLink().toString();
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
        final Group group = new Group(modelName);

        // Populate member list
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (group.containsPerson(person)) {
                throw new IllegalValueException(String.format(MESSAGE_DUPLICATE_PERSON, groupName));
            }
            group.addPerson(person);
        }

        // Populate event list
        for (JsonAdaptedEvent jsonAdaptedEvent : events) {
            Event event = jsonAdaptedEvent.toModelType();
            if (group.containsEvent(event)) {
                throw new IllegalValueException(String.format(MESSAGE_DUPLICATE_EVENT, groupName));
            }
            group.addEvent(event);
        }

        if (repoLink != "None" && !RepoLink.isValidName(repoLink)) {
            throw new IllegalValueException(String.format(MESSAGE_INVALID_GROUP_REPO, groupName));
        }

        return group.setRepoLink(RepoLink.fromStorage(repoLink));
    }
}
