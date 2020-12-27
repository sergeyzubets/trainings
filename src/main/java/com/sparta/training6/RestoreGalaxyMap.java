package com.sparta.training6;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class RestoreGalaxyMap {

    public static void main(String[] args) throws IOException {
        Path filePathBackup = Paths.get("src", "main", "resources", "galaxyMapBackup.json");
        Path filePathToRestore = Paths.get("src", "main", "resources", "galaxyMap.json");
        List<CelestialObject> celestialObjects =
                new ObjectMapper()
                        .readValue(filePathBackup.toFile(), new TypeReference<List<CelestialObject>>() {
                        });
        new ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValue(filePathToRestore.toFile(), celestialObjects);
        System.out.println("Карта восстановлена из бэкапа\n" + filePathToRestore);
    }
}
