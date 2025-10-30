package seedu.address.model.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Group's GitHub repository link in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class RepoLink {

    public static final String MESSAGE_CONSTRAINTS = "Repository link has three parts: \n"
            + "- Domain: Must start with https://github.com/ (case-sensitive)\n"
            + "- Username/Org: Starts with a letter/digit, can include letters, digits, '-', max 39 chars\n"
            + "- Repository name: Starts with a letter/digit, can include letters, digits, '_', '.', '-', "
            + "max 100 chars\n"
            + "Does not allow consecutive '_', '.', or '-'.\n"
            + "Does not allow Username/Org to end with '-'\n"
            + "Does not allow repository name to end with '_', '.', '-' or '/'\n"
            + "Example: https://github.com/user-name/repository-123.repo\n";

    //Must start exactly with 'https://github.com/' Case-sensitive
    //Follow by a Username/org name constraint:
    //- Must start with a letter or digit
    //- May contain letters, digits, or hyphens (-)
    //- Cannot exceed 39 characters
    //Must have one / between username and repository name.
    //Repository name constraints:
    //-Must start with a letter or digit
    //-Must have at least one character
    //- Can contain letters (upper/lower), digits, _, . or -
    //- No slash /

    public static final String VALIDATION_REGEX = "^https://github\\.com/"
            + "[A-Za-z0-9](?:[A-Za-z0-9]|-(?=[A-Za-z0-9])){0,38}/"
            + "[A-Za-z0-9](?:[A-Za-z0-9]|[_.-](?=[A-Za-z0-9])){0,99}$";

    public final String repolink;

    /**
     * Constructs a {@code RepoLink}.
     *
     * @param link A valid link.
     */
    public RepoLink(String link) {
        requireNonNull(link);
        checkArgument(isValidName(link), MESSAGE_CONSTRAINTS);
        repolink = link;
    }

    /**
     * Constructs a {@code RepoLink} that allow string value "none"
     * This Construct should only be call by fromStroage() to load RepoLink from stroage data
     *
     * @param link A valid link.
     */
    private RepoLink(String link, boolean isStorageDefault) {
        requireNonNull(link);
        if (!isStorageDefault) {
            checkArgument(isValidName(link), MESSAGE_CONSTRAINTS);
        }
        repolink = link;
    }

    /**
     * Constructs a {@code RepoLink} with default values
     */
    public RepoLink() {
        repolink = "none";
    }


    /**
     * Static method to create RepoLink from storage or external data.
     **/
    public static RepoLink fromStorage(String link) {
        requireNonNull(link);
        boolean isStorageDefault = link.equals("none");
        return new RepoLink(link, isStorageDefault);
    }

    /**
     * Returns true if a given string is a valid link.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public boolean isRepoSet() {
        return !repolink.equals("none");
    }

    @Override
    public String toString() {
        return repolink;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RepoLink)) {
            return false;
        }

        RepoLink otherRepo = (RepoLink) other;
        return repolink.equals(otherRepo.repolink);
    }

    @Override
    public int hashCode() {
        return repolink.hashCode();
    }

}
