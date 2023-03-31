package org.deblock.exercise.deflight.domain;

import org.deblock.exercise.deflight.tools.money.Money;

import java.time.ZonedDateTime;

public record Flight(
        String airline,
        FlightInformationSupplier supplier,
        Money totalPrice,
        String departureAirportCode,
        String destinationAirportCode,
        ZonedDateTime departureAt,
        ZonedDateTime arrivalAt
) {
    public Flight(
            String airline,
            FlightInformationSupplier supplier,
            Money basePrice,
            Money tax,
            Money discount,
            String departureAirportCode,
            String destinationAirportCode,
            ZonedDateTime departureAt,
            ZonedDateTime arrivalAt
    ) {
        this(
                airline,
                supplier,
                basePrice.add(tax).subtract(discount),
                departureAirportCode,
                destinationAirportCode,
                departureAt,
                arrivalAt
        );
    }
}
