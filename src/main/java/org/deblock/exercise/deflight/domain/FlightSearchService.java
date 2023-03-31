package org.deblock.exercise.deflight.domain;

import org.deblock.exercise.deflight.exception.ExternalApiException;
import org.deblock.exercise.deflight.integration.FlightClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Component
public class FlightSearchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlightSearchService.class);

    private final List<FlightClient> flightClients;
    private final ExecutorService executorService;

    public FlightSearchService(List<FlightClient> flightClients, ExecutorService executorService) {
        this.flightClients = flightClients;
        this.executorService = executorService;
    }

    public List<Flight> search(FlightSearchCriteria flightSearchCriteria) {
        return flightClients.stream()
                .map(client -> executorService.submit( () -> searchFlights(flightSearchCriteria, client)))
                .map(this::getResults)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<Flight> searchFlights(FlightSearchCriteria flightSearchCriteria, FlightClient client) {
        try {
            return client.getFlight(flightSearchCriteria);
        } catch (ExternalApiException e) {
            LOGGER.error(e.getMessage(), e);
            return List.of();
        }
    }

    private List<Flight> getResults(Future<List<Flight>> future) {
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            return List.of();
        }
    }
}
