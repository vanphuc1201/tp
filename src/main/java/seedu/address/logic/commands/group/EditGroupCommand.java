package seedu.address.logic.commands.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_GROUPS;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.person.Person;

/**
 * Edits the details of an existing group in the address book.
 */
public class EditGroupCommand extends Command {

    public static final String COMMAND_WORD = "edit-group";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the group identified "
            + "by the index number used in the displayed group list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "is1108 ca1 "
            + PREFIX_CONTACT_INDEX + "2 ";

    public static final String MESSAGE_EDIT_GROUP_SUCCESS = "Edited Group: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_GROUP = "This group already exists in the address book.";

    private final Index index;
    private final EditGroupDescriptor editGroupDescriptor;

    /**
     * @param index of the group in the filtered group list to edit
     * @param editGroupDescriptor details to edit the group with
     */
    public EditGroupCommand(Index index, EditGroupDescriptor editGroupDescriptor) {
        requireNonNull(index);
        requireNonNull(editGroupDescriptor);

        this.index = index;
        this.editGroupDescriptor = new EditGroupDescriptor(editGroupDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Retrieve current data
        List<Group> lastShownGroupList = model.getFilteredGroupList();

        // Validate group index
        if (index.getZeroBased() >= lastShownGroupList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
        }

        Group groupToEdit = lastShownGroupList.get(index.getZeroBased());
        Group editedGroup = createEditedGroup(groupToEdit, editGroupDescriptor);

        // Check for group duplicates
        if (!groupToEdit.isSameGroup(editedGroup) && model.hasGroup(editedGroup)) {
            throw new CommandException(MESSAGE_DUPLICATE_GROUP);
        }

        // Apply edits
        model.setGroup(groupToEdit, editedGroup);
        syncPersonWithEditedGroup(groupToEdit.getName(), model, editedGroup);
        model.updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
        return new CommandResult(String.format(MESSAGE_EDIT_GROUP_SUCCESS, Messages.format(editedGroup)));
    }

    /**
     * Updates all persons who are in the edited group to reflect the new group name.
     */
    private void syncPersonWithEditedGroup(GroupName nameOfGroupToEdit, Model model, Group editedGroup) {
        for (Person person : editedGroup.getPersons()) {
            Person editedPerson = person
                    .removeGroup(nameOfGroupToEdit)
                    .addGroup(editedGroup.getName());
            model.setPerson(person, editedPerson);
        }
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Group createEditedGroup(Group groupToEdit, EditGroupDescriptor editGroupDescriptor) {
        assert groupToEdit != null;

        GroupName updatedName = editGroupDescriptor.getName().orElse(groupToEdit.getName());

        return groupToEdit.withUpdatedName(updatedName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditGroupCommand)) {
            return false;
        }

        EditGroupCommand otherEditGroupCommand = (EditGroupCommand) other;
        return index.equals(otherEditGroupCommand.index)
                && editGroupDescriptor.equals(otherEditGroupCommand.editGroupDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editGroupDescriptor", editGroupDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the group with. Each non-empty field value will replace the
     * corresponding field value of the group.
     */
    public static class EditGroupDescriptor {
        private GroupName name;

        public EditGroupDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditGroupDescriptor(EditGroupDescriptor toCopy) {
            setGroupName(toCopy.name);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name);
        }

        public void setGroupName(GroupName name) {
            this.name = name;
        }

        public Optional<GroupName> getName() {
            return Optional.ofNullable(name);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditGroupDescriptor)) {
                return false;
            }

            EditGroupDescriptor otherEditGroupDescriptor = (EditGroupDescriptor) other;
            return Objects.equals(name, otherEditGroupDescriptor.name);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .toString();
        }
    }
}
