package net.stedin.demo.saga.orchestrator.route.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum Functie {
    MEDEWERKER,
    MONTEUR;

    @JsonCreator
    public static Functie fromString(String value) {
        return Arrays.stream(Functie.values())
            .filter(f -> f.name().equalsIgnoreCase(value))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Value cannot be converted to a Functie enum"));
    }
}
