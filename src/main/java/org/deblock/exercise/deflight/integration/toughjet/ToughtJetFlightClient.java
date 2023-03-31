package org.deblock.exercise.deflight.integration.toughjet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import org.deblock.exercise.deflight.domain.Flight;
import org.deblock.exercise.deflight.domain.FlightSearchCriteria;
import org.deblock.exercise.deflight.exception.ExternalApiException;
import org.deblock.exercise.deflight.integration.FlightClient;
import org.deblock.exercise.deflight.tools.timezone.AirportZoneIdResolver;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

public class ToughtJetFlightClient implements FlightClient {

    private final HttpClient httpClient;
    private final URI uri;
    private final ObjectMapper objectMapper;
    private final AirportZoneIdResolver airportZoneIdResolver;
    private final CircuitBreaker circuitBreaker;

    public ToughtJetFlightClient(URI uri,
                                 HttpClient httpClient,
                                 ObjectMapper objectMapper,
                                 AirportZoneIdResolver airportZoneIdResolver,
                                 CircuitBreaker circuitBreaker) {
        this.uri = uri;
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.airportZoneIdResolver = airportZoneIdResolver;
        this.circuitBreaker = circuitBreaker;
    }

    @Override
    public List<Flight> getFlight(FlightSearchCriteria flightSearchCriteria) throws ExternalApiException {
        try {
            var httpResponse = sendRequestSafely(flightSearchCriteria).call();
            return Arrays.stream(objectMapper.readValue(httpResponse.body(), ToughJetResponse[].class))
                    .map(crazyAirResponse -> crazyAirResponse.toFlight(airportZoneIdResolver::resolve))
                    .toList();
        } catch (Exception e) {
            throw new ExternalApiException("There was a problem with tough jet API");
        }
    }

    private Callable<HttpResponse<String>> sendRequestSafely(FlightSearchCriteria flightSearchCriteria) throws JsonProcessingException {
        final var request = HttpRequest.newBuilder(uri.resolve("/tough-jet/flight-info"))
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(ToughJetRequest.from(flightSearchCriteria))))
                .build();
        return CircuitBreaker.decorateCallable(
                circuitBreaker, () -> httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        );
    }
}
