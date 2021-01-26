package com.sparta.web;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;

import javax.xml.transform.sax.SAXResult;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
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

    @Test(dataProvider = "getInputCoffee")
    public void FindCoffeeLavazza3(String coffeeName, String pricePerKilo) {
        open(link);
        $x("//div[@class='main_menu__inner']//child::li[@id='main_menu__search']").click();
        $x("//input[@id='searchtext']").setValue(coffeeName).pressEnter();
        String xPath = "//a[@class='to_favorite fa fa-heart ']//following-sibling::div[@class='title']";
        coffeeName = clearString(coffeeName);
        ElementsCollection elements = $$(By.xpath(xPath));
        for (int i = 0; i < elements.size(); i++) {
            SelenideElement element = elements.get(i);
            String nameFromSite = clearString(element.getText());
            if (nameFromSite.contains(coffeeName)) {
                element.click();
                Assert.assertEquals(
                        $x("//ul[@class='description']/li[last()]/span").getText(),
                        pricePerKilo,
                        "Цена за 1кг не совпадает");
                break;
            }
            if (i == elements.size() - 1) {
                Assert.fail("Элемент на найден");
            }
        }
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

    public String clearString(String string) {
        return string
                .toLowerCase()
                .replaceAll("[\\'\"«»]", "")
                .replaceAll("а", "a")
                .replaceAll("е", "e")
                .replaceAll("о", "o")
                .replaceAll("у", "y")
                .replaceAll("с", "c")
                .replaceAll("р", "p")
                .replaceAll("х", "x");
    }
}