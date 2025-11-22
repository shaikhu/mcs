package it.mulders.mcs.clipboard;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

/**
 * Integration test for {@link MacOSClipboardService}.
 * <p>
 * Assumes the {@code pbcopy} command is available on macOS.
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@EnabledOnOs(OS.MAC)
class MacOSClipboardServiceIT implements WithAssertions {
    private final MacOSClipboardService service = new MacOSClipboardService();

    @Test
    void copy_succeeds_when_pbcopy_is_available() {
        Assumptions.assumeTrue(service.isAvailable(), "Clipboard tool pbcopy not available  – skipping test");
        assertThat(service.copy("test clipboard content")).isTrue();
    }
}
