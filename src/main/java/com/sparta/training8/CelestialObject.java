package com.sparta.training8;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
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
        CelestialObject foundCelestialObject;
        for (CelestialObject celestialObjects : celestialObject) {
            if (celestialObjects.getName().equals(searchByName)) {
                foundCelestialObject = celestialObjects;
                return foundCelestialObject;
            }
        }
        System.out.println("Искомый объект не найден! Вместо него летим на Землю");
        return celestialObject.get(0);
    }

    //поиск всех спутников планеты
    public List<CelestialObject> findAllMoons(List<CelestialObject> celestialObject, String parentName) {
        List<CelestialObject> foundCelestialObjects = new ArrayList<>();
        int counter = 0;
        for (CelestialObject celestialObjects : celestialObject) {
            if (celestialObjects.getParentObject().equals(parentName) && (celestialObjects.getType().contains("oon"))) {
                foundCelestialObjects.add(celestialObjects);
                counter++;
            }
        }
        if (counter == 0) {
            foundCelestialObjects.add(celestialObject.get(0));
            System.out.println("У искомого объекта нет спутников! Вместо него летим на Землю");
        }
        return foundCelestialObjects;
    }

    //поиск всех объектов звездной системы
    public List<CelestialObject> findAllCelestialObjects(List<CelestialObject> celestialObject, String systemName) {
        List<CelestialObject> foundCelestialObjects = new ArrayList<>();
        int counter = 0;
        for (CelestialObject celestialObjects : celestialObject) {
            if (celestialObjects.getSystemName().equals(systemName)) {
                foundCelestialObjects.add(celestialObjects);
                counter++;
            }
        }
        if (counter == 0) {
            foundCelestialObjects.add(celestialObject.get(0));
            System.out.println("Искомой системы не найдено или у системы нет объектов! Вместо него летим на Землю");
        }
        return foundCelestialObjects;
    }

    //поиск всех планет звездной системы
    public List<CelestialObject> findAllPlanets(List<CelestialObject> celestialObject, String systemName) {
        List<CelestialObject> foundCelestialObjects = new ArrayList<>();
        int counter = 0;
        for (CelestialObject celestialObjects : celestialObject) {
            if (celestialObjects.getSystemName().equals(systemName) && (celestialObjects.getType().contains("lanet"))) {
                foundCelestialObjects.add(celestialObjects);
                counter++;
            }
        }
        if (counter == 0) {
            foundCelestialObjects.add(celestialObject.get(0));
            System.out.println("Искомой системы не найдено или у системы нет планет! Вместо него летим на Землю");
        }
        return foundCelestialObjects;
    }

    //обновлние карты
    public void updateGalaxyMap(List<CelestialObject> celestialObject, Path filePath) throws IOException {
        List<CelestialObject> celestialObjectsBackup = new ArrayList<>(celestialObject);
        celestialObject.clear();
        celestialObject.addAll(galaxyMapStandardization(celestialObjectsBackup));
        new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(filePath.toFile(), celestialObject);
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

