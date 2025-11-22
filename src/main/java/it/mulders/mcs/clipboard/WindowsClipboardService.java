package it.mulders.mcs.clipboard;

import static it.mulders.mcs.clipboard.CommandUtils.copyCommandExists;
import static it.mulders.mcs.clipboard.CommandUtils.runCopyCommand;

public final class WindowsClipboardService implements ClipboardService {
    private static final String CLIP_COMMAND = "clip";

    @Override
    public boolean isAvailable() {
        return copyCommandExists("where", CLIP_COMMAND);
    }

    @Override
    public boolean copy(String text) {
        return runCopyCommand(new String[] {CLIP_COMMAND}, text);
    }
}
