package it.mulders.mcs.clipboard;

import java.util.stream.Stream;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ClipboardServiceFactoryTest implements WithAssertions {
    private static Stream<Arguments> clipboardServices() {
        return Stream.of(
                Arguments.of("Mac OS X", MacOSClipboardService.class),
                Arguments.of("Darwin", MacOSClipboardService.class),
                Arguments.of("Windows 10", WindowsClipboardService.class),
                Arguments.of("Windows 11", WindowsClipboardService.class),
                Arguments.of("Linux", LinuxClipboardService.class),
                Arguments.of("Unix", LinuxClipboardService.class),
                Arguments.of("AIX", LinuxClipboardService.class),
                Arguments.of("WINDOWS", WindowsClipboardService.class), // case-insensitive
                Arguments.of("Unknown OS", NoOpClipboardService.class));
    }

    @ParameterizedTest(name = "OS: {0} -> {1}")
    @MethodSource("clipboardServices")
    void should_detect_clipboard_services_from_os_name(String osName, Class<? extends ClipboardService> expectedType) {
        var service = ClipboardServiceFactory.getClipboardService(osName);
        assertThat(service).isInstanceOf(expectedType);
    }
}
