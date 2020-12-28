package com.sparta.training8;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Getter
@Setter

public class ChinaSpaceShip extends SpaceShip {

    public ChinaSpaceShip(String shipName, double atmosphereExplRate, double waterExplRate, double lifeExplRate, double mineralsExplRate, double oreExplRate) throws IOException {
        super(shipName, atmosphereExplRate, waterExplRate, lifeExplRate);
        this.mineralsExplRate = mineralsExplRate;
        this.oreExplRate = oreExplRate;
    }

    @Override
    public void exploration(CelestialObject celestialObject, int durationDays, List<CelestialObject> celestialObjectsArray, Path filePath) throws IOException {
        mineralsExploration(celestialObject, durationDays);
        oreExploration(celestialObject, durationDays);
        super.exploration(celestialObject, durationDays, celestialObjectsArray, filePath);
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
