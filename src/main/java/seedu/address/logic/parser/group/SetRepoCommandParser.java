package seedu.address.logic.parser.group;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REPO;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.group.SetRepoCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.RepoLink;

/**
 * Parses input arguments and creates a new SetRepoCommand object
 */
public class SetRepoCommandParser implements Parser<SetRepoCommand> {

    @Override
    public SetRepoCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_REPO);

        Index groupIndex;
        try {
            groupIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetRepoCommand.MESSAGE_USAGE), pe);
        }
        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_REPO)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetRepoCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_REPO);

        RepoLink repoLink = ParserUtil.parseRepoLink(argMultimap.getValue(PREFIX_REPO).get());

        return new SetRepoCommand(groupIndex, repoLink);
    }
}
