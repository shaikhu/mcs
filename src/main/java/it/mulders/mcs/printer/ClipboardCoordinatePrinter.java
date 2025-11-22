package it.mulders.mcs.printer;

import it.mulders.mcs.clipboard.ClipboardService;
import it.mulders.mcs.clipboard.ClipboardServiceFactory;
import it.mulders.mcs.search.artifact.SearchQuery;
import it.mulders.mcs.search.artifact.SearchResponse;
import java.io.PrintStream;

/**
 * Decorator for CoordinatePrinter adding clipboard copying functionality.
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
        // Let the delegate do the printing of coordinates (will error if more than one result)
        delegate.print(query, response, stream);
        var doc = response.docs()[0];
        var coordinates = delegate.coordinatesFor(doc);
        handleClipboardCopy(coordinates, stream);
    }

    private void handleClipboardCopy(final String coordinates, final PrintStream stream) {
        if (clipboardService.isAvailable()) {
            var isSuccessful = clipboardService.copy(coordinates);
            if (isSuccessful) {
                stream.println("Copied to clipboard");
            } else {
                stream.println("Failed to copy to clipboard");
            }
        } else {
            stream.println("Clipboard not available on this system");
        }
    }
}
