package it.mulders.mcs.clipboard;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

/**
 * Integration test for {@link LinuxClipboardService}.
 *
 * These tests assume a typical Linux desktop/server environment where
 * xclip, xsel or wl-copy may or may not be installed.
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@EnabledOnOs(OS.LINUX)
class LinuxClipboardServiceIT implements WithAssertions {
    private final LinuxClipboardService service = new LinuxClipboardService();

    @Test
    void copy_succeeds_when_clipboard_tool_is_available() {
        Assumptions.assumeTrue(
                service.isAvailable(), "No clipboard tool available (xclip/xsel/wl-copy) – skipping test");

        assertThat(service.copy("test clipboard content")).isTrue();
    }
}
