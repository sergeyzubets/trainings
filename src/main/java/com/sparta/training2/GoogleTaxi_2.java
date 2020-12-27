package com.sparta.training2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GoogleTaxi_2 {

    double currentFuelLevel;    // variable for storing the current amount of fuel in the tank
    int fuelTankCapacity;       // variable for storing the capacity of the fuel tank

    public GoogleTaxi_2(double currentFuelLevel, int fuelTankCapacity) {
        this.currentFuelLevel = currentFuelLevel;
        this.fuelTankCapacity = fuelTankCapacity;
    }

    public static void main(String[] args) {

        GoogleTaxi_2 car1 = new GoogleTaxi_2(10, 50);    // new car creation

        /* Start route creation */
        List<String> destination = new ArrayList<>();
        System.out.println("Where do we drive? Please enter the destinations through a Enter or 0 to finish.");
        /** the Scanner writes input values to the array, which are separated by a newline character
        The value 0 is used to complete the input **/
        Scanner newOrder = new Scanner(System.in);
        for (String input = "";!input.equals("0");) {
            newOrder.useDelimiter("\n");
            input = newOrder.next();
            destination.add(input);
        }
        newOrder.close();
        destination.remove(destination.size()-1);
        System.out.println("Route built: "+destination);
        System.out.println("The amount of fuel in the tank "+car1.currentFuelLevel+" l");
        System.out.println("----");
        /* End route creation */

        double totalFuelspent = 0;  //variable for storing total fuel uses
        for (int i = 0; i < destination.size(); i++) {
            String di = destination.get(i);
            int fuelRequired = di.length();
            totalFuelspent = totalFuelspent + fuelRequired;

            if (car1.currentFuelLevel >= fuelRequired) {
                car1.currentFuelLevel = car1.drive(fuelRequired, car1.currentFuelLevel, di);
            } else {
                double fuelShortage = fuelRequired-car1.currentFuelLevel;
                car1.currentFuelLevel = car1.fillUpMyCar(car1.currentFuelLevel, car1.fuelTankCapacity, fuelShortage);
                car1.currentFuelLevel = car1.drive(fuelRequired, car1.currentFuelLevel, destination.get(i));
            }
        }
        System.out.println("The trip to the "+destination+" is completed. Total fuel spent = "+totalFuelspent+" l");
    }

    /**
     *
     * @param destination
     * @return
     */

    /** Drive method.
    Inputs:
        double fuelRequired - how many liters required for the trip (l)
        double currentFuelLevel - how many liters we have (l)
        String destination - name of i-destination target. Used in System.out.println() only
    Outputs:
        currentFuelLevel - updated currentFuelLevel value after the trip (l)
    **/
    public static double drive(double fuelRequired, double currentFuelLevel, String destination) {
        currentFuelLevel = currentFuelLevel - fuelRequired;
        System.out.format("Method Drive. Going to "+destination+". "+fuelRequired+" liters are required. Liters of fuel after the trip = "+"%.3f%n", currentFuelLevel);
        System.out.println("----");
        return currentFuelLevel;
    }

    /** Fill up a car method.
    Inputs:
        double currentFuelLevel - how many liters we have (l)
        int fuelTankCapacity - max of int fuel tank capacity (l). Used for validation of exceeds tank capacity
        double fuelShortage - how many liters required for the trip (l)
    Outputs:
        currentFuelLevel - updated currentFuelLevel value after the refueling (l)
    Uses GetRandomLiter method for random fuel amount generation. The generated amount is added to the current.
    Validation:
        There is a check for the ratio of fuel level after refueling and required for the trip.
        If refueling amount is not enough - GetRandomLiter method is called repeatedly **/
    public double fillUpMyCar(double currentFuelLevel, int fuelTankCapacity, double fuelShortage) {
        do {
            currentFuelLevel = getRandomLiter(currentFuelLevel, fuelTankCapacity);
        }
        while (currentFuelLevel < fuelShortage);
        System.out.format("Method FillUpMyCar. "+fuelShortage+" liters of fuel are required. Liters of fuel after filling up the car = "+"%.3f%n", currentFuelLevel);
        return currentFuelLevel;
    }

    /** The method for random fuel amount generation.
    Inputs:
        double currentFuelLevel - how many liters we have (l)
        int fuelTankCapacity - max of int fuel tank capacity (l). Used fuel in generation
    Attributes:
        double currentFuelLevelBackUp - back up of original fuel level value (l)
        Math.random() is used for generation of values in [0:1).
        Multiplying the Math.random() results by the tank volume increases result values (from [0:1) to [0:X), where X = fuelTankCapacity
    Outputs:
        currentFuelLevel - updated currentFuelLevel value after the generation (l)
    Validation:
        There is a check for the ratio of fuel level after updating and max of fuel tank capacity.
        If refueling amount exceeds tank capacity - currentFuelLevel is reset to the original value and Math.random() is called repeatedly **/
    public static double getRandomLiter(double currentFuelLevel, int fuelTankCapacity) {
        double currentFuelLevelBackUp = currentFuelLevel;
        currentFuelLevel = currentFuelLevel + Math.random()*fuelTankCapacity;
        while (currentFuelLevel > fuelTankCapacity) {
            currentFuelLevel = currentFuelLevelBackUp;
            currentFuelLevel = currentFuelLevel + Math.random()*fuelTankCapacity;
        }
        return currentFuelLevel;
    }
}