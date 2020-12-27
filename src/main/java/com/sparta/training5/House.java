package com.sparta.training5;

public class House {
    protected double houseArea;
    protected int registeredPplNumber;
    protected int iMin = 1;   //нижняя граница (включительно) генератора прописанных человек
    protected int iMax = 10;                //верхняя граница (не включительно) генератора прописанных человек
    protected double tax;

    public House(double houseArea) {
        this.houseArea = houseArea;
    }

    //метод подсчета квартплаты
    public double giveMeMyMoney() {
        tax += registeredPplNumberRandom() * houseArea * 10;
        return tax;
    }

    public int registeredPplNumberRandom() {
        return this.registeredPplNumber = (int) (Math.random() * (iMax - iMin) + iMin);
    }

}