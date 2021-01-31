package com.sparta.web.sendemail;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static com.codeborne.selenide.Selenide.*;

@Data
public class SendAndReceiveEmails {

    String url;

    @Parameters({"url"})
    @BeforeTest
    public void setUpUrl(String url) {
        this.url = url;
    }

    @DataProvider
    public Object[][] getTestEmailInputs() throws IOException {
        List<TestEmailInput> testEmailInput = new ObjectMapper()
                .readValue(
                        Paths.get("src", "test", "resources", "testEmail/testEmailsInput.json").toFile(),
                        new TypeReference<List<TestEmailInput>>() {
                        });
        Object[][] inputData = new Object[testEmailInput.size()][1];
        for (int i = 0; i < testEmailInput.size(); i++) {
            inputData[i][0] = testEmailInput.get(i);
        }
        return inputData;
    }

    @Test(dataProvider = "getTestEmailInputs")
    public void sendAndReceiveEmails(TestEmailInput testEmailInput) {
        login(testEmailInput);
        sendNewEmail(testEmailInput);
        verifySentEmails(testEmailInput);
        logout(testEmailInput);
    }

    public void login(TestEmailInput testEmailInput) {
        open(getUrl());
        $(By.id("Username")).setValue(testEmailInput.getUsername());
        $(By.id("Password")).setValue(testEmailInput.getPassword()).pressEnter();
    }

    public void logout(TestEmailInput testEmailInput) {
        $x("//span[@class='user-account__name']//parent::a[@href='https://passport.yandex.ru']").click();
        $x("//ul[@class='menu__group']/li[last()]/a").click();
    }

    private void sendNewEmail(TestEmailInput testEmailInput) {
        SelenideElement newEmailButton = $x("//a[@class='mail-ComposeButton js-main-action-compose']");
        newEmailButton.shouldBe(Condition.visible);
        newEmailButton.click();
        //add recipient
        $x("//div[@class='MultipleAddressesDesktop ComposeRecipients-MultipleAddressField ComposeRecipients-ToField tst-field-to']//div[@contenteditable='true']")
                .setValue(testEmailInput.getEmailRecipient());
        //add subject
        $x("//div[@class='compose-LabelRow-Content ComposeSubject-Content']/input")
                .setValue(testEmailInput.getEmailSubject());
        //add body
        $x("//div[@class='cke_wysiwyg_div cke_reset cke_enable_context_menu cke_editable cke_editable_themed cke_contents_ltr cke_htmlplaceholder']")
                .setValue(testEmailInput.getEmailBody());
        //send
        $x("//div[@class='ComposeControlPanelButton ComposeControlPanel-Button ComposeControlPanel-SendButton ComposeSendButton ComposeSendButton_desktop']")
                .click();
        //close popup
        $x("//div[@class='ComposeDoneScreen-Actions']/a[@class='control link link_theme_normal ComposeDoneScreen-Link']")
                .click();
    }

    private void verifySentEmails(TestEmailInput testEmailInput) {
        logout(testEmailInput);
        login(testEmailInput);
        String xPath = "//div[@class='mail-MessageSnippet-Item_content-row']//span[@class='mail-MessageSnippet-Item mail-MessageSnippet-Item_subject']/span";
        SelenideElement emailSubject = $x(xPath);
        emailSubject.shouldBe(Condition.visible);
        WebDriver webDriver = WebDriverRunner.getWebDriver();
        List<WebElement> elements = webDriver.findElements(By.xpath(xPath));
        //ElementsCollection elements =  $$(By.xpath(xPath));
        boolean found = false;
        for (WebElement element : elements) {
            String subject = element.getText();
            if (subject.equals(testEmailInput.getEmailSubject())) {
                found = true;
                break;
            }
        }
        if (!found) {
            logout(testEmailInput);
            Assert.fail("Email not found");
        }
    }
}