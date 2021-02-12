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

    SelenideElement newEmailButton = $(".js-main-action-compose");
    SelenideElement addRecipientInput = $(".tst-field-to .composeYabbles");
    SelenideElement addSubjectInput = $(".ComposeSubject-TextField");
    SelenideElement addBodyInput = $(".cke_wysiwyg_div");
    SelenideElement sendEmailButton = $(".ComposeSendButton_desktop");
    SelenideElement closePopupButton = $(".ComposeDoneScreen-Actions");
    String emailSubjectLink = ".mail-MessageSnippet-Item_subject>span";
    SelenideElement userProfileButton = $x("//a[@href='https://passport.yandex.ru']");
    SelenideElement logoutButton = $(".legouser__menu-item_action_exit");

    public void sendEmail(Email email) {
        try {
            newEmailButton.shouldBe(Condition.visible);
            newEmailButton.click();
            addRecipientInput.setValue(email.getRecipient());
            email.setSubject(WebUtils.getDate());
            addSubjectInput.setValue(email.getSubject());
            email.setBody(WebUtils.getMultiLineEmailBodyWithDate());
            addBodyInput.setValue(email.getBody());
            sendEmailButton.click();
            closePopupButton.click();
        } catch (NoSuchElementException ex) {
            Assert.fail(ex.getLocalizedMessage());
        }
    }

    public boolean isReceived(Email email, int timeout, int sleepInterval) {
        WebDriver webDriver = WebDriverRunner.getWebDriver();
        Wait<WebDriver> wait = new WebDriverWait(webDriver, timeout * 60L, sleepInterval * 1000L);
        try {
            wait.until(CustomExpectedConditions.isEmailReceived(emailSubjectLink, email.getSubject()));
            return true;
        } catch (TimeoutException timeoutException) {
            return false;
        }
    }

    public void logout() {
        try {
            userProfileButton.click();
            logoutButton.click();
            Selenide.closeWebDriver();
        } catch (NoSuchElementException | AssertionError ex) {
            Assert.fail("Logout unsuccessful due to " + ex.getLocalizedMessage());
        }
    }

}
