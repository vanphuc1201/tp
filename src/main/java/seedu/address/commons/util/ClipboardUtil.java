package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

/**
 * A utility class that provides methods for interacting with the system clipboard.
 */
public class ClipboardUtil {

    /**
     * Copies the specified text to the system clipboard.
     *
     * @param text The text to copy. Must not be {@code null}.
     */
    public void copyToClipboard(String text) {
        requireNonNull(text);

        Toolkit.getDefaultToolkit()
                .getSystemClipboard()
                .setContents(new StringSelection(text), null);
    }
}
