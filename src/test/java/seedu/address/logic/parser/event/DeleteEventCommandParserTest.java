package seedu.address.logic.parser.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.event.DeleteEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class DeleteEventCommandParserTest {
    public static final String VALID_DELETE_EVENT_COMMAND_ARGS = "1 " + PREFIX_EVENT_INDEX + "1";

    private final DeleteEventCommandParser parser = new DeleteEventCommandParser();
    private final String validIndex = "1";
    private final DeleteEventCommand validCommand = new DeleteEventCommand(Index.fromOneBased(1),
            Index.fromOneBased(1));
    private final String expectedError = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            DeleteEventCommand.MESSAGE_USAGE);

    @Test
    public void parse_validInput_givesCorrectCommand() throws Exception {
        DeleteEventCommand command = parser.parse(VALID_DELETE_EVENT_COMMAND_ARGS);
        assertEquals(command, validCommand);
    }

    @Test
    public void trims_whiteSpace_fromGroupIndex() throws Exception {
        DeleteEventCommand command = parser.parse(" " + validIndex + "  " + PREFIX_EVENT_INDEX + validIndex);
        assertEquals(command, validCommand);
    }

    @Test
    public void trims_whiteSpace_fromEventIndex() throws Exception {
        DeleteEventCommand command = parser.parse(validIndex + " " + PREFIX_EVENT_INDEX
                + " " + validIndex + " ");
        assertEquals(command, validCommand);
    }

    // We will leave correctly parsing the index to ParserUtil.
    @Test
    public void parse_missingGroupIndex_throwsParseException() {
        String input = PREFIX_EVENT_INDEX + validIndex;
        assertThrows(ParseException.class, expectedError, () -> parser.parse(input));
    }

    @Test
    public void parse_missingEventIndex_throwsParseException() {
        String input = validIndex;
        assertThrows(ParseException.class, expectedError, () -> parser.parse(input));
    }

    @Test
    public void parse_duplicateEventIndexPrefix_throwsParseException() {
        String input = validIndex + " "
                + PREFIX_EVENT_INDEX + validIndex + " "
                + PREFIX_EVENT_INDEX + validIndex;
        assertThrows(ParseException.class,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EVENT_INDEX), () -> parser.parse(input));
    }

    @Test
    public void parse_wrongPrefix_throwsParseException() {
        String input = validIndex + " " + PREFIX_NAME + validIndex;
        assertThrows(ParseException.class, expectedError, () -> parser.parse(input));
    }

    @Test
    public void parse_emptyInput_throwsParseException() {
        String input = "";
        assertThrows(ParseException.class, expectedError, () -> parser.parse(input));
    }
}
