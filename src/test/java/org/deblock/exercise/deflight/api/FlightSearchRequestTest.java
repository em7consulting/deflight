package org.deblock.exercise.deflight.api;

import org.deblock.exercise.deflight.exception.BadRequestException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FlightSearchRequestTest {

    @ParameterizedTest
    @MethodSource("arguments")
    void shouldThrowBadRequestExceptionWhenIncorrectValues(
            String origin,
            String destination,
            LocalDate departureDate,
            LocalDate returnDate,
            int numberOfPassengers,
            String expectedMessage
    ) {
        assertThatThrownBy(() -> new FlightSearchRequest(origin, destination, departureDate, returnDate, numberOfPassengers))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(expectedMessage);
    }

    private static Stream<Arguments> arguments() {
        return Stream.of(
                Arguments.of("AAAA", "AAA", LocalDate.parse("2023-01-21"), LocalDate.parse("2023-01-21"), 4, "origin length must be equal to 3"),
                Arguments.of("AAA", "AAAA", LocalDate.parse("2023-01-21"), LocalDate.parse("2023-01-21"), 4, "destination length must be equal to 3"),
                Arguments.of("AAA", "AAA", LocalDate.parse("2023-01-22"), LocalDate.parse("2023-01-21"), 4, "departureDate cannot be after returnDate"),
                Arguments.of("AAA", "AAA", LocalDate.parse("2023-01-21"), LocalDate.parse("2023-01-21"), 0, "numberOfPassengers must be in range [0,4]"),
                Arguments.of("AAA", "AAA", LocalDate.parse("2023-01-21"), LocalDate.parse("2023-01-21"), 5, "numberOfPassengers must be in range [0,4]")
        );
    }

}