package seedu.address.model.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RepoLinkTest {

    public static final RepoLink DEFAULT_REPO = new RepoLink();
    public static final RepoLink VALID_REPO = new RepoLink("abc.com");
    public static final RepoLink VALID_DIFFERENT_REPO = new RepoLink("abcX.com");

    public static final String INVALID_REPO_STRING = "ab";
    public static final String DEFAULT_REPO_STRING = "none";
    public static final String VALID_REPO_STRING = "abc.com";


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
        assertFalse(RepoLink.isValidName("^abc.com")); // only non-alphanumeric characters
        assertFalse(RepoLink.isValidName("ABC.com*")); // contains non-alphanumeric characters
        assertFalse(RepoLink.isValidName("abc.com//user123")); // with invalid path

        // valid name
        assertTrue(RepoLink.isValidName("abc.com")); // domain only
        assertTrue(RepoLink.isValidName("abc.com/user123/repo")); // with path
        assertTrue(RepoLink.isValidName("https://abc.com/user123/repo")); // with https://
        assertTrue(RepoLink.isValidName("http://abc.com/user123/repo")); // with http://
        assertTrue(RepoLink.isValidName("http://abc.com/user123/repo/")); // end with /
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
        assertFalse(VALID_REPO.equals(new RepoLink("xxx.com")));
    }
}
