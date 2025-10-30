package seedu.address.model.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RepoLinkTest {

    public static final RepoLink DEFAULT_REPO = new RepoLink();
    public static final RepoLink VALID_REPO = new RepoLink("https://github.com/user-name/repository-123.repo");
    public static final RepoLink VALID_DIFFERENT_REPO = new RepoLink("https://github.com/user-name/repository-x.repo");

    public static final String INVALID_REPO_STRING = "github.com";
    public static final String DEFAULT_REPO_STRING = "none";
    public static final String VALID_REPO_STRING = "https://github.com/user-name/repository-123.repo";


    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RepoLink(null));
    }

    @Test
    public void constructor_invalidLink_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new RepoLink(INVALID_REPO_STRING));
    }

    @Test
    public void constructor_validLink_success() {
        assertEquals(VALID_REPO_STRING, VALID_REPO.toString());
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> RepoLink.isValidName(null));

        // invalid name
        assertFalse(RepoLink.isValidName("")); // empty string
        assertFalse(RepoLink.isValidName(" ")); // spaces only
        assertFalse(RepoLink.isValidName("https://git^hub.com/user/repo")); //special characters in domain
        assertFalse(RepoLink.isValidName("https://github.com/user^/repo")); //special characters in username
        assertFalse(RepoLink.isValidName("https://github.com/user/re^po")); //special characters in repository
        assertFalse(RepoLink.isValidName("https://github.com/user/-")); //only - in repository
        assertFalse(RepoLink.isValidName("https://github.com/user/")); //empty repository
        assertFalse(RepoLink.isValidName("https://github.com/u_ser/repo")); //_ in username
        assertFalse(RepoLink.isValidName("https://github.com/u--ser/repo")); //consecutive - in username
        assertFalse(RepoLink.isValidName("https://github.com/u.ser/repo")); //. in username
        assertFalse(RepoLink.isValidName("https://github.com/user-/repo")); //username end with -
        assertFalse(RepoLink.isValidName("https://github.com/-user/repo")); //username start with -
        assertFalse(RepoLink.isValidName("https://github.com/user/r__epo")); //consecutive _ in repository
        assertFalse(RepoLink.isValidName("https://github.com/user/r--epo")); //consecutive - in repository
        assertFalse(RepoLink.isValidName("https://github.com/user/r..epo")); //consecutive . in repository
        assertFalse(RepoLink.isValidName("https://github.com/user/repo.")); //repository end with .
        assertFalse(RepoLink.isValidName("https://github.com/user/.repo")); //repository start with .
        assertFalse(RepoLink.isValidName("https://github.com/user/repo-")); //repository end with -
        assertFalse(RepoLink.isValidName("https://github.com/user/-repo")); //repository start with -
        assertFalse(RepoLink.isValidName("https://github.com/user/repo_")); //repository end with _
        assertFalse(RepoLink.isValidName("https://github.com/user/_repo")); //repository start with _
        assertFalse(RepoLink.isValidName("https://github.com/user/repo/")); //repository end with /
        assertFalse(RepoLink.isValidName("https://github.com/user/repo/filepath")); //extra path
        assertFalse(RepoLink.isValidName("https://github.com/" + "u".repeat(40) + "/r")); // 40 char username
        assertFalse(RepoLink.isValidName("https://github.com/u/" + "r".repeat(101))); // 101 char repository

        // valid name
        assertTrue(RepoLink.isValidName("https://github.com/u-ser/repo")); // - in username
        assertTrue(RepoLink.isValidName("https://github.com/user/repo-ro")); // - in repository
        assertTrue(RepoLink.isValidName("https://github.com/user/repo.ro")); // . in repository
        assertTrue(RepoLink.isValidName("https://github.com/user/repo_ro")); // _ in repository
        assertTrue(RepoLink.isValidName("https://github.com/" + "u".repeat(39) + "/r")); // 39 char username
        assertTrue(RepoLink.isValidName("https://github.com/u/" + "r".repeat(100))); // 100 char repository
    }

    @Test
    public void fromStorage() {
        RepoLink emptyRepoFromStorage = RepoLink.fromStorage(DEFAULT_REPO_STRING);
        RepoLink validRepoFromStorage = RepoLink.fromStorage(VALID_REPO_STRING);

        assertEquals(VALID_REPO_STRING, validRepoFromStorage.toString());
        assertEquals(DEFAULT_REPO_STRING, emptyRepoFromStorage.toString());
    }

    @Test
    public void isRepoSet() {
        assertTrue(VALID_REPO.isRepoSet());

        RepoLink emptyRepoFromStorage = RepoLink.fromStorage(DEFAULT_REPO_STRING);

        assertFalse(DEFAULT_REPO.isRepoSet());
        assertFalse(emptyRepoFromStorage.isRepoSet());

    }

    @Test
    public void equals() {

        // same values -> returns true
        assertTrue(VALID_REPO.equals(new RepoLink(VALID_REPO_STRING)));

        // same object -> returns true
        assertTrue(VALID_REPO.equals(VALID_REPO));

        // null -> returns false
        assertFalse(VALID_REPO.equals(null));

        // different types -> returns false
        assertFalse(VALID_REPO.equals(5.0f));

        // different values -> returns false
        assertFalse(VALID_REPO.equals(new RepoLink("https://github.com/user/repo_ro")));
    }
}
