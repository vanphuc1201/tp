package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Description;
import seedu.address.model.group.GroupName;
import seedu.address.model.group.RepoLink;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_GROUP_INDEX = "Group index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_CONTACT_INDEX = "Contact index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        return parseIndexWithMessage(oneBasedIndex, MESSAGE_INVALID_INDEX);
    }

    /**
     * Parses the given {@code oneBasedIndex} string into an {@code Index} object, using a custom error message.
     * <p>
     * Leading and trailing whitespaces are trimmed before parsing.
     *
     * @param oneBasedIndex the string representing a one-based index
     * @param errorMessage the error message to include in the {@link ParseException} if parsing fails
     * @return an {@code Index} object corresponding to the parsed value
     * @throws ParseException if the input is not a valid non-zero unsigned integer
     */
    private static Index parseIndexWithMessage(String oneBasedIndex, String errorMessage) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(errorMessage);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses {@code oneBasedIndexes} into an {@code Set<Index>} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    private static Set<Index> parseIndexesWithMessage(Collection<String> oneBasedIndexes, String errorMessage)
            throws ParseException {
        requireNonNull(oneBasedIndexes);
        // keeps uniqueness
        Set<Index> indexSet = new HashSet<>();
        for (String indexString : oneBasedIndexes) {
            // duplicates automatically ignored
            indexSet.add(parseIndexWithMessage(indexString, errorMessage));
        }
        return indexSet;
    }

    /**
     * Parses a collection of one-based group index strings into a set of {@code Index} objects for groups.
     *
     * @param oneBasedIndexes the collection of strings representing one-based indexes
     * @return a set of {@code Index} objects corresponding to the parsed values
     * @throws ParseException if any of the indexes are invalid
     */
    public static Set<Index> parseGroupIndexes(Collection<String> oneBasedIndexes) throws ParseException {
        requireNonNull(oneBasedIndexes);
        return parseIndexesWithMessage(oneBasedIndexes, MESSAGE_INVALID_GROUP_INDEX);
    }

    /**
     * Parses a single one-based group index string into an {@code Index} object for a group.
     *
     * @param oneBasedIndex the string representing a one-based index
     * @return an {@code Index} object corresponding to the parsed value
     * @throws ParseException if the index is invalid
     */
    public static Index parseGroupIndex(String oneBasedIndex) throws ParseException {
        requireNonNull(oneBasedIndex);
        return parseIndexWithMessage(oneBasedIndex, MESSAGE_INVALID_GROUP_INDEX);
    }

    /**
     * Parses a collection of one-based contact index strings into a set of {@code Index} objects for contacts.
     *
     * @param oneBasedIndexes the collection of strings representing one-based indexes
     * @return a set of {@code Index} objects corresponding to the parsed values
     * @throws ParseException if any of the indexes are invalid
     */
    public static Set<Index> parseContactIndexes(Collection<String> oneBasedIndexes) throws ParseException {
        requireNonNull(oneBasedIndexes);
        return parseIndexesWithMessage(oneBasedIndexes, MESSAGE_INVALID_CONTACT_INDEX);
    }


    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        return parseField(name, Name::new);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        return parseField(phone, Phone::new);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        return parseField(email, Email::new);
    }

    /**
     * Parses a {@code String name} into a {@code GroupName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static GroupName parseGroupName(String name) throws ParseException {
        return parseField(name, GroupName::new);
    }

    /**
     * Parses a {@code String description} into a {@code Description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static Description parseDescription(String description) throws ParseException {
        return parseField(description, Description::new);
    }

    /**
     * Parses a {@code String repoLink} into a {@code RepoLink}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static RepoLink parseRepoLink(String repoLink) throws ParseException {
        return parseField(repoLink, RepoLink::new);
    }

    /**
     * Generic method for parsing validated field types.
     * @param input The String to be parsed.
     * @param constructor Constructor for T, which checks for valid input and throws {@code IllegalArgumentException.}
     */
    private static <T> T parseField(String input, Function<String, T> constructor) throws ParseException {
        requireNonNull(input);
        String trimmedInput = input.trim();
        try {
            return constructor.apply(trimmedInput);
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
