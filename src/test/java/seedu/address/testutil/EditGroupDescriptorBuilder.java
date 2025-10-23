package seedu.address.testutil;

import seedu.address.logic.commands.group.EditGroupCommand.EditGroupDescriptor;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;

/**
 * A utility class to help with building EditGroupDescriptor objects.
 */
public class EditGroupDescriptorBuilder {

    private EditGroupDescriptor descriptor;

    public EditGroupDescriptorBuilder() {
        descriptor = new EditGroupDescriptor();
    }

    public EditGroupDescriptorBuilder(EditGroupDescriptor descriptor) {
        this.descriptor = new EditGroupDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditGroupDescriptor} with fields containing {@code group}'s details
     */
    public EditGroupDescriptorBuilder(Group group) {
        descriptor = new EditGroupDescriptor();
        descriptor.setGroupName(group.getName());
    }

    /**
     * Sets the {@code Name} of the {@code EditGroupDescriptor} that we are building.
     */
    public EditGroupDescriptorBuilder withName(String name) {
        descriptor.setGroupName(new GroupName(name));
        return this;
    }

    public EditGroupDescriptor build() {
        return descriptor;
    }
}
