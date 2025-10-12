package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.group.Group;

/**
 * A utility class containing a list of {@code Group} objects to be used in tests.
 */
public class TypicalGroups {

    public static final Group CS2103T = new GroupBuilder().withName("CS2103T").build();
    public static final Group CS2101_CA2 = new GroupBuilder().withName("CS2101 CA2").build();
    public static final Group CS2101_CA3 = new GroupBuilder().withName("CS2101 CA3").build();
    public static final Group IS1108 = new GroupBuilder().withName("IS1108").build();

    private TypicalGroups() {
    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical groups.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Group group : getTypicalGroups()) {
            ab.addGroup(group);
        }
        return ab;
    }

    public static List<Group> getTypicalGroups() {
        return new ArrayList<>(Arrays.asList(CS2103T, CS2101_CA2, CS2101_CA3, IS1108));
    }
}
