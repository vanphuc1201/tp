package seedu.address.logic.parser.group;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.group.DeleteGroupCommand;
import seedu.address.logic.commands.group.ShowDashboardCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new ShowDashboardCommand object
 */
public class ShowDashboardCommandParser implements Parser<ShowDashboardCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ShowDashboardCommand
     * and returns a ShowDashboardCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ShowDashboardCommand parse(String userInput) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(userInput);
            return new ShowDashboardCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowDashboardCommand.MESSAGE_USAGE), pe);
        }
    }
}
