package seedu.address.logic.commands.event;

import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.logic.commands.ModelStub;
import seedu.address.model.group.Group;

/**
 * A default model stub that has all methods failing except getFilteredGroupList.
 */
public class ModelStubWithGroupList extends ModelStub {
    // empty group list
    private final ObservableList<Group> groupList = FXCollections.observableArrayList();

    /**
     * Creates a model stub with the given {@code Groups}
     */
    public ModelStub withGroups(Group... groups) {
        groupList.addAll(Arrays.asList(groups));
        return this;
    }

    @Override
    public ObservableList<Group> getFilteredGroupList() {
        return new FilteredList<>(groupList);
    }
}