package com.sparta.web.sendemail;

import com.codeborne.selenide.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.web.WebUtils;
import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static com.codeborne.selenide.Selenide.*;

@Data
public class SendAndReceiveEmails {

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
    public void sendAndReceiveEmails(TestData testData) {
        login(testData.getUser());
        sendNewEmail(testData.getEmail());

        WebDriver webDriver = WebDriverRunner.getWebDriver();
        Wait<WebDriver> wait = new WebDriverWait(webDriver, getTimeout() * 60L, getSleepInterval() * 1000L);

        SoftAssert isReceived = new SoftAssert();

        try {
            wait.until(
                    CustomExpectedConditions.isEmailReceived(
                            ".mail-MessageSnippet-Item_subject>span",
                            testData.getEmail().getSubject()
                    )
            );
            isReceived.assertTrue(true);
        } catch (TimeoutException ex) {

            isReceived.assertTrue(false, "Email was not received");
        }

        //logout();
        isReceived.assertAll();
    }

    public void login(User user) {
        open(getUrl());
        $(By.id("Username")).setValue(user.getUsername());
        $(By.id("Password")).setValue(user.getPassword()).pressEnter();
    }

    @AfterMethod
    public void logout() {
        //try catch не смог выйти
        $x("//a[@href='https://passport.yandex.ru']").click();
        $(".legouser__menu-item_action_exit").click();
        //webDriver.quit();
        Selenide.closeWebDriver();
    }

    public void sendNewEmail(Email email) {
        SelenideElement newEmailButton = $(".js-main-action-compose");
        newEmailButton.shouldBe(Condition.visible);
        newEmailButton.click();
        //add recipient
        $(".tst-field-to .composeYabbles").setValue(email.getRecipient());
        //add subject
        email.setSubject(WebUtils.getDate());
        $(".ComposeSubject-TextField").setValue(email.getSubject());
        //add body
        email.setBody(WebUtils.getMultiLineEmailBodyWithDate());
        $(".cke_wysiwyg_div").setValue(email.getBody());
        //send
        $(".ComposeSendButton_desktop").click();
        //close popup
        $(".ComposeDoneScreen-Actions").click();
    }
}