package com.sparta.training8;

import java.util.ArrayList;
import java.util.List;

public class Route {

    String name;
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
