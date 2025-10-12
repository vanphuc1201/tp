package seedu.address.logic.parser.groups;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_INDEX;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.groups.AddMemberCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddMemberCommand object
 */
public class AddMemberCommandParser implements Parser<AddMemberCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddMemberCommand
     * and returns an AddMemberCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddMemberCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_GROUP_INDEX, PREFIX_CONTACT_INDEX);

        Index personIndex;
        Index groupIndex;

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_GROUP_INDEX, PREFIX_CONTACT_INDEX)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMemberCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_GROUP_INDEX, PREFIX_CONTACT_INDEX);
        groupIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_GROUP_INDEX).get());
        personIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_CONTACT_INDEX).get());

        return new AddMemberCommand(groupIndex, personIndex);
    }

}
