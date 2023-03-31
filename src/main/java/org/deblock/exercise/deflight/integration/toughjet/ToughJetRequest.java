package org.deblock.exercise.deflight.integration.toughjet;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.deblock.exercise.deflight.domain.FlightSearchCriteria;

import java.time.LocalDate;

record ToughJetRequest(
        String from,
        String to,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate outboundDate,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate inboundDate,
        int numberOfAdults
) {
    public static ToughJetRequest from(FlightSearchCriteria flightSearchCriteria) {
        return new ToughJetRequest(
                flightSearchCriteria.departureAirportCode(),
                flightSearchCriteria.destinationAirportCode(),
                flightSearchCriteria.departureDate(),
                flightSearchCriteria.arrivalDate(),
                flightSearchCriteria.numberOfPassengers()
        );
    }
}
