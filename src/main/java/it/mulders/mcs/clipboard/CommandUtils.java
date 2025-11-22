package it.mulders.mcs.clipboard;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

class CommandUtils {
    private CommandUtils() {}

    static boolean copyCommandExists(String... command) {
        try {
            var processBuilder = new ProcessBuilder(command);
            var process = processBuilder.start();
            return process.waitFor() == 0;
        } catch (IOException | InterruptedException e) {
            return false;
        }
    }

    static boolean runCopyCommand(String[] command, String text) {
        try {
            var processBuilder = new ProcessBuilder(command);
            var process = processBuilder.start();
            try (var out = process.getOutputStream()) {
                out.write(text.getBytes(StandardCharsets.UTF_8));
            }
            return process.waitFor() == 0;
        } catch (IOException | InterruptedException e) {
            return false;
        }
    }
}
