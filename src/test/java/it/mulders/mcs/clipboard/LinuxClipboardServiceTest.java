package it.mulders.mcs.clipboard;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

/**
 * OS-level tests for {@link LinuxClipboardService}.
 *
 * These tests assume a typical Linux desktop/server environment where
 * xclip, xsel or wl-copy may or may not be installed.
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@EnabledOnOs(OS.LINUX)
class LinuxClipboardServiceTest implements WithAssertions {
    private final LinuxClipboardService service = new LinuxClipboardService();

    @Test
    void copy_succeeds_when_clipboard_tool_is_available() {
        Assumptions.assumeTrue(
                service.isAvailable(), "No clipboard tool available (xclip/xsel/wl-copy) – skipping test");

        assertThat(service.copy("test clipboard content")).isTrue();
    }

    @Test
    void copy_fails_when_no_clipboard_tool_is_available() {
        Assumptions.assumeFalse(service.isAvailable(), "Clipboard tool is available – skipping no-tool test");

        assertThat(service.copy("test content")).isFalse();
    }
}
