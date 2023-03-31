package org.deblock.exercise.deflight.domain;

import org.deblock.exercise.deflight.exception.ExternalApiException;
import org.deblock.exercise.deflight.integration.FlightClient;
import org.deblock.exercise.deflight.tools.money.Money;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;

class FlightSearchServiceTest {

    private final FlightClient flightClient1 = Mockito.mock(FlightClient.class);
    private final FlightClient flightClient2 = Mockito.mock(FlightClient.class);
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final FlightSearchService flightSearchService = new FlightSearchService(
            List.of(flightClient1, flightClient2), executorService
    );

    @Test
    void shouldReturnFlightsBasedOnClientsSearch() throws ExternalApiException {
        // given
        var flightSearchCriteria = new FlightSearchCriteria(
                "KRK",
                "LHR",
                LocalDate.parse("2023-05-21"),
                LocalDate.parse("2023-05-22"),
                4
        );
        var flight = new Flight(
                "RyanAir",
                FlightInformationSupplier.TOUGH_JET,
                new Money(new BigDecimal(20)),
                "KRK",
                "LHR",
                ZonedDateTime.of(LocalDate.parse("2023-05-21"), LocalTime.of(20, 15), ZoneId.of("UTC")),
                ZonedDateTime.of(LocalDate.parse("2023-05-22"), LocalTime.of(10, 40), ZoneId.of("UTC"))
        );
        given(flightClient1.getFlight(flightSearchCriteria)).willReturn(List.of(flight));
        given(flightClient2.getFlight(flightSearchCriteria)).willReturn(List.of());

        // when
        List<Flight> searchResult = flightSearchService.search(flightSearchCriteria);

        // then
        assertThat(searchResult).containsExactly(flight);
    }

    @Test
    void shouldReturnFlightsBasedOnClientsSearchWhenSomeClientsReturWithErrors() throws ExternalApiException {
        // given
        var flightSearchCriteria = new FlightSearchCriteria(
                "KRK",
                "LHR",
                LocalDate.parse("2023-05-21"),
                LocalDate.parse("2023-05-22"),
                4
        );
        var flight = new Flight(
                "RyanAir",
                FlightInformationSupplier.TOUGH_JET,
                new Money(new BigDecimal(20)),
                "KRK",
                "LHR",
                ZonedDateTime.of(LocalDate.parse("2023-05-21"), LocalTime.of(20, 15), ZoneId.of("UTC")),
                ZonedDateTime.of(LocalDate.parse("2023-05-22"), LocalTime.of(10, 40), ZoneId.of("UTC"))
        );
        given(flightClient1.getFlight(flightSearchCriteria)).willReturn(List.of(flight));
        given(flightClient2.getFlight(flightSearchCriteria)).willThrow(new ExternalApiException("message"));

        // when
        List<Flight> searchResult = flightSearchService.search(flightSearchCriteria);

        // then
        assertThat(searchResult).containsExactly(flight);
    }
}