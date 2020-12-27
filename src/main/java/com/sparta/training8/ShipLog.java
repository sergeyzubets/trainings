package com.sparta.training8;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.nio.file.Path;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter

public class ShipLog {

    private String shipName;
    private String shipType;
    private double atmosphereExplRate;
    private double waterExplRate;
    private double lifeExplRate;
    private double atmosphereExplCoef;
    private double waterExplCoef;
    private double lifeExplCoef;
    private String targetName;
    private String atmosphere = "unknown";
    private String water = "unknown";
    private String life = "unknown";
    private String oil = "unknown";
    private String gas = "unknown";
    private String minerals = "unknown";
    private String ore = "unknown";
    private Path logFilePath;

}
