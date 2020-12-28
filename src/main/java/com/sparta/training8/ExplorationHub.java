package com.sparta.training8;

import lombok.Getter;
import lombok.Setter;

import java.util.Random;

@Getter
@Setter

public abstract class ExplorationHub {

    double atmosphereExplRate; //сколько тыс кв км исследует корабль за ед. времени (ч)
    double atmosphereExplCoef = 0.01;
    int atmosphereMinRequiredDays;
    double atmosphereMinSpentDays;
    double waterExplRate;
    double waterExplCoef = 0.3;
    int waterMinRequiredDays;
    double waterMinSpentDays;
    double lifeExplCoef = 0.5;
    double lifeExplRate;
    int lifeMinRequiredDays;
    double lifeMinSpentDays;
    double oilExplRate;
    double oilExplCoef = 0.6;
    int oilMinRequiredDays;
    double gasExplRate;
    double gasExplCoef = 0.77;
    int gasMinRequiredDays;
    double mineralsExplRate;
    double mineralsExplCoef = 0.8;
    int mineralsMinRequiredDays;
    double oreExplRate;
    double oreExplCoef = 0.88;
    int oreMinRequiredDays;

    //Расчет площади повехности сферы = PI*D^2
    public double getSurfaceArea(CelestialObject celestialObject) {
        return Math.PI * Math.pow(celestialObject.getDiameter(), 2);
    }

    //Поиск атмосферы. Результат исследования становиться известен, если просканировано >= 1% поверхности
    public void atmosphereExploration(CelestialObject celestialObject, int durationDays) {
        double surfaceArea = getSurfaceArea(celestialObject);
        if (atmosphereExplRate * 1000 * durationDays * 24 >= surfaceArea * atmosphereExplCoef) {
            boolean b = giveBooleanRandom();
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
    public void waterExploration(CelestialObject celestialObject, int durationDays) {
        double surfaceArea = getSurfaceArea(celestialObject);
        if (waterExplRate * 1000 * durationDays * 24 >= surfaceArea * waterExplCoef) {
            boolean b = giveBooleanRandom();
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
    public void lifeExploration(CelestialObject celestialObject, int durationDays) {
        double surfaceArea = getSurfaceArea(celestialObject);
        if (lifeExplRate * 1000 * durationDays * 24 >= surfaceArea * lifeExplCoef) {
            boolean b = giveBooleanRandom();
            if (b && celestialObject.getWater().contains("rue")) {
                celestialObject.setLife("true");
            } else {
                celestialObject.setLife("false");
            }
        } else {
            lifeMinRequiredDays = (int) ((surfaceArea * lifeExplCoef) / (lifeExplRate * 1000 * 24)) + 1;
        }
    }

    //Поиск нефти. Результат исследования становиться известен, если просканировано >= 60% поверхности
    public void oilExploration(CelestialObject celestialObject, int durationDays) {
        double surfaceArea = getSurfaceArea(celestialObject);
        if (oilExplRate * 1000 * durationDays * 24 >= surfaceArea * oilExplCoef) {
            boolean b = giveBooleanRandom();
            if (b) {
                celestialObject.setOil("true");
            } else {
                celestialObject.setOil("false");
            }
        } else {
            oilMinRequiredDays = (int) ((surfaceArea * oilExplCoef) / (oilExplRate * 1000 * 24)) + 1;
        }
    }

    //Поиск газа. Результат исследования становиться известен, если просканировано >= 77% поверхности
    public void gasExploration(CelestialObject celestialObject, int durationDays) {
        double surfaceArea = getSurfaceArea(celestialObject);
        if (gasExplRate * 1000 * durationDays * 24 >= surfaceArea * gasExplCoef) {
            boolean b = giveBooleanRandom();
            if (b) {
                celestialObject.setGas("true");
            } else {
                celestialObject.setGas("false");
            }
        } else {
            gasMinRequiredDays = (int) ((surfaceArea * gasExplCoef) / (gasExplRate * 1000 * 24)) + 1;
        }
    }

    //Поиск полезных ископаемых. Результат исследования становиться известен, если просканировано >= 80% поверхности
    public void mineralsExploration(CelestialObject celestialObject, int durationDays) {
        double surfaceArea = getSurfaceArea(celestialObject);
        if (mineralsExplRate * 1000 * durationDays * 24 >= surfaceArea * mineralsExplCoef) {
            boolean b = giveBooleanRandom();
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
    public void oreExploration(CelestialObject celestialObject, int durationDays) {
        double surfaceArea = getSurfaceArea(celestialObject);
        if (oreExplRate * 1000 * durationDays * 24 >= surfaceArea * oreExplCoef) {
            boolean b = giveBooleanRandom();
            if (b && celestialObject.getMinerals().contains("rue")) {
                celestialObject.setOre("true");
            } else {
                celestialObject.setOre("false");
            }
        } else {
            oreMinRequiredDays = (int) ((surfaceArea * oreExplCoef) / (oreExplRate * 1000 * 24)) + 1;
        }
    }

    public boolean giveBooleanRandom() {
        Random rd = new Random();
        return rd.nextBoolean();
    }
}
