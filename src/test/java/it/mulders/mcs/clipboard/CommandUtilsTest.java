package it.mulders.mcs.clipboard;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

/**
 * Small OS-level tests for {@link CommandUtils}.
 * <p>
 * These tests assume standard tools are available on the PATH:
 * <ul>
 *     <li>Unix: {@code which}, {@code echo}, {@code cat}</li>
 *     <li>Windows: {@code where}, {@code cmd}, {@code findstr}</li>
 * </ul>
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CommandUtilsTest implements WithAssertions {

    private static final String NON_EXISTENT_COMMAND = "mcs-nonexistent-command-xyz";

    @Nested
    @EnabledOnOs({OS.LINUX, OS.MAC})
    class Unix_copyCommandExists {

        @Test
        void returns_true_for_available_command() {
            assertThat(CommandUtils.copyCommandExists("which", "echo")).isTrue();
        }

        @Test
        void returns_false_for_unavailable_command() {
            assertThat(CommandUtils.copyCommandExists("which", NON_EXISTENT_COMMAND))
                    .isFalse();
        }
    }

    @Nested
    @EnabledOnOs(OS.WINDOWS)
    class Windows_copyCommandExists {

        @Test
        void returns_true_for_available_command() {
            assertThat(CommandUtils.copyCommandExists("where", "cmd")).isTrue();
        }

        @Test
        void returns_false_for_unavailable_command() {
            assertThat(CommandUtils.copyCommandExists("where", NON_EXISTENT_COMMAND))
                    .isFalse();
        }
    }

    @Nested
    @EnabledOnOs({OS.LINUX, OS.MAC})
    class Unix_runCopyCommand {
        @Test
        void succeeds_with_valid_command() {
            boolean result = CommandUtils.runCopyCommand(new String[] {"cat"}, "test content");
            assertThat(result).isTrue();
        }
    }

    @Nested
    @EnabledOnOs(OS.WINDOWS)
    class Windows_runCopyCommand {
        @Test
        void succeeds_with_valid_command() {
            boolean result = CommandUtils.runCopyCommand(new String[] {"findstr", "."}, "test content");
            assertThat(result).isTrue();
        }
    }

    @Test
    void run_copy_command_returns_false_for_invalid_executable() {
        boolean result = CommandUtils.runCopyCommand(new String[] {NON_EXISTENT_COMMAND}, "test content");
        assertThat(result).isFalse();
    }
}
