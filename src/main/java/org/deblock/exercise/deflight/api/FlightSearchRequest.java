package org.deblock.exercise.deflight.api;

import org.deblock.exercise.deflight.domain.FlightSearchCriteria;
import org.deblock.exercise.deflight.tools.validation.Checks;

import java.time.LocalDate;

public record FlightSearchRequest(
        String origin,
        String destination,
        LocalDate departureDate,
        LocalDate returnDate,
        int numberOfPassengers
) {
    public FlightSearchRequest(
            String origin,
            String destination,
            LocalDate departureDate,
            LocalDate returnDate,
            int numberOfPassengers
    ) {
        this.origin = Checks.exactLength(origin, 3, "origin");
        this.destination = Checks.exactLength(destination, 3, "destination");
        Checks.fromNotAfterTo(departureDate, returnDate, "departureDate", "returnDate");
        this.departureDate = departureDate;
        this.returnDate = returnDate;
        this.numberOfPassengers = Checks.valueInRange(numberOfPassengers, 0, 4, "numberOfPassengers");
    }

    public FlightSearchCriteria toFlightSearchCriteria() {
        return new FlightSearchCriteria(
                origin,
                destination,
                departureDate,
                returnDate,
                numberOfPassengers
        );
    }
}
