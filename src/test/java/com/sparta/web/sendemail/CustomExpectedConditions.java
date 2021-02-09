package com.sparta.web.sendemail;

import com.codeborne.selenide.Selenide;
import lombok.Data;
import org.jetbrains.annotations.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.List;

@Data
public class CustomExpectedConditions implements ExpectedCondition {

    public static ExpectedCondition<Boolean> isEmailReceived(String cssSelector, String subject) {
        return webDriver -> {
            List<WebElement> elements = webDriver.findElements(By.cssSelector(cssSelector));
            for (WebElement element : elements) {
                if (element.getText().equals(subject)) {
                    System.out.println("Found email has subject: " + element.getText());
                    return true;
                }
            }
            Selenide.refresh();
            return false;
        };
    }

    @Nullable
    @Override
    public Object apply(@Nullable Object o) {
        return null;
    }
}
