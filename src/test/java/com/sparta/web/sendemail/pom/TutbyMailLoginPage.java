package com.sparta.web.sendemail.pom;

import com.codeborne.selenide.SelenideElement;
import com.sparta.web.sendemail.User;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;

import static com.codeborne.selenide.Selenide.$;

public class TutbyMailLoginPage {

    SelenideElement usernameInput = $(By.id("Username"));
    SelenideElement passwordInput = $(By.id("Password"));

    public void login(User user) {
        try {
            usernameInput.setValue(user.getUsername());
            passwordInput.setValue(user.getPassword()).pressEnter();
        } catch (NoSuchElementException ex) {
            Assert.fail(ex.getLocalizedMessage());
        }
    }
}
