package com.numberzApp.emailModal;

import com.numberzApp.genericLib.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

/**
 * Created by Kuldeep on 8/29/2017.
 */
public class EmailPage {

    private static final Logger LOG = new Logger(EmailPage.class);

    WebDriver driver;

    @FindBy(xpath = "//label[contains(text(),'Email')]")
    WebElement emailTextWE;

    // Buttons WebElement
    @FindBy(xpath = "//div[@class='col-md-7 no-padding']/button[text()='Cancel']")
    WebElement cancelButtonWE;

    @FindBy(xpath = "//div[@class='col-md-7 no-padding']/button[text()='Send']")
    WebElement sendButtonWE;


    @FindBy(xpath = "//a/span[contains(text(),'Expand')]")
    WebElement expandButtonWE;

    @FindBy(xpath = "//input[@placeholder=\"Email ID\"]")
    WebElement emailAddressWE;

    @FindBy(xpath = "//input[@placeholder=\"Email Subject\"]")
    WebElement emailSubjectWE;

    @FindBy(xpath = "//*[@id='tinymce']/p")
    WebElement emailMessageWE;

    @FindBy(xpath = "//label[text()='Pay Now']/..//div[@class='react-toggle-track']")
    WebElement payNowToggleButton;

    @FindBy(xpath = "//label[text()='Get Finance']/..//div[@class='react-toggle-track']")
    WebElement getFinanceToggleButton;

    public EmailPage(WebDriver driver){
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS); // Initialize all web Elements using PageFactory.initElements()
        PageFactory.initElements(driver,this);
    }

    public String getEmailTextMessage(){
        LOG.debug("Getting Email Text Message");
        String emailTextMessage = emailMessageWE.getText();
        return emailTextMessage;
    }

    public String getEmailSubject(){
        LOG.debug("Getting Email Subject");
        String emailSubject = emailSubjectWE.getAttribute("value");
        LOG.debug("Actual Email Subject is :"+emailSubject);
        return emailSubject;
    }

    public void clickOnSendButton() throws InterruptedException {
        LOG.debug("Clicking on Send Button shown on Email Modal/ Page ");
        sendButtonWE.click();
        LOG.debug("Wait for 10 sec after click on Send Button, otherwise you will get Network Error Issue");
        Thread.sleep(10000);
    }

    public void cancelEmail(){
        LOG.debug("Click on Cancel Button for Cancel the Email");
        cancelButtonWE.click();
    }

    /*public void sendEmail(){
        LOG.debug("Checking Email Address before click on send, Email Address should not be empty");
        String emailAddress=emailAddressWE.getAttribute("value");
        Assert.assertTrue(emailAddress != "", "Email Address is Empty, Please update customer Email Address");
        System.out.println("sending email to :"+emailAddress);
        Assert.assertTrue(expandButtonWE.isDisplayed(),"Expand Button on email modal not dispalyed");
        expandButtonWE.click();
        System.out.println("Subject is : "+getEmailSubject());
        clickOnSendButton();
    }*/

    public void checkEmailAddrPresent() throws InterruptedException {
        LOG.debug("Checking Email Address before click on send, Email Address should not be empty");
        String emailAddress=emailAddressWE.getAttribute("value");
        Assert.assertTrue(emailAddress != "", "Email Address is Empty, Please update customer Email Address");
        System.out.println("sending email to :"+emailAddress);
        /*LOG.debug("Wait for 3 sec after send an Email");
        Thread.sleep(3000);*/
    }

    public void clickOnExpandButton(){
        Assert.assertTrue(expandButtonWE.isDisplayed(),"Expand Button on email modal not displayed");
        Actions actions = new Actions(driver);
        actions.moveToElement(expandButtonWE).perform();
        expandButtonWE.click();
    }

    public void checkGetFinanceOptionEnabled(){
        WebElement getFinanceOption = driver.findElement(By.xpath("//label[text()='Get Finance']/..//input"));
        //LOG.debug("Get Finance Tag Size : "+getFinanceOption.getSize());
        LOG.debug("Get F Att is Present : " + isAttribtuePresent(getFinanceOption,"checked"));
    }

    private boolean isAttribtuePresent(WebElement element, String attribute) {
        Boolean result = false;
        try {
            String value = element.getAttribute(attribute);
            if (value != null){
                result = true;
            }
        } catch (Exception e) {}
        return result;
    }

    public void clickOnGetFinanceToggle(){
        LOG.debug("Click on Get Finance Button for disable it");
        WebDriverWait wait = new WebDriverWait(driver,60);
        wait.until(ExpectedConditions.elementToBeClickable(getFinanceToggleButton));

        getFinanceToggleButton.click();
    }
}
