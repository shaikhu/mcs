package it.mulders.mcs.clipboard;

import static it.mulders.mcs.clipboard.CommandUtils.copyCommandExists;
import static it.mulders.mcs.clipboard.CommandUtils.runCopyCommand;

public final class MacOSClipboardService implements ClipboardService {
    private static final String PBCOPY_COMMAND = "pbcopy";

    @Override
    public boolean isAvailable() {
        return copyCommandExists("which", PBCOPY_COMMAND);
    }

    @Override
    public boolean copy(String text) {
        return runCopyCommand(new String[] {PBCOPY_COMMAND}, text);
    }
}
