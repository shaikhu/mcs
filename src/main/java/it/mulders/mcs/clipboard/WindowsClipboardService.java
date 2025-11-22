package it.mulders.mcs.clipboard;

import static it.mulders.mcs.clipboard.ClipboardCommandUtils.copyCommandExists;
import static it.mulders.mcs.clipboard.ClipboardCommandUtils.runCopyCommand;

public final class WindowsClipboardService implements ClipboardService {
    private static final String CLIP_COMMAND = "clip";

    @Override
    public boolean isAvailable() {
        return copyCommandExists("where", CLIP_COMMAND);
    }

    @Override
    public boolean copy(String text) {
        if (!isAvailable()) {
            return false;
        }
        return runCopyCommand(text, CLIP_COMMAND);
    }
}
