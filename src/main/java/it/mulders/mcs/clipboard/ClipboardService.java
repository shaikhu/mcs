package it.mulders.mcs.clipboard;

/**
 * Service for copying text to the system clipboard.
 * Implementations use OS-specific clipboard utilities.
 */
public interface ClipboardService {
    /**
     * Check if the clipboard service is available on this system.
     * @return true if clipboard operations are supported, false otherwise
     */
    boolean isAvailable();

    /**
     * Copy text to the system clipboard.
     * @param text the text to copy
     * @return true if the operation succeeded, false otherwise
     */
    boolean copy(String text);
}
