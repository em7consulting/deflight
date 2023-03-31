package org.deblock.exercise.deflight.tools.timezone;

import javax.validation.constraints.NotNull;
import java.time.ZoneId;
import java.util.Map;
import java.util.Optional;

public class LocalAirportZoneIdResolver implements AirportZoneIdResolver {
    private static final ZoneId UTC = ZoneId.of("UTC");

    private final Map<String, ZoneId> airportCodeToZoneId;

    public LocalAirportZoneIdResolver(
            Map<String, ZoneId> airportCodeToZoneId) {
        this.airportCodeToZoneId = airportCodeToZoneId;
    }

    @Override
    public ZoneId resolve(@NotNull String airportCode) {
        return Optional.ofNullable(airportCodeToZoneId.get(airportCode)).orElse(UTC);
    }
}
