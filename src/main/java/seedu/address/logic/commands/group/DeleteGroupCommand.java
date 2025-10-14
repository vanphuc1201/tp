package seedu.address.logic.commands.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * Deletes a group identified using its displayed index from the address book.
 */
public class DeleteGroupCommand extends Command {

    public static final String COMMAND_WORD = "delete-group";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the group identified by the index number used in the displayed group list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_GROUP_SUCCESS = "This Group has been deleted: %1$s";

    private final Index targetIndex;

    /**
     * Creates a DeleteGroupCommand to delete the specified {@code Group}
     */
    public DeleteGroupCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Group> lastShownGroupList = model.getFilteredGroupList();

        if (targetIndex.getZeroBased() >= lastShownGroupList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
        }

        Group groupToDelete = lastShownGroupList.get(targetIndex.getZeroBased());
        for (Person personToEdit : groupToDelete.getPersons()) {
            System.out.println(personToEdit.toString());
            Person newPerson = removeGroupFromPerson(groupToDelete.getName(), personToEdit);
            model.setPerson(personToEdit, newPerson);
        }
        model.deleteGroup(groupToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_GROUP_SUCCESS, Messages.format(groupToDelete)));
    }

    /**
     * Removes the group from the person's set of groups.
     * Returns a new Person object with the updated set of groups.
     */
    private static Person removeGroupFromPerson(GroupName groupName, Person toRemove) {
        requireAllNonNull(groupName, toRemove);
        Name name = toRemove.getName();
        Phone phone = toRemove.getPhone();
        Email email = toRemove.getEmail();
        Set<GroupName> groups = toRemove.getGroups();
        groups.remove(groupName);

        return new Person(name, phone, email, groups);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteGroupCommand)) {
            return false;
        }

        DeleteGroupCommand otherDeleteGroupCommand = (DeleteGroupCommand) other;
        return targetIndex.equals(otherDeleteGroupCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
