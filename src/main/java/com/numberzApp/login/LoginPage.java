package com.numberzApp.login;

import com.numberzApp.genericLib.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;


/**
 * Created by Kuldeep on 8/28/2017.
 */
public class LoginPage {

    private static final Logger LOG = new Logger(LoginPage.class);

    WebDriver driver;
    public static final String loginUrl = "https://app.numberz.in/app";

    @FindBy(id="pageTitle")
    WebElement pageTitleWE;

    @FindBy(id="Login-email")
    WebElement emailAddressWE;

    @FindBy(id="Login-password")
    WebElement passwordWE;

    @FindBy(id="Login-button")
    WebElement loginButtonWE;


    public LoginPage(WebDriver driver){
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
        PageFactory.initElements(driver, this); // Initialize all web Elements using PageFactory.initElements()
    }


    public void enterEmailAddress(String username)
    {
        LOG.debug("Clear Username Input filed");
        emailAddressWE.clear();
        LOG.debug("Enter username / Email Address : "+username);
        emailAddressWE.sendKeys(username);
    }

    public void enterPassword(String password){

        LOG.debug("Clear Password Input filed");
        passwordWE.clear();
        LOG.debug("Enter Password : "+password);
        passwordWE.sendKeys(password);
    }

    public void clickOnLoginButton(){

        LOG.debug("Click on Sign In button");
        loginButtonWE.click();
    }

    public void loginToNumberzApp(String username, String password) throws InterruptedException {
        this.enterEmailAddress(username);
        this.enterPassword(password);
        this.clickOnLoginButton();
        LOG.debug("Home page Loading , please Wait for 7 seconds");
        Thread.sleep(25000);
    }
}
