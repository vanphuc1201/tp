package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;


public class DescriptionTest {

    public static final String VALID_DESCRIPTION_STRING = "Weekly report";
    public static final Description VALID_DESCRIPTION = new Description(VALID_DESCRIPTION_STRING);
    public static final Description VALID_DESCRIPTION_2 = new Description("Project meeting");

    @Test
    void constructor_validInput() {
        assertNotNull(VALID_DESCRIPTION);
        assertEquals(VALID_DESCRIPTION_STRING, VALID_DESCRIPTION.description());
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Description(null));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        String invalidDescription = "";
        assertThrows(IllegalArgumentException.class, () -> new Description(invalidDescription));
    }

    @Test
    public void isValidDescription() {
        // null description
        assertThrows(NullPointerException.class, () -> Description.isValidDescription(null));

        // invalid descriptions
        assertFalse(Description.isValidDescription("")); // empty string
        assertFalse(Description.isValidDescription(" ")); // spaces only

        // valid descriptions
        assertTrue(Description.isValidDescription(VALID_DESCRIPTION_STRING));
        assertTrue(Description.isValidDescription("valid description")); // alphabets only
        assertTrue(Description.isValidDescription("12345")); // numbers only
        assertTrue(Description.isValidDescription("A description with symbols!@#")); // alphanumeric with symbols
        assertTrue(Description.isValidDescription("peter the 2nd")); // alphanumeric with spaces
        assertTrue(Description.isValidDescription("A long description with many words that go on forever"));
    }

    @Test
    public void equals() {
        Description description = VALID_DESCRIPTION;

        // same values -> returns true
        assertTrue(description.equals(new Description(VALID_DESCRIPTION_STRING)));

        // same object -> returns true
        assertTrue(description.equals(description));

        // null -> returns false
        assertFalse(description.equals(null));

        // different types -> returns false
        assertFalse(description.equals(5.0f));

        // different values -> returns false
        assertFalse(description.equals(VALID_DESCRIPTION_2));
    }
}
