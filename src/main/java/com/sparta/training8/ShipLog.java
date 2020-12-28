package com.sparta.training8;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.nio.file.Path;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter

public class ShipLog {
    String shipName;
    String shipType;
    double atmosphereExplRate;
    double waterExplRate;
    double lifeExplRate;
    double atmosphereExplCoef;
    double waterExplCoef;
    double lifeExplCoef;
    String targetName;
    String atmosphere = "unknown";
    String water = "unknown";
    String life = "unknown";
    String oil = "unknown";
    String gas = "unknown";
    String minerals = "unknown";
    String ore = "unknown";
    Path logFilePath;
}
