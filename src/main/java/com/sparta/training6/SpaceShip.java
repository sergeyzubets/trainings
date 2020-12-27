package com.sparta.training6;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@AllArgsConstructor
@Data
@Getter
@Setter

public class SpaceShip {

    protected String shipName;
    protected double atmosphereExplRate; //сколько тыс кв км исследует корабль за ед. времени (ч)
    protected double atmosphereExplCoef = 0.01;
    protected int atmosphereMinRequiredDays;
    protected double atmosphereMinSpentDays;
    protected double waterExplRate;
    protected double waterExplCoef = 0.3;
    protected int waterMinRequiredDays;
    protected double waterMinSpentDays;
    protected double lifeExplCoef = 0.5;
    protected double lifeExplRate;
    protected int lifeMinRequiredDays;
    protected double lifeMinSpentDays;
    protected Path logFilePath;
    protected List<ShipLog> shipLogs;
    protected ShipLog shipLog;

    public SpaceShip(String shipName, double atmosphereExplRate, double waterExplRate, double lifeExplRate) throws IOException {
        this.shipName = shipName;
        this.atmosphereExplRate = atmosphereExplRate;
        this.waterExplRate = waterExplRate;
        this.lifeExplRate = lifeExplRate;
        //создаем пустой бортовой журнал при создании нового корабля
        createShipLog();
    }

    public void exploration(CelestialObject celestialObject, int durationDays, List<CelestialObject> celestialObjectArray, Path filePath) throws IOException {
        //вычисляем площадь повехности шара = PI*D^2
        double surfaceArea = Math.PI * Math.pow(celestialObject.getDiameter(), 2);
        // поиск атмосферы
        atmosphereExploration(celestialObject, durationDays, surfaceArea);
        // поиск воды
        waterExploration(celestialObject, durationDays, surfaceArea);
        // поиск жизни
        lifeExploration(celestialObject, durationDays, surfaceArea);
        //запись бортового журнала
        updateShipLog(celestialObject, durationDays);
        //обновление данных в базе
        celestialObject.updateGalaxyMap(celestialObjectArray, filePath);
        System.out.println("Корабль '" + shipName + "' завершил исследование объекта '" + celestialObject.getName() + "'. Бортовой журнал и карта обновлены");
    }

    public void explorationAll(List<CelestialObject> celestialObjectsArray, List<Integer> durationDaysArray, List<CelestialObject> celestialObjects, Path filePath) throws IOException {
        for (int i = 0; i < celestialObjectsArray.size(); i++) {
            exploration(celestialObjectsArray.get(i), durationDaysArray.get(i), celestialObjects, filePath);
        }
    }

    //Поиск атмосферы. Результат исследования становиться известен, если просканировано >= 1% поверхности
    public void atmosphereExploration(CelestialObject celestialObject, int durationDays, double surfaceArea) {
        if (atmosphereExplRate * 1000 * durationDays * 24 >= surfaceArea * atmosphereExplCoef) {
            boolean b = giveMeBooleanRandom();
            if (b) {
                celestialObject.setAtmosphere("true");
            } else {
                celestialObject.setAtmosphere("false");
            }
        } else {
            atmosphereMinRequiredDays = (int) ((surfaceArea * atmosphereExplCoef) / (atmosphereExplRate * 1000 * 24)) + 1;
        }
    }

    //Поиск воды. Результат исследования становиться известен, если просканировано >= 30% поверхности
    public void waterExploration(CelestialObject celestialObject, int durationDays, double surfaceArea) {
        if (waterExplRate * 1000 * durationDays * 24 >= surfaceArea * waterExplCoef) {
            boolean b = giveMeBooleanRandom();
            if (b) {
                celestialObject.setWater("true");
            } else {
                celestialObject.setWater("false");
            }
        } else {
            waterMinRequiredDays = (int) ((surfaceArea * waterExplCoef) / (waterExplRate * 1000 * 24)) + 1;
        }
    }

    //Поиск внеземной жизни. Результат исследования становиться известен, если просканировано >= 50% поверхности и на объекте есть вода
    public void lifeExploration(CelestialObject celestialObject, int durationDays, double surfaceArea) {
        if (lifeExplRate * 1000 * durationDays * 24 >= surfaceArea * lifeExplCoef) {
            boolean b = giveMeBooleanRandom();
            if (b && celestialObject.getWater().contains("rue")) {
                celestialObject.setLife("true");
            } else {
                celestialObject.setLife("false");
            }
        } else {
            lifeMinRequiredDays = (int) ((surfaceArea * lifeExplCoef) / (lifeExplRate * 1000 * 24)) + 1;
        }
    }

    public boolean giveMeBooleanRandom() {
        Random rd = new Random();
        return rd.nextBoolean();
    }

    //создание бортового журнала при создании корабля
    public void createShipLog() throws IOException {
        logFilePath = Paths.get("src", "main", "resources", "spaceShipLogs", shipName + ".json");
        List<ShipLog> shipLogs = new ArrayList<>();
        new ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValue(logFilePath.toFile(), shipLogs);
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
        //считываем существующий бортовой журнал
        shipLogs =
                new ObjectMapper()
                        .readValue(logFilePath.toFile(), new TypeReference<List<ShipLog>>() {
                        });
        //обновляем бортовой журнал
        shipLogs.add(shipLog);
        new ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValue(logFilePath.toFile(), shipLogs);
    }
}
