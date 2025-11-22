package it.mulders.mcs.clipboard;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

class ClipboardCommandUtils {
    private ClipboardCommandUtils() {}

    static boolean copyCommandExists(String... command) {
        try {
            var processBuilder = new ProcessBuilder(command);
            var process = processBuilder.start();
            return process.waitFor() == 0;
        } catch (IOException | InterruptedException e) {
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt(); // Restore interrupt status
            }
            return false;
        }
    }

    static boolean runCopyCommand(String text, String... command) {
        if (text == null || command == null || command.length == 0) {
            return false;
        }

        try {
            var processBuilder = new ProcessBuilder(command);
            var process = processBuilder.start();
            try (var out = process.getOutputStream()) {
                out.write(text.getBytes(StandardCharsets.UTF_8));
            }
            return process.waitFor() == 0;
        } catch (IOException | InterruptedException e) {
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt(); // Restore interrupt status
            }
            System.err.printf("Failed to run copy command %s: %s%n", Arrays.toString(command), e.getLocalizedMessage());
            return false;
        }
    }
}
