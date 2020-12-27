package com.sparta.training5;

import java.util.*;

public class Street {

    protected String streetName;
    protected double totalTax;

    public Street(String streetName) {
        this.streetName = streetName;
    }

    public static void main(String[] args) {

        //ул Ленина
        Street leninaStreet = new Street("Ленина");
        List<House> leninaStreetArray = new ArrayList<>();
        leninaStreetArray.add(new Cottage(140.24));
        leninaStreetArray.add(new ApartmentHouse(37.2, "ProjectA"));
        leninaStreetArray.add(new ApartmentHouse(64.98, "ProjectB"));
        leninaStreetArray.add(new ApartmentHouse(23.423, "ProjectC"));
        leninaStreetArray.add(new ApartmentHouse(10, "ProjectD"));
        leninaStreetArray.add(new Townhouse(120.65, 2));
        leninaStreetArray.add(new Cottage(404.21));
        leninaStreet.giveMeStreetMoney(leninaStreetArray);

        //ул Пушкина
        Street pushkinaStreet = new Street("Пушкина");
        List<House> pushkinaStreetArray = new ArrayList<>();
        pushkinaStreetArray.add(new Cottage(100));
        pushkinaStreetArray.add(new Townhouse(120, 2));
        pushkinaStreetArray.add(new Townhouse(360, 3));
        pushkinaStreetArray.add(new Cottage(200));
        pushkinaStreetArray.add(new Cottage(120));
        pushkinaStreet.giveMeStreetMoney(pushkinaStreetArray);
    }

    //метод подсчета квартплаты
    public void giveMeStreetMoney(List<House> housesArray) {
        for (House house : housesArray) {
            totalTax += house.giveMeMyMoney();
        }
        System.out.format("Общая сумма, которую должны на улице '" + streetName + "' равна $" + "%,.2f%n", totalTax);
    }
}