package com.sparta.web.sendemail.pom;

import com.sparta.web.sendemail.User;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;

import static com.codeborne.selenide.Selenide.$;

public class TutbyMailLoginPage {

    public static void login(User user) {
        try {
            $(By.id("Username")).setValue(user.getUsername());
            $(By.id("Password")).setValue(user.getPassword()).pressEnter();
        } catch (NoSuchElementException ex) {
            Assert.fail(ex.getLocalizedMessage());
        }
    }

}
