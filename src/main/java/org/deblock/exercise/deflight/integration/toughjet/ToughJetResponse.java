package org.deblock.exercise.deflight.integration.toughjet;

import org.deblock.exercise.deflight.domain.Flight;
import org.deblock.exercise.deflight.tools.money.Money;
import org.deblock.exercise.deflight.domain.FlightInformationSupplier;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.util.function.Function;

record ToughJetResponse(
        String carrier,
        BigDecimal basePrice,
        BigDecimal tax,
        BigDecimal discount,
        String departureAirportName,
        String arrivalAirportName,
        Instant outboundDateTime,
        Instant inboundDateTime
) {
    public Flight toFlight(Function<String, ZoneId> airportTimeZoneIdFunction) {
        return new Flight(
                carrier,
                FlightInformationSupplier.CRAZY_AIR,
                new Money(basePrice),
                new Money(tax),
                new Money(discount),
                departureAirportName,
                arrivalAirportName,
                outboundDateTime.atZone(airportTimeZoneIdFunction.apply(departureAirportName)),
                inboundDateTime.atZone(airportTimeZoneIdFunction.apply(arrivalAirportName))
        );
    }
}
