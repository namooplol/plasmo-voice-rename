package org.namo.plasmoname.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigManager {

    private static final Path PATH = Path.of("config/plasmo-name.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static Config config;

    public static void init() throws IOException {
        if (Files.notExists(PATH)) {
            config = Config.defaultConfig();
            save();
        } else {
            try (Reader reader = Files.newBufferedReader(PATH)) {
                config = GSON.fromJson(reader, Config.class);
            }
        }
    }

    public static void save() throws IOException {
        try (Writer writer = Files.newBufferedWriter(PATH)) {
            GSON.toJson(config, writer);
        }
    }


    public static String getDisplayName(String uuid) {
        return config.players().getOrDefault(uuid, "Unknown");
    }

    public static void setDisplayName(String uuid, String name) throws IOException {
        boolean exists = config.players().containsKey(uuid);

        config.players().put(uuid, name); // put จัดการให้แล้ว
        save();

    }
}