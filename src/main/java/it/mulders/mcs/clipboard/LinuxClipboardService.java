package it.mulders.mcs.clipboard;

import static it.mulders.mcs.clipboard.CommandUtils.copyCommandExists;
import static it.mulders.mcs.clipboard.CommandUtils.runCopyCommand;

/**
 * Linux clipboard service supporting multiple clipboard utilities.
 * Tries xclip, xsel, and wl-copy (for Wayland) in that order.
 */
public final class LinuxClipboardService implements ClipboardService {
    private String[] availableCommand = null;

    @Override
    public boolean isAvailable() {
        if (availableCommand != null) {
            return true;
        }

        if (copyCommandExists("which", "xclip")) {
            availableCommand = new String[] {"xclip", "-selection", "clipboard"};
            return true;
        } else if (copyCommandExists("which", "xsel")) {
            availableCommand = new String[] {"xsel", "--clipboard", "--input"};
            return true;
        } else if (copyCommandExists("which", "wl-copy")) {
            availableCommand = new String[] {"wl-copy"};
            return true;
        }

        return false;
    }

    @Override
    public boolean copy(String text) {
        if (!isAvailable()) {
            return false;
        }
        return runCopyCommand(availableCommand, text);
    }
}
