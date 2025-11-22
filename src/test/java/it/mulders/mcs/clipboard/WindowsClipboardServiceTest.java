package it.mulders.mcs.clipboard;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

/**
 * Small OS-level tests for {@link WindowsClipboardService}.
 * <p>
 * Assumes the {@code clip} command is available on Windows.
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@EnabledOnOs(OS.WINDOWS)
class WindowsClipboardServiceTest implements WithAssertions {
    private final WindowsClipboardService service = new WindowsClipboardService();

    @Test
    void is_available_returns_true_when_clip_is_present() {
        assertThat(service.isAvailable()).isTrue();
    }

    @Test
    void copy_succeeds_when_clip_is_available() {
        assertThat(service.copy("test clipboard content")).isTrue();
    }
}
