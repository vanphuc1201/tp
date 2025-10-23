package seedu.address.logic.parser.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.group.EditGroupCommand;
import seedu.address.logic.commands.group.EditGroupCommand.EditGroupDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditGroupCommandParser implements Parser<EditGroupCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditGroupCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditGroupCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME);

        EditGroupDescriptor editGroupDescriptor = new EditGroupDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editGroupDescriptor.setGroupName(ParserUtil.parseGroupName(argMultimap.getValue(PREFIX_NAME).get()));
        }

        if (!editGroupDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditGroupCommand.MESSAGE_NOT_EDITED);
        }

        return new EditGroupCommand(index, editGroupDescriptor);
    }

}
