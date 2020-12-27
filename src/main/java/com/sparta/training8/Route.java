package com.sparta.training8;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor

public class Route extends CelestialObject {

    protected String name;
    List<CelestialObject> routeTargetCelestials = new ArrayList<>();
    List<Integer> routeTargetDurationDays = new ArrayList<>();

    public Route(String name) {
        this.name = name;
    }

    public void addTarget(CelestialObject celestialObject, int durationDays) {
        routeTargetCelestials.add(celestialObject);
        routeTargetDurationDays.add(durationDays);
    }
}
