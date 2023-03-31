package org.deblock.exercise.deflight.integration.crazyair;

import org.deblock.exercise.deflight.domain.Flight;
import org.deblock.exercise.deflight.tools.money.Money;
import org.deblock.exercise.deflight.domain.FlightInformationSupplier;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.function.Function;

record CrazyAirResponse(
        String airline,
        BigDecimal price,
        String departureAirportCode,
        String destinationAirportCode,
        LocalDateTime departureDate,
        LocalDateTime arrivalDate
) {
    public Flight toFlight(Function<String, ZoneId> airportTimeZoneIdFunction) {
        return new Flight(
                airline,
                FlightInformationSupplier.CRAZY_AIR,
                new Money(price),
                departureAirportCode,
                destinationAirportCode,
                departureDate.atZone(airportTimeZoneIdFunction.apply(departureAirportCode)),
                arrivalDate.atZone(airportTimeZoneIdFunction.apply(destinationAirportCode))
        );
    }
}
