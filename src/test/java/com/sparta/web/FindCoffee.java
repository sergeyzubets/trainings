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
        String price = $x("//div[@class='price_byn']/div[@class='price']").getText()
                .replaceAll("[а-яА-Я]", "")
                .replaceAll(".$", "");
        Assert.assertEquals(
                2 * Double.parseDouble(price),
                pricePerKilo,
                "Цена за 1кг не совпадает");
    }

    @Test
    public void FindCoffeeLavazza2() {
        String coffeeLavazza500 = "«Lavazza» qualita oro, 500";
        open("https://e-dostavka.by/");
        $x("//div[@class='main_menu__inner']//child::li[@id='main_menu__search']").click();
        $x("//input[@id='searchtext']").setValue(coffeeLavazza500).pressEnter();
        $x("//a[contains(text(),'" + coffeeLavazza500 + "')]").click();
        Assert.assertEquals(
                $x("//ul[@class='description']/li[last()]/span").getText(),
                "64.98",
                "Цена за 1кг не совпадает");
    }
}