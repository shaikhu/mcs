package it.mulders.mcs.clipboard;

import static it.mulders.mcs.clipboard.ClipboardCommandUtils.copyCommandExists;
import static it.mulders.mcs.clipboard.ClipboardCommandUtils.runCopyCommand;

public final class LinuxClipboardService implements ClipboardService {
    private String[] availableCommand = null;

    @Override
    public boolean isAvailable() {
        if (availableCommand != null) { // use cache to prevent rechecking
            return true;
        }

        if (copyCommandExists("which", "xclip")) {
            availableCommand = new String[] {"xclip", "-selection", "clipboard"};
            return true;
        } else if (copyCommandExists("which", "xsel")) {
            availableCommand = new String[] {"xsel", "--clipboard", "--input"};
            return true;
        } else if (copyCommandExists("which", "wl-copy")) { // for Wayland
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
        return runCopyCommand(text, availableCommand);
    }
}
