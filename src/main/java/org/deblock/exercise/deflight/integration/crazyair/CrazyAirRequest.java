package org.deblock.exercise.deflight.integration.crazyair;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.deblock.exercise.deflight.domain.FlightSearchCriteria;

import java.time.LocalDate;

record CrazyAirRequest(
        String origin,
        String destination,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate departureDate,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate returnDate,
        int passengerCount
) {
    public static CrazyAirRequest from(FlightSearchCriteria flightSearchCriteria) {
        return new CrazyAirRequest(
                flightSearchCriteria.departureAirportCode(),
                flightSearchCriteria.destinationAirportCode(),
                flightSearchCriteria.departureDate(),
                flightSearchCriteria.arrivalDate(),
                flightSearchCriteria.numberOfPassengers()
        );
    }
}
