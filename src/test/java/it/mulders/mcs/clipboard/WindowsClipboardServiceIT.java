package it.mulders.mcs.clipboard;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

/**
 * Integration test for {@link WindowsClipboardService}.
 * <p>
 * Assumes the {@code clip} command is available on Windows.
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@EnabledOnOs(OS.WINDOWS)
class WindowsClipboardServiceIT implements WithAssertions {
    private final WindowsClipboardService service = new WindowsClipboardService();

    @Test
    void copy_succeeds_when_clip_is_available() {
        Assumptions.assumeTrue(service.isAvailable(), "Clipboard tool clip not available  – skipping test");
        assertThat(service.copy("test clipboard content")).isTrue();
    }
}
