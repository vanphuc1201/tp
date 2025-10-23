package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");

    // Contacts
    public static final Prefix PREFIX_CONTACT_INDEX = new Prefix("c/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");

    // Groups
    public static final Prefix PREFIX_GROUP_INDEX = new Prefix("g/");

    // Events
    // No commands (right now) use email and event index together.
    public static final Prefix PREFIX_EVENT_INDEX = new Prefix("e/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("d/");

    // repo
    public static final Prefix PREFIX_REPO = new Prefix("r/");

}
