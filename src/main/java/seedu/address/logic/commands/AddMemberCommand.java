package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_INDEX;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

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

        /*
        TODO: Actual implementation of whole execution sequence
        List<Person> = lastShownPersonList = model.getFilteredPersonList();
        List<Group> = lastShownGroupList = model.getFilteredGroupList();

        if (contactIndex.getZeroBased() >= lastShownPersonList) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (groupIndex.getZeroBased() >= lastShownGroupList) {
            throw new CommandException(Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
        }

        Person personToAdd = lastShownPersonList.get(contactIndex);
        Group groupToAddTo = lastShownGroupList.get(groupIndex);

        model.addPersonToGroup(groupToAddTo, personToAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(personToAdd),
                Messages.format(groupToAddTo));
         */

        return new CommandResult(String.format("Added member %s to group %s",
                this.contactIndex.getOneBased(), this.groupIndex.getOneBased()));
    }
}
