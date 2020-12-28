package com.sparta.training8;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Getter
@Setter

public class SpaceShuttle extends SpaceShip {

    public SpaceShuttle(String shipName, double atmosphereExplRate, double waterExplRate, double lifeExplRate, double oilExplRate, double gasExplRate) throws IOException {
        super(shipName, atmosphereExplRate, waterExplRate, lifeExplRate);
        this.oilExplRate = oilExplRate;
        this.gasExplRate = gasExplRate;
    }

    @Override
    public void exploration(CelestialObject celestialObject, int durationDays, List<CelestialObject> celestialObjectsArray, Path filePath) throws IOException {
        oilExploration(celestialObject, durationDays);
        gasExploration(celestialObject, durationDays);
        super.exploration(celestialObject, durationDays, celestialObjectsArray, filePath);
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
