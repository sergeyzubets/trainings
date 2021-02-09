package com.sparta.web.sendemail.pom;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.web.sendemail.TestData;
import lombok.Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static com.codeborne.selenide.Selenide.open;

@Data

public class POMTest {

    String url;
    int timeout;
    int sleepInterval;
    Wait<WebDriver> wait;

    @Parameters({"url"})
    @BeforeTest
    public void setUp(String url) {
        this.url = url;
        setTimeout(2);        //in mins
        setSleepInterval(5);  //in sec
        Configuration.startMaximized = true;
    }

    @BeforeMethod
    public void openTheLink() {
        open(getUrl());
        WebDriver webDriver = WebDriverRunner.getWebDriver();
        wait = new WebDriverWait(webDriver, getTimeout() * 60L, getSleepInterval() * 1000L);
    }

    @DataProvider
    public Object[][] getTestEmailInputs() throws IOException {
        List<TestData> testData = new ObjectMapper()
                .readValue(
                        Paths.get("src", "test", "resources", "testEmail/testEmailsInput.json").toFile(),
                        new TypeReference<List<TestData>>() {});
        Object[][] inputData = new Object[testData.size()][1];
        for (int i = 0; i < testData.size(); i++) {
            inputData[i][0] = testData.get(i);
        }
        return inputData;
    }

    @Test(dataProvider = "getTestEmailInputs")
    public void receiveEmailsVerification(TestData testData) {
        TutbyMailLoginPage.login(testData.getUser());
        TutbyMailMainPage.sendEmail(testData.getEmail());
        Assert.assertTrue(TutbyMailMainPage.isReceived(getWait(), testData.getEmail()), "The email was not received");
    }

    @AfterMethod
    public void logout() {
        TutbyMailMainPage.logout();
    }
}