package org.deblock.exercise.deflight.domain;

import java.time.LocalDate;

public record FlightSearchCriteria(
        String departureAirportCode,
        String destinationAirportCode,
        LocalDate departureDate,
        LocalDate arrivalDate,
        int numberOfPassengers
) {
}
