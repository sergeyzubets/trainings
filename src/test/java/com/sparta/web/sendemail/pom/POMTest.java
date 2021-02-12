package com.sparta.web.sendemail.pom;

import com.codeborne.selenide.Configuration;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.web.sendemail.TestData;
import lombok.Data;
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
    }

    @DataProvider
    public Object[][] getTestEmailInputs() throws IOException {
        List<TestData> testData = new ObjectMapper()
                .readValue(
                        Paths.get("src", "test", "resources", "testEmail/testEmailsInput.json").toFile(),
                        new TypeReference<List<TestData>>() {
                        });
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
        Assert.assertTrue(TutbyMailMainPage.isReceived(testData.getEmail(), getTimeout(), getSleepInterval()), "The email was not received");
    }

    @AfterMethod
    public void logout() {
        TutbyMailMainPage.logout();
    }
}