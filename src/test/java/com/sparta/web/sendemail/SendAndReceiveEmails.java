package com.sparta.web.sendemail;

import com.codeborne.selenide.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.web.WebUtils;
import lombok.Data;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static com.codeborne.selenide.Selenide.*;

@Data
public class SendAndReceiveEmails {

    String url;
    String emailSubject;
    String emailBody;

    @Parameters({"url"})
    @BeforeTest
    public void setUpUrl(String url) {
        this.url = url;
    }

    @BeforeMethod
    public void openUrl() {
        open(getUrl());
    }

    @Test(dataProvider = "getTestEmailInputs")
    public void sendAndReceiveEmails(TestEmailInput testEmailInput) {
        //login
        $(By.id("Username")).setValue(testEmailInput.getUsername());
        $(By.id("Password")).setValue(testEmailInput.getPassword());
        $x("//input[@class='button loginButton gradientforbutton']").click();
        //new email
        SelenideElement newEmailButton = $x("//a[@class='mail-ComposeButton js-main-action-compose']");
        newEmailButton.shouldBe(Condition.visible);
        newEmailButton.click();
        //add recipient
        $x("//div[@class='MultipleAddressesDesktop ComposeRecipients-MultipleAddressField ComposeRecipients-ToField tst-field-to']//div[@contenteditable='true']")
                .setValue(testEmailInput.getRecipient());
        //add subject
        setEmailSubject(WebUtils.getDate());
        $x("//div[@class='compose-LabelRow-Content ComposeSubject-Content']/input")
                .setValue(getEmailSubject());
        //add body
        setEmailBody(WebUtils.getMultiLineEmailBodyWithDate());
        $x("//div[@class='cke_wysiwyg_div cke_reset cke_enable_context_menu cke_editable cke_editable_themed cke_contents_ltr cke_htmlplaceholder']")
                .setValue(getEmailBody());
        //send
        $x("//div[@class='ComposeControlPanelButton ComposeControlPanel-Button ComposeControlPanel-SendButton ComposeSendButton ComposeSendButton_desktop']")
                .click();
        //close popup
        $x("//div[@class='ComposeDoneScreen-Actions']/a[@class='control link link_theme_normal ComposeDoneScreen-Link']")
                .click();
        //open sent folder
        $x("//a[@href='#sent']").click();
        $x("//div[@class='b-mail-pager__label']").shouldBe(Condition.visible);
        ElementsCollection elements = $$(By.xpath("//div[@class='mail-MessageSnippet-Item_content-row']//span[@class='mail-MessageSnippet-Item mail-MessageSnippet-Item_subject']/span"));
        boolean found = false;
        for (SelenideElement element : elements) {
            String subject = element.getText();
            if (subject.equals(getEmailSubject())) {
                element.click();
                Assert.assertEquals(
                        $x("//div[@class='js-message-body-content mail-Message-Body-Content']").getText(),
                        getEmailBody(),
                        "The subject is the same but the body is not");
                found = true;
                break;
            }
        }
        if (!found) {
            Assert.fail("Email not found");
        }
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
}
