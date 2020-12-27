package com.sparta.training6;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import lombok.Data;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Data
@JsonIgnoreProperties(ignoreUnknown = true)

public class CelestialObject {

    private String name;
    private String type;  // Planet, Moon, Asteroid, Meteorite
    private String systemName;
    private String parentObject;
    private int diameter; // в км
    private String atmosphere = "unknown";
    private String water = "unknown";
    @JsonProperty("extraterrestrialLife")
    private String life = "unknown";
    private String oil = "unknown";
    private String gas = "unknown";
    private String minerals = "unknown";
    private String ore = "unknown";

    public CelestialObject() {
    }

    //поиск объекта по названию
    public CelestialObject findObjectsByName(List<CelestialObject> celestialObject, String searchByName) {
        CelestialObject foundCelestialObject = null;
        for (CelestialObject celestialObjects : celestialObject) {
            if (celestialObjects.getName().equals(searchByName)) {
                foundCelestialObject = celestialObjects;
            }
        }
        return foundCelestialObject;
    }

    //поиск всех спутников планеты
    public List<CelestialObject> findAllMoons(List<CelestialObject> celestialObject, String parentName) {
        List<CelestialObject> foundCelestialObjects = new ArrayList<>();
        for (CelestialObject celestialObjects : celestialObject) {
            if (celestialObjects.getParentObject().equals(parentName) && (celestialObjects.getType().contains("oon"))) {
                foundCelestialObjects.add(celestialObjects);
            }
        }
        return foundCelestialObjects;
    }

    //поиск всех объектов звездной системы
    public List<CelestialObject> findAllCelestialObjects(List<CelestialObject> celestialObject, String systemName) {
        List<CelestialObject> foundCelestialObjects = new ArrayList<>();
        for (CelestialObject celestialObjects : celestialObject) {
            if (celestialObjects.getSystemName().equals(systemName)) {
                foundCelestialObjects.add(celestialObjects);
            }
        }
        return foundCelestialObjects;
    }

    //поиск всех планет звездной системы
    public List<CelestialObject> findAllPlanets(List<CelestialObject> celestialObject, String systemName) {
        List<CelestialObject> foundCelestialObjects = new ArrayList<>();
        for (CelestialObject celestialObjects : celestialObject) {
            if (celestialObjects.getSystemName().equals(systemName) && (celestialObjects.getType().contains("lanet"))) {
                foundCelestialObjects.add(celestialObjects);
            }
        }
        return foundCelestialObjects;
    }

    //обновлние карты
    public void updateGalaxyMap(List<CelestialObject> celestialObject, Path filePath) throws IOException {
        List<CelestialObject> celestialObjectsBackup = new ArrayList<>(celestialObject);
        celestialObject.clear();
        celestialObject.addAll(galaxyMapStandardization(celestialObjectsBackup));
        //обновляем карту
        new ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValue(filePath.toFile(), celestialObject);
    }

    //приведение карты к "шаблону": type, water, atmosphere, extraterrestrialLife, oil, gas, minerals, ore - всегда пишутся с маленькой буквы
    public List<CelestialObject> galaxyMapStandardization(List<CelestialObject> celestialObject) {
        for (CelestialObject iCelestialObject : celestialObject) {
            iCelestialObject.setType(iCelestialObject.getType().toLowerCase());
            iCelestialObject.setWater(iCelestialObject.getWater().toLowerCase());
            iCelestialObject.setAtmosphere(iCelestialObject.getAtmosphere().toLowerCase());
            iCelestialObject.setLife(iCelestialObject.getLife().toLowerCase());
            iCelestialObject.setOil(iCelestialObject.getOil().toLowerCase());
            iCelestialObject.setGas(iCelestialObject.getGas().toLowerCase());
            iCelestialObject.setMinerals(iCelestialObject.getMinerals().toLowerCase());
            iCelestialObject.setOre(iCelestialObject.getOre().toLowerCase());
        }
        return celestialObject;
    }
}

