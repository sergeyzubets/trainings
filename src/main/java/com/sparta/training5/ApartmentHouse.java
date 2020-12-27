package com.sparta.training5;

public class ApartmentHouse extends House {

    protected String apartmentType;
    protected int entranceCount;                // кол-во подъездов в многоквартирном доме
    protected int floorCount;                   // кол-во этажей в многоквартирном доме
    protected int apartmentsCount;              // кол-во кваритир на этаже
    protected double oneRoomFlatArea;           // площадь 1-комнатных квартир
    protected double twoRoomFlatArea;           // площадь 2х-комнатных квартир
    protected double threeRoomFlatArea;         // площадь 3х-комнатных квартир
    protected double fourRoomFlatArea;          // площадь 4х-комнатных квартир
    protected int oneRoomFlatCount;             // кол-во 1-комнатных квартир
    protected int twoRoomFlatCount;             // кол-во 2x-комнатных квартир
    protected int threeRoomFlatCount;           // кол-во 3х-комнатных квартир
    protected int fourRoomFlatCount;            // кол-во 4х-комнатных квартир

    public ApartmentHouse(double houseArea, String apartmentType) {
        // ProjectA: 1 одноподъездный 18 этажный дом, по 4 квартиры на этаже (1шка, 2шка, 3шка и 4комн.), площадь квартир отличается от заданной
        // ProjectB: 3 одноподъездный 9 этажный дом, по 4 квартиры на этаже (1шка, две 2шки, 3шка), площадь квартир отличается от заданной
        // ProjectC: 1 одноподъездный 10 этажный дом, по 2 4хкомнт. квартиры на этаже, площадь квартир отличается от заданной
        // ProjectD: 1 одноподъездная 9 этажная малосемейка, по 18 квартир дефолтной площади на этаже
        super(houseArea);
        this.apartmentType = apartmentType;
        switch (apartmentType) {
            case "ProjectA":
                entranceCount = 1;
                floorCount = 18;
                apartmentsCount = 4;
                oneRoomFlatArea = 32.97;
                twoRoomFlatArea = 55.53;
                threeRoomFlatArea = 79.06;
                fourRoomFlatArea = 94.55;
                oneRoomFlatCount = entranceCount * floorCount * apartmentsCount / 4;
                twoRoomFlatCount = entranceCount * floorCount * apartmentsCount / 4;
                threeRoomFlatCount = entranceCount * floorCount * apartmentsCount / 4;
                fourRoomFlatCount = entranceCount * floorCount * apartmentsCount / 4;
                break;
            case "ProjectB":
                entranceCount = 3;
                floorCount = 9;
                apartmentsCount = 4;
                oneRoomFlatArea = 35.87;
                twoRoomFlatArea = 55.03;
                threeRoomFlatArea = 77.42;
                oneRoomFlatCount = entranceCount * floorCount * apartmentsCount / 4;
                twoRoomFlatCount = entranceCount * floorCount * apartmentsCount / 2;
                threeRoomFlatCount = entranceCount * floorCount * apartmentsCount / 4;
                fourRoomFlatCount = 0;
                break;
            case "ProjectC":
                entranceCount = 1;
                floorCount = 10;
                apartmentsCount = 2;
                fourRoomFlatArea = 109.25;
                oneRoomFlatCount = 0;
                twoRoomFlatCount = 0;
                threeRoomFlatCount = 0;
                fourRoomFlatCount = entranceCount * floorCount * apartmentsCount;
                break;
            case "ProjectD":
                entranceCount = 1;
                floorCount = 9;
                apartmentsCount = 18;
                break;
            default:
                System.out.println("Указанный тип не существует");
                break;
        }
    }

    @Override
    //метод подсчета квартплаты
    public double giveMeMyMoney() {
        int i;
        // перебираем все однушки
        for (i = 0; i < oneRoomFlatCount; i++) {
            tax += registeredPplNumberRandom() * oneRoomFlatArea * 10;
        }
        // перебираем все двушки
        for (i = 0; i < twoRoomFlatCount; i++) {
            tax += registeredPplNumberRandom() * twoRoomFlatArea * 10;
        }
        // перебираем все трешки
        for (i = 0; i < threeRoomFlatCount; i++) {
            tax += registeredPplNumberRandom() * threeRoomFlatArea * 10;
        }
        // перебираем все 4ки
        for (i = 0; i < fourRoomFlatCount; i++) {
            tax += registeredPplNumberRandom() * fourRoomFlatArea * 10;
        }
        return tax;
    }
}