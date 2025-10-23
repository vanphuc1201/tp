package seedu.address.logic.parser.group;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.GROUP_NAME_DESC_CS2101_CA1;
import static seedu.address.logic.commands.CommandTestUtil.GROUP_NAME_DESC_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GROUP_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_NAME_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_GROUP;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_GROUP;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.group.EditGroupCommand;
import seedu.address.logic.commands.group.EditGroupCommand.EditGroupDescriptor;
import seedu.address.model.group.GroupName;
import seedu.address.testutil.EditGroupDescriptorBuilder;

public class EditGroupCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditGroupCommand.MESSAGE_USAGE);

    private EditGroupCommandParser parser = new EditGroupCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, VALID_GROUP_INDEX, EditGroupCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + GROUP_NAME_DESC_CS2103T, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + GROUP_NAME_DESC_CS2103T, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_GROUP_NAME_DESC, GroupName.MESSAGE_CONSTRAINTS); // invalid name
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_GROUP;
        String userInput = targetIndex.getOneBased() + GROUP_NAME_DESC_CS2103T;

        EditGroupDescriptor descriptor = new EditGroupDescriptorBuilder().withName(VALID_GROUP_NAME_CS2103T).build();
        EditGroupCommand expectedCommand = new EditGroupCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTagValue_failure()

        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_GROUP;
        String userInput = targetIndex.getOneBased() + INVALID_GROUP_NAME_DESC + GROUP_NAME_DESC_CS2103T;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid followed by valid
        userInput = targetIndex.getOneBased() + GROUP_NAME_DESC_CS2103T + INVALID_GROUP_NAME_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // mulltiple valid fields repeated
        userInput = targetIndex.getOneBased() + GROUP_NAME_DESC_CS2103T + GROUP_NAME_DESC_CS2101_CA1;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple invalid values
        userInput = targetIndex.getOneBased() + INVALID_GROUP_NAME_DESC + INVALID_GROUP_NAME_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));
    }
}
