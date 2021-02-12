package com.sparta.web.sendemail.pom;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.sparta.web.WebUtils;
import com.sparta.web.sendemail.CustomExpectedConditions;
import com.sparta.web.sendemail.Email;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class TutbyMailMainPage {

    public static void sendEmail(Email email) {
        try {
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
            //close the popup
            $(".ComposeDoneScreen-Actions").click();
        } catch (NoSuchElementException ex){
            Assert.fail(ex.getLocalizedMessage());
        }
    }

    public static boolean isReceived(Email email, int timeout, int sleepInterval) {
        WebDriver webDriver = WebDriverRunner.getWebDriver();
        Wait<WebDriver> wait = new WebDriverWait(webDriver, timeout * 60L, sleepInterval * 1000L);
        String cssSelector = ".mail-MessageSnippet-Item_subject>span";
        try {
            wait.until(CustomExpectedConditions.isEmailReceived(cssSelector, email.getSubject()));
            return true;
        } catch (TimeoutException timeoutException) {
            return false;
        }
    }

    public static void logout() {
        try {
            $x("//a[@href='https://passport.yandex.ru']").click();
            $(".legouser__menu-item_action_exit").click();
            Selenide.closeWebDriver();
        } catch (NoSuchElementException | AssertionError ex) {
            Assert.fail("Logout unsuccessful due to " + ex.getLocalizedMessage());
        }
    }

}
