package com.sparta.web.sendemail.pom;

import com.codeborne.selenide.Configuration;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.web.sendemail.TestData;
import lombok.Data;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static com.codeborne.selenide.Selenide.open;

@Data

public class POMTest {

    String url;
    int timeout;
    int sleepInterval;
    File file;
    TutbyMailLoginPage loginPage = new TutbyMailLoginPage();
    TutbyMailMainPage mainPage = new TutbyMailMainPage();

    @Parameters({"url"})
    @BeforeTest
    public void setUp(String url) {
        this.url = url;
        setTimeout(2);        //in mins
        setSleepInterval(5);  //in sec
        Configuration.startMaximized = true;
        file = Paths.get("src", "test", "resources", "testEmail/testEmailsInput.json").toFile();
    }

    @BeforeMethod
    public void openTheLink() {
        open(getUrl());
    }

    @DataProvider
    public Object[][] getTestEmailInputs() throws IOException {
        List<TestData> testData = new ObjectMapper()
                .readValue(getFile(), new TypeReference<List<TestData>>() {
                });
        Object[][] inputData = new Object[testData.size()][1];
        for (int i = 0; i < testData.size(); i++) {
            inputData[i][0] = testData.get(i);
        }
        return inputData;
    }

    @Test(dataProvider = "getTestEmailInputs")
    public void receiveEmailsVerification(TestData testData) {
        loginPage.login(testData.getUser());
        mainPage.sendEmail(testData.getEmail());
        Assert.assertTrue(mainPage.isReceived(testData.getEmail(), getTimeout(), getSleepInterval()));
    }

    @AfterMethod
    public void logout() {
        TutbyMailMainPage mainPage = new TutbyMailMainPage();
        mainPage.logout();
    }
}