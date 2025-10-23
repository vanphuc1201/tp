package seedu.address.logic.parser.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REPO;
import static seedu.address.model.group.RepoLinkTest.INVALID_REPO_STRING;
import static seedu.address.model.group.RepoLinkTest.VALID_REPO;
import static seedu.address.model.group.RepoLinkTest.VALID_REPO_STRING;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_GROUP;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.group.SetRepoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.RepoLink;

public class SetRepoCommandParserTest {
    private final SetRepoCommandParser parser = new SetRepoCommandParser();
    private final String validStringIndex = "1";
    private final String inValidStringIndex = "x";
    private final SetRepoCommand validCommand = new SetRepoCommand(INDEX_FIRST_GROUP, VALID_REPO);
    private final String expectedError = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetRepoCommand.MESSAGE_USAGE);

    @Test
    public void parse_validInput_success() throws Exception {
        SetRepoCommand command = parser.parse(validStringIndex + " " + PREFIX_REPO + VALID_REPO_STRING);
        assertEquals(command, validCommand);
    }

    @Test
    public void trims_whiteSpace_fromIndex() throws Exception {
        SetRepoCommand command = parser.parse(" " + validStringIndex + " " + PREFIX_REPO + VALID_REPO_STRING);
        assertEquals(command, validCommand);
    }

    @Test
    public void trims_whiteSpace_fromRepoLink() throws Exception {
        SetRepoCommand command = parser.parse(validStringIndex + " " + PREFIX_REPO + VALID_REPO_STRING + " ");
        assertEquals(command, validCommand);
    }

    @Test
    public void parse_invalidIndex_throwsParseException() throws Exception {
        String input = inValidStringIndex + " " + PREFIX_REPO + VALID_REPO_STRING;
        assertThrows(ParseException.class, expectedError, () -> parser.parse(input));
    }

    @Test
    public void parse_invalidRepoLink_throwsParseException() throws Exception {
        String input = validStringIndex + " " + PREFIX_REPO + INVALID_REPO_STRING;
        assertThrows(ParseException.class, RepoLink.MESSAGE_CONSTRAINTS, () -> parser.parse(input));
    }

    @Test
    public void parse_missingIndex_throwsParseException() throws Exception {
        String input = PREFIX_REPO + VALID_REPO_STRING;
        assertThrows(ParseException.class, expectedError, () -> parser.parse(input));
    }

    @Test
    public void parse_missingRepoLink_throwsParseException() throws Exception {
        String input = validStringIndex + " " + PREFIX_REPO;
        assertThrows(ParseException.class, RepoLink.MESSAGE_CONSTRAINTS, () -> parser.parse(input));
    }

    @Test
    public void parse_missingRepoLinkPreFix_throwsParseException() throws Exception {
        String input = validStringIndex + " ";
        assertThrows(ParseException.class, expectedError, () -> parser.parse(input));
    }

    @Test
    public void parse_wrongPrefix_throwsParseException() throws Exception {
        String input = validStringIndex + " " + PREFIX_NAME + VALID_REPO_STRING;
        assertThrows(ParseException.class, expectedError, () -> parser.parse(input));
    }

    @Test
    public void parse_emptyInput_throwsParseException() {
        String input = "";
        assertThrows(ParseException.class, expectedError, () -> parser.parse(input));
    }

}
