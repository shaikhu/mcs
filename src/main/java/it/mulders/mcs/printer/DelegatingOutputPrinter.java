package it.mulders.mcs.printer;

import it.mulders.mcs.search.artifact.SearchQuery;
import it.mulders.mcs.search.artifact.SearchResponse;
import java.io.PrintStream;

/**
 * Output printer that delegates to a different printer, depending on the number of search results.
 */
public class DelegatingOutputPrinter implements OutputPrinter {
    private final OutputPrinter noOutput;
    private final OutputPrinter coordinateOutput;
    private final OutputPrinter tabularSearchOutput;

    public DelegatingOutputPrinter(
            final OutputPrinter coordinateOutput, final boolean showVulnerabilities, final Boolean copyToClipboard) {
        this.noOutput = new NoOutputPrinter();
        this.tabularSearchOutput = new TabularOutputPrinter(showVulnerabilities);
        this.coordinateOutput = wrapWithClipboardIfEnabled(copyToClipboard, coordinateOutput);
    }

    // Visible for testing
    DelegatingOutputPrinter(
            final OutputPrinter noOutput,
            final OutputPrinter coordinateOutput,
            final OutputPrinter tabularSearchOutput) {
        this.noOutput = noOutput;
        this.coordinateOutput = coordinateOutput;
        this.tabularSearchOutput = tabularSearchOutput;
    }

    @Override
    public void print(final SearchQuery query, final SearchResponse.Response response, final PrintStream stream) {
        switch (response.numFound()) {
            case 0 -> noOutput.print(query, response, stream);
            case 1 -> coordinateOutput.print(query, response, stream);
            default -> tabularSearchOutput.print(query, response, stream);
        }
    }

    private static OutputPrinter wrapWithClipboardIfEnabled(
            final Boolean copyToClipboard, final OutputPrinter coordinateOutput) {
        // Use the flag (true or false) if provided, otherwise check system property
        var isClipboardEnabled = copyToClipboard != null
                ? copyToClipboard
                : "true".equals(System.getProperty("clipboard.copy", "false"));

        if (isClipboardEnabled && coordinateOutput instanceof CoordinatePrinter printer) {
            return new ClipboardCoordinatePrinter(printer);
        }
        return coordinateOutput;
    }
}
