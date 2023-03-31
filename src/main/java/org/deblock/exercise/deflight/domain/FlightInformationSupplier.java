package org.deblock.exercise.deflight.domain;

public enum FlightInformationSupplier {
    CRAZY_AIR("CrazyAir"), TOUGH_JET("ToughJet");

    private final String name;

    FlightInformationSupplier(String name) {
        this.name = name;
    }
}
