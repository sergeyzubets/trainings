package com.sparta.web;

import org.testng.Assert;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.*;

public class FindCoffee {

    @Test
    public void FindCoffeeLavazza1() {
        double pricePerKilo = 64.98;
        open("https://e-dostavka.by/");
        $x("//div[@class='main_menu__inner']//child::li[@id='main_menu__search']").click();
        $x("//input[@id='searchtext']").setValue("Lavazza qualita oro 500").pressEnter();
        String price = $x("//div[@class='price']").getText().replaceAll("[а-яА-Я]", "").replaceAll(".$", "");
        Assert.assertEquals(2 * Double.parseDouble(price), pricePerKilo, "Цена за 1кг не совпадает");
    }

    @Test
    public void FindCoffeeLavazza2() {
        String pricePerHalfKilo = "32р.49к."; // pricePerKilo / 2 = 64р.98к. / 2 = 32р.49к.
        open("https://e-dostavka.by/");
        $x("//div[@class='main_menu__inner']//child::li[@id='main_menu__search']").click();
        $x("//input[@id='searchtext']").setValue("Lavazza qualita oro 500").pressEnter();
        String price = $x("//div[@class='price']").getText();
        Assert.assertEquals(price, pricePerHalfKilo, "Цена за 1кг не совпадает");
    }
}