package org.namo.plasmoname.json;

import java.util.HashMap;
import java.util.Map;

public record Config(
        Map<String, String> players
) {
    public static Config defaultConfig() {
        return new Config(new HashMap<>());
    }
}