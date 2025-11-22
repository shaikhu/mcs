package it.mulders.mcs.clipboard;

import static it.mulders.mcs.clipboard.ClipboardCommandUtils.copyCommandExists;
import static it.mulders.mcs.clipboard.ClipboardCommandUtils.runCopyCommand;

public final class MacOSClipboardService implements ClipboardService {
    private static final String PBCOPY_COMMAND = "pbcopy";

    @Override
    public boolean isAvailable() {
        return copyCommandExists("which", PBCOPY_COMMAND);
    }

    @Override
    public boolean copy(String text) {
        if (!isAvailable()) {
            return false;
        }
        return runCopyCommand(text, PBCOPY_COMMAND);
    }
}
