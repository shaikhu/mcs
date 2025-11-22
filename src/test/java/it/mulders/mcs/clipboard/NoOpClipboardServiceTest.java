package it.mulders.mcs.clipboard;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class NoOpClipboardServiceTest implements WithAssertions {
    private final NoOpClipboardService service = new NoOpClipboardService();

    @Test
    void is_available_always_returns_false() {
        assertThat(service.isAvailable()).isFalse();
    }

    @Test
    void copy_always_returns_false() {
        assertThat(service.copy("any text")).isFalse();
        assertThat(service.copy("")).isFalse();
        assertThat(service.copy(null)).isFalse();
    }
}
