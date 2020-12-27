package com.sparta.training6;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Getter
@Setter

public class ChinaSpaceShip extends SpaceShip {

    private double mineralsExplRate;
    private double mineralsExplCoef = 0.8;
    private int mineralsMinRequiredDays;
    private double oreExplRate;
    private double oreExplCoef = 0.88;
    private int oreMinRequiredDays;

    public ChinaSpaceShip(String shipName, double atmosphereExplRate, double waterExplRate, double lifeExplRate, double mineralsExplRate, double oreExplRate) throws IOException {
        super(shipName, atmosphereExplRate, waterExplRate, lifeExplRate);
        this.mineralsExplRate = mineralsExplRate;
        this.oreExplRate = oreExplRate;
    }

    @Override
    public void exploration(CelestialObject celestialObject, int durationDays, List<CelestialObject> celestialObjectArray, Path filePath) throws IOException {
        //вычисляем площадь повехности шара = PI*D^2
        double surfaceArea = Math.PI * Math.pow(celestialObject.getDiameter(), 2);
        // поиск полезных ископаемых
        mineralsExploration(celestialObject, durationDays, surfaceArea);
        // поиск руды
        oreExploration(celestialObject, durationDays, surfaceArea);
        super.exploration(celestialObject, durationDays, celestialObjectArray, filePath);
    }

    //Поиск полезных ископаемых. Результат исследования становиться известен, если просканировано >= 80% поверхности
    public void mineralsExploration(CelestialObject celestialObject, int durationDays, double surfaceArea) {
        if (mineralsExplRate * 1000 * durationDays * 24 >= surfaceArea * mineralsExplCoef) {
            boolean b = giveMeBooleanRandom();
            if (b) {
                celestialObject.setMinerals("true");
            } else {
                celestialObject.setMinerals("false");
            }
        } else {
            mineralsMinRequiredDays = (int) ((surfaceArea * mineralsExplCoef) / (mineralsExplRate * 1000 * 24)) + 1;
        }
    }

    //Поиск руды. Результат исследования становиться известен, если просканировано >= 88% поверхности И найдены полезные ископаемые
    public void oreExploration(CelestialObject celestialObject, int durationDays, double surfaceArea) {
        if (oreExplRate * 1000 * durationDays * 24 >= surfaceArea * oreExplCoef) {
            boolean b = giveMeBooleanRandom();
            if (b && celestialObject.getMinerals().contains("rue")) {
                celestialObject.setOre("true");
            } else {
                celestialObject.setOre("false");
            }
        } else {
            oreMinRequiredDays = (int) ((surfaceArea * oreExplCoef) / (oreExplRate * 1000 * 24)) + 1;
        }
    }

    @Override
    public void updateShipLog(CelestialObject celestialObject, int durationDays) throws IOException {
        if (durationDays >= mineralsMinRequiredDays) {
            shipLog.setMinerals(celestialObject.getMinerals());
        } else
            shipLog.setMinerals("Полезные ископаемые исследовать не успели, не хватило " + (mineralsMinRequiredDays - durationDays) + " дня(ей).");
        if (durationDays >= oreMinRequiredDays) {
            shipLog.setOre(celestialObject.getOre());
        } else
            shipLog.setOre("Руду исследовать не успели, не хватило " + (oreMinRequiredDays - durationDays) + " дня(ей).");
        super.updateShipLog(celestialObject, durationDays);
    }
}
