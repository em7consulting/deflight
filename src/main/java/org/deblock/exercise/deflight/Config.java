package org.deblock.exercise.deflight;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.deblock.exercise.deflight.integration.toughjet.ToughtJetFlightClient;
import org.deblock.exercise.deflight.tools.timezone.AirportZoneIdResolver;
import org.deblock.exercise.deflight.tools.timezone.LocalAirportZoneIdResolver;
import org.deblock.exercise.deflight.integration.FlightClient;
import org.deblock.exercise.deflight.integration.crazyair.CrazyAirFlightClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.http.HttpClient;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class Config {

    @Bean
    HttpClient httpClient() {
        return HttpClient.newHttpClient();
    }

    @Bean
    ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    @Bean
    @Qualifier("crazyAir")
    FlightClient crazyAirFlightClient(@Value("${integration.crazy-air.uri}") URI crazyAirUri,
                                      HttpClient httpClient,
                                      ObjectMapper objectMapper,
                                      AirportZoneIdResolver airportZoneIdResolver,
                                      CircuitBreakerRegistry circuitBreakerRegistry) {
        return new CrazyAirFlightClient(
                crazyAirUri, httpClient, objectMapper, airportZoneIdResolver, circuitBreakerRegistry.circuitBreaker("crazyAir")
        );
    }

    @Bean
    @Qualifier("toughJet")
    FlightClient toughJetFlightClient(@Value("${integration.tough-jet.uri}") URI toughJetUri,
                                      HttpClient httpClient,
                                      ObjectMapper objectMapper,
                                      AirportZoneIdResolver airportZoneIdResolver,
                                      CircuitBreakerRegistry circuitBreakerRegistry) {
        return new ToughtJetFlightClient(
                toughJetUri, httpClient, objectMapper, airportZoneIdResolver, circuitBreakerRegistry.circuitBreaker("toughAir")
        );
    }

    @Bean
    List<FlightClient> flightClients(@Qualifier("crazyAir") FlightClient crazyAirFlightClient,
                                     @Qualifier("toughJet") FlightClient toughJetFlightClient) {
        return List.of(crazyAirFlightClient, toughJetFlightClient);
    }

    @Bean
    AirportZoneIdResolver airportZoneIdResolver() {
        return new LocalAirportZoneIdResolver(Map.of(
                "LHR", ZoneId.of("Etc/UTC"),
                "DBX", ZoneId.of("Asia/Dubai")
        ));
    }

    @Bean
    CircuitBreakerRegistry circuitBreakerRegistry() {
        final var circuitBreakerConfig = CircuitBreakerConfig.ofDefaults();
        return CircuitBreakerRegistry.of(circuitBreakerConfig);
    }

    @Bean
    ExecutorService executorService(@Value("${executorservice.thread.count}") int threadCount) {
        return Executors.newFixedThreadPool(threadCount);
    }
}
