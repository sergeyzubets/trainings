package com.sparta.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static com.codeborne.selenide.Selenide.*;

public class FindCoffee {

    String link;

    @Parameters({"link"})
    @BeforeMethod
    public void setUp(String link) {
        this.link = link;
    }

    @Test(dataProvider = "getInputCoffee")
    public void FindCoffeeLavazza1(String coffeeName, String pricePerKilo) {
        open(link);
        $x("//div[@class='main_menu__inner']//child::li[@id='main_menu__search']").click();
        $x("//input[@id='searchtext']").setValue(coffeeName).pressEnter();
        String xPath = "//div[@class='title']/a[contains(text(),'" + coffeeName + "')]/.." +
                "//following-sibling::div[@class='prices__wrapper']//div[@class='price_byn']/div[@class='price']";
        Assert.assertTrue($x(xPath).exists(), "Элемент на найден");
        String returnedPrice = $x(xPath).getText()
                .replaceAll("\n.*", "")
                .replaceAll("[а-яА-Я]", "")
                .replaceAll(".$", "");
        Assert.assertEquals(
                Double.toString(2 * Double.parseDouble(returnedPrice)),
                pricePerKilo,
                "Цена за 1кг не совпадает");
    }

    @Test(dataProvider = "getInputCoffee")
    public void FindCoffeeLavazza2(String coffeeName, String pricePerKilo) {
        open(link);
        $x("//div[@class='main_menu__inner']//child::li[@id='main_menu__search']").click();
        $x("//input[@id='searchtext']").setValue(coffeeName).pressEnter();
        Assert.assertTrue(
                $x("//a[contains(text(),'" + coffeeName + "')]").isDisplayed(),
                "Элемент на найден"
        );
        $x("//a[contains(text(),'" + coffeeName + "')]").click();
        Assert.assertEquals(
                $x("//ul[@class='description']/li[last()]/span").getText(),
                pricePerKilo,
                "Цена за 1кг не совпадает");
    }

    @DataProvider
    public Object[][] getInputCoffee() throws IOException {
        List<InputCoffee> inputCoffees = new ObjectMapper()
                .readValue(
                        Paths.get("src", "test", "resources", "findCoffeeInput.json").toFile(),
                        new TypeReference<List<InputCoffee>>() {
                        });
        Object[][] inputData = new Object[inputCoffees.size()][2];
        for (int i = 0; i < inputCoffees.size(); i++) {
            inputData[i][0] = inputCoffees.get(i).coffeeName;
            inputData[i][1] = inputCoffees.get(i).pricePerKilo;
        }
        return inputData;
    }
}