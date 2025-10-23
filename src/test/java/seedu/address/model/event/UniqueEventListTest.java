package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.event.EventTest.VALID_EVENT;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;

public class UniqueEventListTest {

    private final UniqueEventList uniqueEventList = new UniqueEventList();

    @Test
    public void contains_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.contains(null));
    }

    @Test
    public void contains_eventNotInList_returnsFalse() {
        assertFalse(uniqueEventList.contains(VALID_EVENT));
    }

    @Test
    public void contains_eventInList_returnsTrue() {
        uniqueEventList.add(VALID_EVENT);
        assertTrue(uniqueEventList.contains(VALID_EVENT));
    }

    @Test
    public void add_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.add(null));
    }

    @Test
    public void add_duplicateEvent_throwsDuplicateEventException() {
        uniqueEventList.add(VALID_EVENT);
        assertThrows(DuplicateEventException.class, () -> uniqueEventList.add(VALID_EVENT));
    }

    @Test
    public void remove_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.remove(null));
    }

    @Test
    public void remove_eventNotInList_throwsEventNotFoundException() {
        assertThrows(EventNotFoundException.class, () -> uniqueEventList.remove(VALID_EVENT));
    }

    @Test
    public void remove_eventInList_removesEvent() {
        uniqueEventList.add(VALID_EVENT);
        uniqueEventList.remove(VALID_EVENT);
        UniqueEventList expected = new UniqueEventList();
        assertEquals(expected, uniqueEventList);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueEventList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueEventList.asUnmodifiableObservableList().toString(), uniqueEventList.toString());
    }
}
