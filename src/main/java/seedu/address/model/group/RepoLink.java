package seedu.address.model.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Group's GitHub repository link in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class RepoLink {

    public static final String MESSAGE_CONSTRAINTS = "Repository link must include a valid domain (e.g., github.com).\n"
            + "It can start with 'http://' or 'https://' "
            + "and may include a path to a repository (e.g., github.com/user123/projectrepo).";

    //url need to start with 'http://' or 'https://'
    //follow by a domain name example 'github.com'
    //follow by a path starting with '/' example '/user123/repo-name'
    //no space allow for ending.
    public static final String VALIDATION_REGEX =
            "^(?:https?://)?([A-Za-z0-9.-]+\\.[A-Za-z]{2,})(/[\\w.-]+)*/?$";

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
        if(!isStorageDefault) {
            checkArgument(isValidName(link), MESSAGE_CONSTRAINTS);
        }
        repolink = link;
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
     * Constructs a {@code RepoLink} with default values
     */
    public RepoLink() {
        repolink = "none";
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
