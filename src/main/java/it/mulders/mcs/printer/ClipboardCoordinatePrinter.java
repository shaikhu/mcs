package it.mulders.mcs.printer;

import it.mulders.mcs.clipboard.ClipboardService;
import it.mulders.mcs.clipboard.ClipboardServiceFactory;
import it.mulders.mcs.search.artifact.SearchQuery;
import it.mulders.mcs.search.artifact.SearchResponse;
import java.io.PrintStream;

/**
 * Decorator for CoordinatePrinter that adds clipboard copying functionality.
 * Delegates the actual formatting to the wrapped printer, then handles clipboard operations.
 */
public class ClipboardCoordinatePrinter implements OutputPrinter {
    private final CoordinatePrinter delegate;
    private final ClipboardService clipboardService;

    public ClipboardCoordinatePrinter(final CoordinatePrinter delegate) {
        this(delegate, ClipboardServiceFactory.getClipboardService());
    }

    // Visible for testing
    ClipboardCoordinatePrinter(final CoordinatePrinter delegate, final ClipboardService clipboardService) {
        this.delegate = delegate;
        this.clipboardService = clipboardService;
    }

    @Override
    public void print(final SearchQuery query, final SearchResponse.Response response, final PrintStream stream) {
        // Let the delegate do the checking/printing of coordinates
        delegate.print(query, response, stream);

        // If delegate didn't error, we know there's only a single result
        var doc = response.docs()[0];
        var coordinates = delegate.coordinatesFor(doc);

        // Handle copy
        handleClipboardCopy(coordinates, stream);
    }

    private void handleClipboardCopy(final String coordinates, final PrintStream stream) {
        if (clipboardService.isAvailable()) {
            boolean success = clipboardService.copy(coordinates);
            if (success) {
                stream.println("Copied to clipboard");
            } else {
                stream.println("Failed to copy to clipboard");
            }
        } else {
            stream.println("Clipboard not available on this system");
        }
    }
}
