package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.group.GroupName;

/**
 * Jackson-friendly version of {@link GroupName}.
 */
public class JsonAdaptedGroupName {
    private final String groupName;

    /**
     * Constructs a {@code JsonAdaptedGroupName} with the given {@code groupName}.
     */
    @JsonCreator
    public JsonAdaptedGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * Converts a given {@code GroupName} into this class for Jackson use.
     */
    public JsonAdaptedGroupName(GroupName source) {
        groupName = source.fullName;
    }

    @JsonValue
    public String getGroupName() {
        return groupName;
    }

    /**
     * Converts this Jackson-friendly adapted groupName object into the model's {@code GroupName} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted groupName.
     */
    public GroupName toModelType() throws IllegalValueException {
        if (!GroupName.isValidName(groupName)) {
            throw new IllegalValueException(GroupName.MESSAGE_CONSTRAINTS);
        }
        return new GroupName(groupName);
    }

}
