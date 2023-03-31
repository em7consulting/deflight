package org.deblock.exercise.deflight.tools.timezone;

import javax.validation.constraints.NotNull;
import java.time.ZoneId;

@FunctionalInterface
public interface AirportZoneIdResolver {
    ZoneId resolve(@NotNull String airportCode);
}
