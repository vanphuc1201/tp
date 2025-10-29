package seedu.address.logic.commands.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

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
import seedu.address.model.person.Person;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add-contact";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + "[" + PREFIX_GROUP_INDEX + "GROUPS]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "12345678 "
            + PREFIX_EMAIL + "e1234567@u.nus.edu "
            + PREFIX_GROUP_INDEX + "1 "
            + PREFIX_GROUP_INDEX + "2 ";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person's name or phone or email "
            + "already exists in the address book.\n"
            + "All name, phone and email need to be unique.";
    public static final String MESSAGE_DUPLICATE_PERSON_IN_GROUP =
            "This person is already in one of the specified groups";
    private final Set<Index> groupsIndexes;
    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person, Set<Index> groupsIndexes) {
        requireNonNull(person);
        toAdd = person;
        this.groupsIndexes = groupsIndexes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Group> lastShownGroupList = model.getFilteredGroupList();

        // Check that all group indexes are valid
        if (groupsIndexes.stream().anyMatch(idx -> idx.getZeroBased() >= lastShownGroupList.size())) {
            throw new CommandException(Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
        }

        // check for duplicate person in group's member list
        if (groupsIndexes.stream().map(i -> lastShownGroupList.get(i.getZeroBased()))
                .anyMatch(g -> g.containsPerson(toAdd))) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON_IN_GROUP);
        }

        // check for duplicate person
        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        //Adding the person to the group's member list and return the finalised person
        Person finalisePerson = model.addPersonToGroups(groupsIndexes, toAdd);

        //add the finalise person to the address book
        model.addPerson(finalisePerson);

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(finalisePerson)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd) && groupsIndexes.equals(otherAddCommand.groupsIndexes);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .add("groupsIndexes", groupsIndexes)
                .toString();
    }
}
