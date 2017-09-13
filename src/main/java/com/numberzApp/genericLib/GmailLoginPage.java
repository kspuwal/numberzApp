package com.numberzApp.genericLib;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by Surender on 8/31/2017.
 */
public class GmailLoginPage {

    private static final Logger LOG = new Logger(GmailLoginPage.class);

    String actualLoanPageTitle;
    String expectedLoanPageTitle = "numberz - Cashflow. Simplified.";

    WebDriver driver;
    @FindBy(xpath = "//input[@aria-label='Email or phone']")
    WebElement gmailUsernameWE;

    @FindBy(xpath = "//a[contains(text(),'Get Finance')]")
    WebElement getFinanceButtonWE;

    @FindBy(name = "password")
    WebElement gmailPasswordInputBox;

    @FindBy(id = "identifierNext")
    WebElement usernameNextButton;

    @FindBy(id = "passwordNext")
    WebElement passwordNextButton;

    WebElement matchingEmailWE;

    public GmailLoginPage(WebDriver driver) {
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        PageFactory.initElements(driver, this);
    }

    public void loginToGmail(String username, String password) throws InterruptedException {
        driver.get("https://www.gmail.com");
        LOG.debug("Enter Username in Email / Phone input field");
        gmailUsernameWE.clear();
        gmailUsernameWE.sendKeys(username);
        usernameNextButton.click();
//        gmailPasswordInputBox.clear();
        gmailPasswordInputBox.sendKeys(password);
        Actions action = new Actions(driver);
        action.moveToElement(passwordNextButton).perform();
        passwordNextButton.click();
        Thread.sleep(5000);

    }

    public boolean checkInvoiceReceivedOrNot(String expectedSubject) {
        List<WebElement> gmailSubjects = driver.findElements(By.xpath("//td[6]/..//div[@class='y6']/span"));

        for (WebElement we : gmailSubjects) {
            String actualSubject = we.getText();
            LOG.debug("Matching with Email Subject : "+actualSubject);
            if (expectedSubject.equalsIgnoreCase(actualSubject)) {
                matchingEmailWE = we;
                LOG.debug("Matched with Email Subject : "+actualSubject);
                LOG.debug("Invoice received successfully having subject :" + actualSubject);
                return true;
            }
        }
        LOG.debug("Invoice not received yet");
        return false;
    }

    public void openGmailBasedOnSubject(String expectedSubject) {

        //driver.findElement(By.xpath("//td[6]/..//div[@class='y6']/span[contains(text(),'"+expectedSubject+"')]")).click();
        matchingEmailWE.click();
        String actualSubOnOpenedGmail = driver.findElement(By.xpath("//div[@class='ha']/h2")).getText();
        LOG.debug("Actual Email Subject : " + actualSubOnOpenedGmail);
        LOG.debug("Expected Email Subject : " + expectedSubject);
        Assert.assertTrue(actualSubOnOpenedGmail.equals(expectedSubject), "Subject not matching , you might be opened wrong email");
        LOG.debug("You opened correct email having subject : " + actualSubOnOpenedGmail);

    }

    public boolean isGetFinanceDisplayed(){
        LOG.debug("Checking Get Finance Displayed or Not ");
        Assert.assertTrue(getFinanceButtonWE.isDisplayed(), "Get Finance Button not Displayed");
        return getFinanceButtonWE.isDisplayed();
    }

    public void clickOnGetFinanceButton() throws InterruptedException {
        Assert.assertTrue(getFinanceButtonWE.isDisplayed(), "Get Finance button not displayed");
        getFinanceButtonWE.click();
        LOG.debug("After click on Get Finance Button , please wait for Get Finance page ");
        Thread.sleep(7000);

    }

    public void clickOnGetFinanceAndSwitchToLoanWindow() throws InterruptedException {

        String gmailTab = driver.getWindowHandle();
        LOG.debug("Gamil window handle : "+ gmailTab);
        clickOnGetFinanceButton();
        Set<String> windowHandles = driver.getWindowHandles();
        for (String handle : windowHandles) {
            LOG.debug("window handle : "+handle);
            if(!gmailTab.equals(handle)) {
                driver.switchTo().window(handle);
                Thread.sleep(5000);
            }
        }
    }
}

