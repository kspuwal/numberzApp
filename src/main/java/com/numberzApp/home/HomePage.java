package com.numberzApp.home;

import com.numberzApp.genericLib.Logger;
import org.openqa.selenium.JavascriptExecutor;
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
 * Created by Kuldeep on 8/28/2017.
 */
public class HomePage {
    private static final Logger LOG = new Logger(HomePage.class);

    WebDriver driver;

    @FindBy(xpath =".//*[@id='homeContainer']/div[1]/div/span[text()='Home']")
    WebElement homeTextWE;

    @FindBy(xpath ="//*[@id='sidebar']/ul/li[2]")
    WebElement invoicesSideBarWE;

    @FindBy(xpath ="//*[@id='sidebar']/ul/li[1]")
    WebElement homeSideBarWE;

    public HomePage(WebDriver driver){
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        PageFactory.initElements(driver, this); // Initialize all web Elements using PageFactory.initElements()
    }

    public void navigateToInvoicesPage() throws InterruptedException {
        LOG.debug("Navigate to Invoices by Click on Invoices Button displaying in Left SideBar");

       // driver.findElement(By.xpath("//div[@id='sidebar']/..//span[text()='Invoices']")).click();

        //Using Java Script to resolve the 'element not clickable' issue
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.scrollTo(0,"+invoicesSideBarWE.getLocation().y+")");
        Assert.assertTrue(invoicesSideBarWE.isDisplayed(),"Invoices button not displayed in Sidebar ");
        LOG.debug("Clicking on Invoices button shown in Sidebar");
        Assert.assertTrue(invoicesSideBarWE.isDisplayed(),"Invoices button in SideBar not displayed");
        Actions actions = new Actions(driver);
        actions.moveToElement(invoicesSideBarWE).perform();
        invoicesSideBarWE.click();
        LOG.debug("Invoices page loading, please Wait for 7 sec ");
        Thread.sleep(7000);
    }

    public boolean isDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver,60);
        wait.until(ExpectedConditions.visibilityOf(homeTextWE));
        return homeTextWE.isDisplayed();
    }
}
