package com.sparta.training8;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Getter
@Setter

public class SpaceShuttle extends SpaceShip {

    private double oilExplRate;
    private double oilExplCoef = 0.6;
    private int oilMinRequiredDays;
    private double gasExplRate;
    private double gasExplCoef = 0.77;
    private int gasMinRequiredDays;

    public SpaceShuttle(String shipName, double atmosphereExplRate, double waterExplRate, double lifeExplRate, double oilExplRate, double gasExplRate) throws IOException {
        super(shipName, atmosphereExplRate, waterExplRate, lifeExplRate);
        this.oilExplRate = oilExplRate;
        this.gasExplRate = gasExplRate;

    }

    @Override
    public void exploration(CelestialObject celestialObject, int durationDays, List<CelestialObject> celestialObjectArray, Path filePath) throws IOException {
        //вычисляем площадь повехности шара = PI*D^2
        double surfaceArea = Math.PI * Math.pow(celestialObject.getDiameter(), 2);
        // поиск нефти
        oilExploration(celestialObject, durationDays, surfaceArea);
        // поиск газа
        gasExploration(celestialObject, durationDays, surfaceArea);
        super.exploration(celestialObject, durationDays, celestialObjectArray, filePath);
    }

    //Поиск нефти. Результат исследования становиться известен, если просканировано >= 60% поверхности
    public void oilExploration(CelestialObject celestialObject, int durationDays, double surfaceArea) {
        if (oilExplRate * 1000 * durationDays * 24 >= surfaceArea * oilExplCoef) {
            boolean b = giveMeBooleanRandom();
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
    public void gasExploration(CelestialObject celestialObject, int durationDays, double surfaceArea) {
        if (gasExplRate * 1000 * durationDays * 24 >= surfaceArea * gasExplCoef) {
            boolean b = giveMeBooleanRandom();
            if (b) {
                celestialObject.setGas("true");
            } else {
                celestialObject.setGas("false");
            }
        } else {
            gasMinRequiredDays = (int) ((surfaceArea * gasExplCoef) / (gasExplRate * 1000 * 24)) + 1;
        }
    }

    @Override
    public void updateShipLog(CelestialObject celestialObject, int durationDays) throws IOException {
        if (durationDays >= oilMinRequiredDays) {
            shipLog.setOil(celestialObject.getOil());
        } else
            shipLog.setOil("Нефть исследовать не успели, не хватило " + (oilMinRequiredDays - durationDays) + " дня(ей).");
        if (durationDays >= gasMinRequiredDays) {
            shipLog.setGas(celestialObject.getGas());
        } else
            shipLog.setGas("Газ исследовать не успели, не хватило " + (gasMinRequiredDays - durationDays) + " дня(ей).");
        super.updateShipLog(celestialObject, durationDays);
    }
}
