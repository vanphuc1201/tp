package seedu.address.model.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalGroups.CS2101_CA2;
import static seedu.address.testutil.TypicalGroups.CS2103T;
import static seedu.address.testutil.TypicalGroups.IS1108;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.GroupBuilder;

public class GroupTest {

    @Test
    public void isSameGroup() {
        // same object -> returns true
        assertTrue(CS2103T.isSameGroup(CS2103T));

        // null -> returns false
        assertFalse(CS2103T.isSameGroup(null));

        // same name -> returns true
        Group editedCS2103T = new GroupBuilder().withName("CS2103T").build();
        assertTrue(CS2103T.isSameGroup(editedCS2103T));

        // different name, all other attributes same -> returns false
        editedCS2103T = new GroupBuilder(CS2103T).withName("New name").build();
        assertFalse(CS2103T.isSameGroup(editedCS2103T));

        // name differs in case, all other attributes same -> returns false
        Group editedIS1108 = new GroupBuilder().withName("IS1108".toLowerCase()).build();
        assertFalse(IS1108.isSameGroup(editedIS1108));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = "IS1108" + " ";
        editedIS1108 = new GroupBuilder().withName(nameWithTrailingSpaces).build();
        assertFalse(IS1108.isSameGroup(editedIS1108));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Group copyCS2103T = new GroupBuilder(CS2103T).build();
        assertTrue(CS2103T.equals(copyCS2103T));

        // same object -> returns true
        assertTrue(CS2103T.equals(CS2103T));

        // null -> returns false
        assertFalse(CS2103T.equals(null));

        // different type -> returns false
        assertFalse(CS2103T.equals(5));

        // different person -> returns false
        assertFalse(CS2103T.equals(CS2101_CA2));

        // different name -> returns false
        Group editedCS2103T = new GroupBuilder().withName("Diff name").build();
        assertFalse(CS2103T.equals(editedCS2103T));

        // different person list -> returns false
        editedCS2103T = new GroupBuilder(CS2103T).withPersons(ALICE, BENSON, ELLE, CARL, FIONA).build();
        assertFalse(CS2103T.equals(editedCS2103T));

    }

    @Test
    public void toStringMethod() {
        String expected = Group.class.getCanonicalName() + "{name=" + CS2103T.getName()
                + ", events=" + CS2103T.getEvents()
                + ", persons=" + CS2103T.getPersons()
                + ", repo-link=" + CS2103T.getRepoLink()+"}";
        assertEquals(expected, CS2103T.toString());
    }
}
