package seedu.address.logic.commands.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_INDEX;

import java.util.HashSet;
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
 * Adds a person to the specified group.
 */
public class AddMemberCommand extends Command {

    public static final String COMMAND_WORD = "add-member";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person specified by index number"
            + "to the group also specified by index number\n"
            + "Parameters: "
            + PREFIX_GROUP_INDEX + "GROUP INDEX (must be a positive integer) "
            + PREFIX_CONTACT_INDEX + "CONTACT INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_GROUP_INDEX + "1 "
            + PREFIX_CONTACT_INDEX + "2";

    public static final String MESSAGE_SUCCESS = "New member: '%1$s' added to group: '%2$s'";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person has already been added to the group";

    private final Index groupIndex;
    private final Index contactIndex;

    /**
     * Creates an AddMemberCommand to add the specified {@code person} to the specified {@code group}
     */
    public AddMemberCommand(Index groupIndex, Index contactIndex) {
        requireAllNonNull(contactIndex, groupIndex);
        this.contactIndex = contactIndex;
        this.groupIndex = groupIndex;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownPersonList = model.getFilteredPersonList();
        List<Group> lastShownGroupList = model.getFilteredGroupList();

        if (contactIndex.getZeroBased() >= lastShownPersonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (groupIndex.getZeroBased() >= lastShownGroupList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownPersonList.get(contactIndex.getZeroBased());
        Group groupToAddTo = lastShownGroupList.get(groupIndex.getZeroBased());
        Person toAdd = addGroupToPerson(groupToAddTo.getName(), personToEdit);

        if (groupToAddTo.containsPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToEdit, toAdd);
        model.addPersonToGroup(groupToAddTo, toAdd);

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(personToEdit),
                Messages.format(groupToAddTo)));
    }

    private static Person addGroupToPerson(GroupName groupName, Person toAddTo) {
        requireAllNonNull(groupName, toAddTo);
        Name name = toAddTo.getName();
        Phone phone = toAddTo.getPhone();
        Email email = toAddTo.getEmail();
        Set<GroupName> groups = new HashSet<>(toAddTo.getGroups());
        groups.add(groupName);

        return new Person(name, phone, email, groups);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddMemberCommand)) {
            return false;
        }

        AddMemberCommand otherAddMemberCommand = (AddMemberCommand) other;
        return groupIndex.equals(otherAddMemberCommand.groupIndex)
                && contactIndex.equals(otherAddMemberCommand.contactIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("groupIndex", groupIndex)
                .add("contactIndex", contactIndex)
                .toString();
    }
}
