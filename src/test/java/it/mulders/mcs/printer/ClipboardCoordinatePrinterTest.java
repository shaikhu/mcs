package it.mulders.mcs.printer;

import static org.mockito.Mockito.*;

import it.mulders.mcs.clipboard.ClipboardService;
import it.mulders.mcs.search.artifact.SearchResponse;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ClipboardCoordinatePrinterTest implements WithAssertions {

    private static final String GAV_COORDINATES = "org.codehaus.plexus:plexus-utils:3.4.1";

    private static final CoordinatePrinter GAV_PRINTER = new GavOutput();

    private static final SearchResponse.Response RESPONSE =
            new SearchResponse.Response(1, 0, new SearchResponse.Response.Doc[] {
                new SearchResponse.Response.Doc(
                        "org.codehaus.plexus:plexus-utils:3.4.1",
                        "org.codehaus.plexus",
                        "plexus-utils",
                        "3.4.1",
                        null,
                        "jar",
                        1630022910000L)
            });

    private ClipboardService clipboard;

    @BeforeEach
    void setup() {
        clipboard = mock(ClipboardService.class);
    }

    @Test
    void copies_coordinates_when_clipboard_is_available_and_copy_successful() {
        // Arrange
        when(clipboard.isAvailable()).thenReturn(true);
        when(clipboard.copy(GAV_COORDINATES)).thenReturn(true);

        // Act
        var clipboardPrinter = new ClipboardCoordinatePrinter(GAV_PRINTER, clipboard);
        var output = getOutput(clipboardPrinter);

        // Verify
        verify(clipboard).isAvailable();
        verify(clipboard).copy(GAV_COORDINATES);

        assertThat(output).contains(GAV_COORDINATES).contains("Copied to clipboard");
    }

    @Test
    void prints_warning_when_copy_fails_even_if_clipboard_is_available() {
        // Arrange
        when(clipboard.isAvailable()).thenReturn(true);
        when(clipboard.copy(GAV_COORDINATES)).thenReturn(false);

        // Act
        var clipboardPrinter = new ClipboardCoordinatePrinter(GAV_PRINTER, clipboard);
        var output = getOutput(clipboardPrinter);

        // Verify
        verify(clipboard).isAvailable();
        verify(clipboard).copy(GAV_COORDINATES);

        assertThat(output).contains(GAV_COORDINATES).contains("Failed to copy to clipboard");
    }

    @Test
    void prints_warning_clipboard_is_not_available() {
        // Arrange
        when(clipboard.isAvailable()).thenReturn(false);

        // Act
        var clipboardPrinter = new ClipboardCoordinatePrinter(GAV_PRINTER, clipboard);
        var output = getOutput(clipboardPrinter);

        // Verify
        verify(clipboard).isAvailable();
        verify(clipboard, never()).copy(anyString());

        assertThat(output).contains(GAV_COORDINATES).contains("Clipboard not available");
    }

    private String getOutput(final ClipboardCoordinatePrinter clipboardPrinter) {
        var out = new ByteArrayOutputStream();
        var stream = new PrintStream(out);
        clipboardPrinter.print(null, RESPONSE, stream);
        return out.toString();
    }
}
