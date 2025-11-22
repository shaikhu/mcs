package it.mulders.mcs.clipboard;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

/**
 * Small OS-level tests for {@link ClipboardCommandUtils}.
 * <p>
 * These tests assume standard tools are available on the PATH:
 * <ul>
 *     <li>Unix: {@code which}, {@code echo}, {@code cat}</li>
 *     <li>Windows: {@code where}, {@code cmd}, {@code findstr}</li>
 * </ul>
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ClipboardCommandUtilsIT implements WithAssertions {

    private static final String NON_EXISTENT_COMMAND = "mcs-nonexistent-command-xyz";

    @Nested
    class CopyCommandExists {

        @Test
        @EnabledOnOs({OS.LINUX, OS.MAC})
        void returns_true_for_available_command_on_unix() {
            assertThat(ClipboardCommandUtils.copyCommandExists("which", "echo")).isTrue();
        }

        @Test
        @EnabledOnOs({OS.LINUX, OS.MAC})
        void returns_false_for_unavailable_command_on_unix() {
            assertThat(ClipboardCommandUtils.copyCommandExists("which", NON_EXISTENT_COMMAND))
                    .isFalse();
        }

        @Test
        @EnabledOnOs(OS.WINDOWS)
        void returns_true_for_available_command_on_windows() {
            assertThat(ClipboardCommandUtils.copyCommandExists("where", "cmd")).isTrue();
        }

        @Test
        @EnabledOnOs(OS.WINDOWS)
        void returns_false_for_unavailable_command_on_windows() {
            assertThat(ClipboardCommandUtils.copyCommandExists("where", NON_EXISTENT_COMMAND))
                    .isFalse();
        }
    }

    @Nested
    class RunCopyCommand {

        @Test
        @EnabledOnOs({OS.LINUX, OS.MAC})
        void succeeds_with_valid_unix_command() {
            boolean result = ClipboardCommandUtils.runCopyCommand(new String[] {"cat"}, "test content");
            assertThat(result).isTrue();
        }

        @Test
        @EnabledOnOs(OS.WINDOWS)
        void succeeds_with_valid_windows_command() {
            boolean result = ClipboardCommandUtils.runCopyCommand(new String[] {"findstr", "."}, "test content");
            assertThat(result).isTrue();
        }

        @Test
        void returns_false_for_invalid_executable() {
            boolean result = ClipboardCommandUtils.runCopyCommand(new String[] {NON_EXISTENT_COMMAND}, "test content");
            assertThat(result).isFalse();
        }

        @Test
        void run_copy_command_returns_false_for_null_text() {
            assertThat(ClipboardCommandUtils.runCopyCommand(new String[] {"cat"}, null))
                    .isFalse();
        }

        @Test
        void run_copy_command_returns_false_for_empty_command() {
            assertThat(ClipboardCommandUtils.runCopyCommand(new String[] {}, "text"))
                    .isFalse();
        }
    }
}
