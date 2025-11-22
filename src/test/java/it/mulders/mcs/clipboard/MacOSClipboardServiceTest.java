package it.mulders.mcs.clipboard;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

/**
 * OS-level tests for {@link MacOSClipboardService}.
 * <p>
 * Assumes the {@code pbcopy} command is available on macOS.
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@EnabledOnOs(OS.MAC)
class MacOSClipboardServiceTest implements WithAssertions {
    private final MacOSClipboardService service = new MacOSClipboardService();

    @Test
    void is_available_returns_true_when_pbcopy_is_present() {
        assertThat(service.isAvailable()).isTrue();
    }

    @Test
    void copy_succeeds_when_pbcopy_is_available() {
        assertThat(service.copy("test clipboard content")).isTrue();
    }
}
