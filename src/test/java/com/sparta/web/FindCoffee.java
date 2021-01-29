package com.sparta.web;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static com.codeborne.selenide.Selenide.*;

public class FindCoffee {

    String link;

    @Parameters({"link"})
    @BeforeClass
    public void setUp(String link) {
        this.link = link;
    }

    @BeforeMethod
    public void openLink() {
        open(link);
    }

    @Test(dataProvider = "getInputCoffee")
    public void findCoffeeLavazza1(InputCoffee inputCoffee) {
        $x("//div[@class='main_menu__inner']//child::li[@id='main_menu__search']").click();
        $(By.id("searchtext")).setValue(inputCoffee.getCoffeeName()).pressEnter();
        String xPath = "//div[@class='title']/a[contains(text(),'" + inputCoffee.getCoffeeName() + "')]/.." +
                "//following-sibling::div[@class='prices__wrapper']//div[@class='price_byn']/div[@class='price']";
        SelenideElement sElement = $x(xPath);
        Assert.assertTrue(sElement.exists(), "Item not found");
        String returnedPrice = clearPrice(sElement.getText());
        Assert.assertEquals(
                Double.toString(2 * Double.parseDouble(returnedPrice)),
                inputCoffee.getPricePerKilo(),
                "Price for 1kg does not match");
    }

    @Test(dataProvider = "getInputCoffee")
    public void findCoffeeLavazza2(InputCoffee inputCoffee) {
        $x("//div[@class='main_menu__inner']//child::li[@id='main_menu__search']").click();
        $(By.id("searchtext")).setValue(inputCoffee.getCoffeeName()).pressEnter();
        Assert.assertTrue(
                $x("//a[contains(text(),'" + inputCoffee.getCoffeeName() + "')]").isDisplayed(),
                "Item not found");
        $x("//a[contains(text(),'" + inputCoffee.getCoffeeName() + "')]").click();
        Assert.assertEquals(
                $x("//ul[@class='description']/li[last()]/span").getText(),
                inputCoffee.getPricePerKilo(),
                "Price for 1kg does not match");
    }

    @Test(dataProvider = "getInputCoffee")
    public void findCoffeeLavazza3(InputCoffee inputCoffee) {
        $x("//div[@class='main_menu__inner']//child::li[@id='main_menu__search']").click();
        $(By.id("searchtext")).setValue(inputCoffee.getCoffeeName()).pressEnter();
        inputCoffee.setCoffeeName(clearString(inputCoffee.getCoffeeName()));
        ElementsCollection elements = $$(By.xpath("//a[@class='to_favorite fa fa-heart ']//following-sibling::div[@class='title']"));
        boolean found = false;
        for (SelenideElement element : elements) {
            String nameFromSite = clearString(element.getText());
            if (nameFromSite.contains(inputCoffee.getCoffeeName())) {
                found = true;
                element.click();
                Assert.assertEquals(
                        $x("//ul[@class='description']/li[last()]/span").getText(),
                        inputCoffee.getPricePerKilo(),
                        "Price for 1kg does not match");
                break;
            }
        }
        if (!found) {
            Assert.fail("Item not found");
        }
    }

    @DataProvider
    public Object[][] getInputCoffee() throws IOException {
        List<InputCoffee> inputCoffees = new ObjectMapper()
                .readValue(
                        Paths.get("src", "test", "resources", "findCoffeeInput.json").toFile(),
                        new TypeReference<List<InputCoffee>>() {
                        });
        Object[][] inputData = new Object[inputCoffees.size()][1];
        for (int i = 0; i < inputCoffees.size(); i++) {
            inputData[i][0] = inputCoffees.get(i);
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

    public String clearPrice(String string) {
        return string
                .replaceAll("\n.*", "")
                .replaceAll("[а-яА-Я]", "")
                .replaceAll(".$", "");
    }
}