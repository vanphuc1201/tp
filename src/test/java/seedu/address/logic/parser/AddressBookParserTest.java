package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.event.DeleteEventCommandParserTest.VALID_DELETE_EVENT_COMMAND_ARGS;
import static seedu.address.logic.parser.event.EditEventCommandParserTest.VALID_EDIT_EVENT_COMMAND_ARGS;
import static seedu.address.model.event.DescriptionTest.VALID_DESCRIPTION_STRING;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_GROUP;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.event.AddEventCommand;
import seedu.address.logic.commands.event.DeleteEventCommand;
import seedu.address.logic.commands.group.AddGroupCommand;
import seedu.address.logic.commands.group.DeleteGroupCommand;
import seedu.address.logic.commands.event.EditEventCommand;
import seedu.address.logic.commands.group.EditGroupCommand;
import seedu.address.logic.commands.group.EditGroupCommand.EditGroupDescriptor;
import seedu.address.logic.commands.group.FindGroupCommand;
import seedu.address.logic.commands.group.ListGroupCommand;
import seedu.address.logic.commands.person.AddCommand;
import seedu.address.logic.commands.person.DeleteCommand;
import seedu.address.logic.commands.person.EditCommand;
import seedu.address.logic.commands.person.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.person.FindCommand;
import seedu.address.logic.commands.person.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupNameContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditGroupDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.GroupBuilder;
import seedu.address.testutil.GroupUtil;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person, new HashSet<>()), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + String.join(" ", keywords));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_addGroup() throws Exception {
        Group group = new GroupBuilder().build();
        AddGroupCommand command = (AddGroupCommand) parser.parseCommand(GroupUtil.getAddGroupCommand(group));
        assertEquals(new AddGroupCommand(group), command);
    }

    @Test
    public void parseCommand_deleteGroup() throws Exception {
        DeleteGroupCommand command = (DeleteGroupCommand) parser.parseCommand(
                DeleteGroupCommand.COMMAND_WORD + " " + INDEX_FIRST_GROUP.getOneBased());
        assertEquals(new DeleteGroupCommand(INDEX_FIRST_GROUP), command);
    }

    @Test
    public void parseCommand_editGroup() throws Exception {
        Group group = new GroupBuilder().build();
        EditGroupDescriptor descriptor = new EditGroupDescriptorBuilder(group).build();
        EditGroupCommand command = (EditGroupCommand) parser.parseCommand(EditGroupCommand.COMMAND_WORD + " "
                + INDEX_FIRST_GROUP.getOneBased() + " " + GroupUtil.getEditGroupDescriptorDetails(descriptor));
        assertEquals(new EditGroupCommand(INDEX_FIRST_GROUP, descriptor), command);
    }

    @Test
    public void parseCommand_listGroup() throws Exception {
        assertTrue(parser.parseCommand(ListGroupCommand.COMMAND_WORD) instanceof ListGroupCommand);
        assertTrue(parser.parseCommand(ListGroupCommand.COMMAND_WORD + " 3") instanceof ListGroupCommand);
    }

    @Test
    public void parseCommand_findGroup() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindGroupCommand command = (FindGroupCommand) parser.parseCommand(
                FindGroupCommand.COMMAND_WORD + " " + String.join(" ", keywords));
        assertEquals(new FindGroupCommand(new GroupNameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_addEvent() throws Exception {
        final String validInput = AddEventCommand.COMMAND_WORD + " 1 " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_STRING;
        assertTrue(parser.parseCommand(validInput) instanceof AddEventCommand);
    }

    @Test
    public void parseCommand_editEvent() throws Exception {
        final String validInput = EditEventCommand.COMMAND_WORD + " " + VALID_EDIT_EVENT_COMMAND_ARGS;
        assertTrue(parser.parseCommand(validInput) instanceof EditEventCommand);
    }

    @Test
    public void parseCommand_deleteEvent() throws Exception {
        final String validInput = DeleteEventCommand.COMMAND_WORD + " " + VALID_DELETE_EVENT_COMMAND_ARGS;
        assertTrue(parser.parseCommand(validInput) instanceof DeleteEventCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
                -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
