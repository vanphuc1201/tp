package seedu.address.logic.parser.group;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_GROUP;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.group.AddMemberCommand;


public class AddMemberCommandParserTest {
    private AddMemberCommandParser parser = new AddMemberCommandParser();

    @Test
    public void parse_validArgs_returnsAddMemberCommand() {
        assertParseSuccess(parser, " " + PREFIX_GROUP_INDEX + "3 " + PREFIX_CONTACT_INDEX + "1",
                new AddMemberCommand(INDEX_THIRD_GROUP, INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "die",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMemberCommand.MESSAGE_USAGE));

        assertParseFailure(parser, " " + PREFIX_GROUP_INDEX + "3",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMemberCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicatePrefixes_throwsParseException() {
        assertParseFailure(parser,
                " " + PREFIX_GROUP_INDEX + "3 " + PREFIX_GROUP_INDEX + "3 " + PREFIX_CONTACT_INDEX + "1",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_GROUP_INDEX));

    }
}
