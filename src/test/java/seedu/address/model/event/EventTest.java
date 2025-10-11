package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.event.DescriptionTest.VALID_DESCRIPTION;
import static seedu.address.model.event.DescriptionTest.VALID_DESCRIPTION_2;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class EventTest {

    private static final Event VALID_EVENT = new Event(VALID_DESCRIPTION);

    @Test
    void constructor_validDescription_shouldCreateEvent() {
        assertNotNull(VALID_EVENT, "VALID_EVENT should not be null");
        assertEquals(VALID_DESCRIPTION, VALID_EVENT.description());
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Event(null));
    }

    @Test
    void toStringMethod() {
        assertEquals(VALID_EVENT.toString(), VALID_DESCRIPTION.toString());
    }

    @Test
    void equals() {
        // same values -> returns true
        assertTrue(VALID_EVENT.equals(new Event(VALID_DESCRIPTION)));

        // same object -> returns true
        assertTrue(VALID_EVENT.equals(VALID_EVENT));

        // null -> returns false
        assertFalse(VALID_EVENT.equals(null));

        // different types -> returns false
        assertFalse(VALID_EVENT.equals(5.0f));

        // different values -> returns false
        assertFalse(VALID_EVENT.equals(new Event(VALID_DESCRIPTION_2)));
    }
}
