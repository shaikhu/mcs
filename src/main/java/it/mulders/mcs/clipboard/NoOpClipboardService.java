package it.mulders.mcs.clipboard;

public final class NoOpClipboardService implements ClipboardService {
    @Override
    public boolean isAvailable() {
        return false;
    }

    @Override
    public boolean copy(String text) {
        return false;
    }
}
