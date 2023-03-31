package org.deblock.exercise.deflight.integration;

import org.deblock.exercise.deflight.domain.Flight;
import org.deblock.exercise.deflight.domain.FlightSearchCriteria;
import org.deblock.exercise.deflight.exception.ExternalApiException;

import java.util.List;

public interface FlightClient {
    List<Flight> getFlight(FlightSearchCriteria flightSearchCriteria) throws ExternalApiException;
}
