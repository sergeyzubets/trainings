package com.sparta.training8;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

public class SpaceShip extends ExplorationHub {

    String shipName;
    Path logFilePath;
    List<ShipLog> shipLogs;
    ShipLog shipLog;

    public SpaceShip(String shipName, double atmosphereExplRate, double waterExplRate, double lifeExplRate) throws IOException {
        this.shipName = shipName;
        this.atmosphereExplRate = atmosphereExplRate;
        this.waterExplRate = waterExplRate;
        this.lifeExplRate = lifeExplRate;
        createShipLog();
    }

    public void exploration(CelestialObject celestialObject, int durationDays, List<CelestialObject> celestialObjectsArray, Path filePath) throws IOException {
        atmosphereExploration(celestialObject, durationDays);
        waterExploration(celestialObject, durationDays);
        lifeExploration(celestialObject, durationDays);
        updateShipLog(celestialObject, durationDays);
        celestialObject.updateGalaxyMap(celestialObjectsArray, filePath);
        System.out.println("Корабль '" + shipName + "' завершил исследование объекта '" + celestialObject.getName() + "'. Бортовой журнал и карта обновлены");
    }

    public void explorationAll(List<CelestialObject> celestialObjectsArray, List<Integer> durationDaysArray, List<CelestialObject> celestialObjects, Path filePath) throws IOException {
        for (int i = 0; i < celestialObjectsArray.size(); i++) {
            exploration(celestialObjectsArray.get(i), durationDaysArray.get(i), celestialObjects, filePath);
        }
    }

    //создание бортового журнала при создании корабля
    public void createShipLog() throws IOException {
        logFilePath = Paths.get("src", "main", "resources", "spaceShipLogs", shipName + ".json");
        List<ShipLog> shipLogs = new ArrayList<>();
        new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(logFilePath.toFile(), shipLogs);
        shipLog = new ShipLog();
    }

    //запись результатов исслеловний в бортовой жкрнал
    public void updateShipLog(CelestialObject celestialObject, int durationDays) throws IOException {
        shipLog.setShipName(shipName);
        shipLog.setShipType(getClass().getSimpleName());
        shipLog.setAtmosphereExplRate(atmosphereExplRate);
        shipLog.setWaterExplRate(waterExplRate);
        shipLog.setLifeExplRate(lifeExplRate);
        shipLog.setAtmosphereExplCoef(atmosphereExplCoef);
        shipLog.setWaterExplCoef(waterExplCoef);
        shipLog.setLifeExplCoef(lifeExplCoef);
        shipLog.setTargetName(celestialObject.getName());
        if (durationDays >= atmosphereMinRequiredDays) {
            shipLog.setAtmosphere(celestialObject.getAtmosphere());
        } else
            shipLog.setAtmosphere("Атмосферу исследовать не успели, не хватило " + (atmosphereMinRequiredDays - durationDays) + " дня(ей).");
        if (durationDays >= waterMinRequiredDays) {
            shipLog.setWater(celestialObject.getWater());
        } else
            shipLog.setWater("Воду исследовать не успели, не хватило " + (waterMinRequiredDays - durationDays) + " дня(ей).");
        if (durationDays >= lifeMinRequiredDays) {
            shipLog.setLife(celestialObject.getLife());
        } else
            shipLog.setLife("Жизнь исследовать не успели, не хватило " + (lifeMinRequiredDays - durationDays) + " дня(ей).");
        shipLog.setLogFilePath(logFilePath);
        shipLogs = new ObjectMapper().readValue(logFilePath.toFile(), new TypeReference<List<ShipLog>>() {
        });
        shipLogs.add(shipLog);
        new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(logFilePath.toFile(), shipLogs);
    }
}