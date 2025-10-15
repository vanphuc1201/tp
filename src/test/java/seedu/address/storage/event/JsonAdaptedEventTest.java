package seedu.address.storage.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.model.event.DescriptionTest.VALID_DESCRIPTION_STRING;
import static seedu.address.model.event.EventTest.VALID_EVENT;
import static seedu.address.storage.event.JsonAdaptedEvent.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Description;

public class JsonAdaptedEventTest {
    private static final String INVALID_DESCRIPTION = " ";

    @Test
    public void toModelType_validDescription_returnsEvent() throws Exception {
        JsonAdaptedEvent jsonEvent = new JsonAdaptedEvent(VALID_DESCRIPTION_STRING);
        assertEquals(VALID_DESCRIPTION_STRING, jsonEvent.toModelType().description().toString());
    }

    @Test
    public void toModelType_fromValidEvent_returnsEvent() throws Exception {
        JsonAdaptedEvent jsonEvent = new JsonAdaptedEvent(VALID_EVENT);
        assertEquals(VALID_EVENT, jsonEvent.toModelType());
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedEvent jsonEvent = new JsonAdaptedEvent((String) null);
        String expectedError = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedError, jsonEvent::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedEvent jsonEvent = new JsonAdaptedEvent(INVALID_DESCRIPTION);
        assertThrows(IllegalValueException.class, Description.MESSAGE_CONSTRAINTS, jsonEvent::toModelType);
    }
}
