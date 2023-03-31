package org.deblock.exercise.deflight.api;

import org.deblock.exercise.deflight.domain.Flight;
import org.deblock.exercise.deflight.tools.money.Money;

import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;

record FlightSearchResponse(
        String airline,
        String supplier,
        Money fare,
        String departureAirportCode,
        String destinationAirportCode,
        String departureDate,
        String arrivalDate
) {
    static FlightSearchResponse fromFlight(Flight flight) {
        return new FlightSearchResponse(
                flight.airline(),
                flight.supplier().name(),
                flight.totalPrice().setScale(2, RoundingMode.CEILING),
                flight.departureAirportCode(),
                flight.destinationAirportCode(),
                flight.departureAt().format(DateTimeFormatter.ISO_DATE_TIME),
                flight.arrivalAt().format(DateTimeFormatter.ISO_DATE_TIME)
        );
    }
}
