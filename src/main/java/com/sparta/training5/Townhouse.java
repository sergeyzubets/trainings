package com.sparta.training5;

public class Townhouse extends House {

    protected int apartmentsCount;                   // кол-во отдельных квартир в таунхаусе

    public Townhouse(double houseArea, int apartmentsCount) {
        super(houseArea);
        this.apartmentsCount = apartmentsCount;
    }

    @Override
    //метод подсчета квартплаты
    public double giveMeMyMoney(){
        tax += registeredPplNumberRandom() * houseArea * 10 * apartmentsCount;
        return tax;
    }
}


