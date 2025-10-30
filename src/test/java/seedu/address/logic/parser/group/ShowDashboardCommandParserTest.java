package seedu.address.logic.parser.group;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.group.DeleteGroupCommand;
import seedu.address.logic.commands.group.ShowDashboardCommand;

public class ShowDashboardCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowDashboardCommand.MESSAGE_USAGE);

    private ShowDashboardCommandParser parser = new ShowDashboardCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteGroupCommand() {
        assertParseSuccess(parser, "1", new ShowDashboardCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5", MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0", MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ShowDashboardCommand.MESSAGE_USAGE));
    }
}
