package org.deblock.exercise.deflight.api;

import org.deblock.exercise.deflight.domain.FlightSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
class FlightController {

    private final FlightSearchService flightSearchService;

    public FlightController(FlightSearchService flightSearchService) {
        this.flightSearchService = flightSearchService;
    }

    @PostMapping(value = "/flights/search", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<FlightSearchResponse>> searchFlight(@RequestBody @Valid FlightSearchRequest request) {
        final var flightSearchCriteria = request.toFlightSearchCriteria();
        final var flightSearchResult = flightSearchService.search(flightSearchCriteria).parallelStream()
                .map(FlightSearchResponse::fromFlight)
                .collect(Collectors.toList());
        return ResponseEntity.ok(flightSearchResult);
    }
}
