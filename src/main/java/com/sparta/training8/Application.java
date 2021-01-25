package com.sparta.training8;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Application {

    public static void main(String[] args) throws IOException {

        Path filePath = Paths.get("src", "main", "resources", "galaxyMap.json");
        List<CelestialObject> celestialObjects = new ObjectMapper().readValue(filePath.toFile(), new TypeReference<List<CelestialObject>>() {
        });

        CelestialObject earth = new CelestialObject();
        earth = earth.findObjectsByName(celestialObjects, "Earth");

        //Создаем корабли
        SpaceShip spaceShuttle1 = new SpaceShuttle("Space Shuttle 1", 7, 2, 3, 4, 6);
        SpaceShip buran1 = new Buran("Buran 1", 2, 20, 20, 12, 22, 10, 11);
        SpaceShip shenzhou1 = new ChinaSpaceShip("Shenzhou 1", 13, 15, 13, 14.5, 15.5);
        SpaceShip euro1 = new EuropeanSpaceShip("EuropeanSpaceShip 1", 2, 1, 1, 22, 33);

        CelestialObject mars = new CelestialObject();
        mars = mars.findObjectsByName(celestialObjects, "Mars");
        spaceShuttle1.exploration(mars, 1000, celestialObjects, filePath);

//        CelestialObject jupiter = new CelestialObject();
//        jupiter.setName("Jupiter");
//        celestialObjects.add(jupiter);
//        jupiter.setParentObject("Solar");
//        jupiter.setType("planet");
//        jupiter.setDiameter(139820);
//        jupiter.setSystemName("Solar System");

        Route route1 = new Route("В поисках воды");
        CelestialObject venus = new CelestialObject();
        venus = venus.findObjectsByName(celestialObjects, "Venus");
        route1.addTarget(mars, 20);
        route1.addTarget(venus, 122);
        route1.addTarget(mars, 20000);
//        buran1.explorationAll(route1.routeTargetCelestials, route1.routeTargetDurationDays, celestialObjects, filePath);

        Route route2 = new Route("Спутники Сатурна");
        CelestialObject saturn = new CelestialObject();
        List<CelestialObject> saturnMoons = saturn.findAllMoons(celestialObjects, "Saturn");
        for (CelestialObject iCelestialObject : saturnMoons) {
            route2.addTarget(iCelestialObject, 33);
        }
//        shenzhou1.explorationAll(route2.routeTargetCelestials, route2.routeTargetDurationDays, celestialObjects, filePath);

        Route route3 = new Route("В поисках жизни"); //Марс, Титан, Ганимед
        CelestialObject titan = new CelestialObject();
        titan = titan.findObjectsByName(celestialObjects, "Titan");
        CelestialObject prometheus = new CelestialObject();
        prometheus = prometheus.findObjectsByName(celestialObjects, "Prometheus");
        route3.addTarget(mars, 180);
        route3.addTarget(titan, 120);
        route3.addTarget(prometheus, 120);
//        euro1.explorationAll(route3.routeTargetCelestials, route3.routeTargetDurationDays, celestialObjects, filePath);

        Route route4 = new Route("поиск по всем объектам Солнечной системы");
        List<CelestialObject> solarSystemObjects = earth.findAllCelestialObjects(celestialObjects, "Solar system");
        for (CelestialObject iCelestialObject : solarSystemObjects) {
            route4.addTarget(iCelestialObject, 25);
        }
//        spaceShuttle1.explorationAll(route4.routeTargetCelestials, route4.routeTargetDurationDays, celestialObjects, filePath);

        Route route5 = new Route("поиск по всем планетам Солнечной системы");
        List<CelestialObject> solarSystemPlanets = earth.findAllPlanets(celestialObjects, "Solar system");
        for (CelestialObject iCelestialObject : solarSystemPlanets) {
            route5.addTarget(iCelestialObject, 33);
        }
//        spaceShuttle1.explorationAll(route5.routeTargetCelestials, route5.routeTargetDurationDays, celestialObjects, filePath);
    }
}
