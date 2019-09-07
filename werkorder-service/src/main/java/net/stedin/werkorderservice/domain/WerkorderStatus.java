package net.stedin.werkorderservice.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum WerkorderStatus {
    INACTIEF, ACTIEF, GEANNULEERD, AFGEROND;

    @JsonCreator
    public static WerkorderStatus fromString(String value) {
        return Arrays.stream(WerkorderStatus.values())
            .filter(ws -> ws.name().equalsIgnoreCase(value))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Value cannot be converted to a WerkorderStatus enum"));
    }
}
