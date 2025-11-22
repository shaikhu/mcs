package it.mulders.mcs.clipboard;

public class ClipboardServiceFactory {
    private ClipboardServiceFactory() {
        throw new AssertionError("Instantiation not allowed");
    }

    public static ClipboardService getClipboardService() {
        return getClipboardService(System.getProperty("os.name"));
    }

    // Visible for testing
    static ClipboardService getClipboardService(String osName) {
        osName = osName.toLowerCase();
        if (osName.contains("mac") || osName.contains("darwin")) {
            return new MacOSClipboardService();
        } else if (osName.contains("win")) {
            return new WindowsClipboardService();
        } else if (osName.contains("nux") || osName.contains("nix") || osName.contains("aix")) {
            return new LinuxClipboardService();
        } else {
            return new NoOpClipboardService();
        }
    }
}
