package com.numberzApp.invoices;

import com.numberzApp.genericLib.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

/**
 * Created by Kuldeep on 8/28/2017.
 */
public class InvoicesPage {

    private static final Logger LOG = new Logger(InvoicesPage.class);

    WebDriver driver;

    @FindBy(xpath = "//span[@class='col-md-3 tabHeadingLabel'][text()='Invoices']")
    WebElement invoiceTextWE;

    @FindBy(id = "add-invoice-btn-navbar")
    WebElement addInvoiceBtn;

    @FindBy(xpath = "//div[@id='app']/..//span/i")
    WebElement caretDownBtn;

    @FindBy(id = "receivables-search-invoice")
    WebElement searchInputBox;

    @FindBy(xpath = "//span/a/em")
    WebElement searchBtn;

    @FindBy(id = "receivables-all-filter")
    WebElement allFilterWE;

    @FindBy(id = "receivables-overdue-filter")
    WebElement overDueFilterWE;

    @FindBy(id = "receivables-due-soon-filter")
    WebElement dueSoonFilterWE;

    @FindBy(id = "receivables-due-later-filter")
    WebElement dueLaterFilterWE;

    @FindBy(id = "receivables-closed-filter")
    WebElement closedFilterWE;

    @FindBy(id = "receivables-paid-filter")
    WebElement paidFilterWE;

    @FindBy(id = "receivables-cancelled-filter")
    WebElement cancelFilterWE;


    public InvoicesPage(WebDriver driver){
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        PageFactory.initElements(driver, this); // Initialize all web Elements using PageFactory.initElements()
    }


    public void clickOnAddInvoiceButton() throws InterruptedException {
        LOG.debug("Navigate to ADD Tax Invoice from Invoices by click on Add Invoice button ");
        Assert.assertTrue(addInvoiceBtn.isDisplayed(), "Add Invoice button not visible/displayed");

        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.scrollTo(0,"+addInvoiceBtn.getLocation().y+")");
        addInvoiceBtn.click();
        LOG.debug("Tax Invoice page loading, please Wait for 7 sec ");
        //Due to slow Internet speed , I make it 10 Sec wait , will reduce it to 5 sec
        Thread.sleep(10000);
    }

    /*public boolean isDisplay(){
        LOG.debug("Checking Invoice page is Displayed or Not");
        return invoiceTextWE.isDisplayed();
    }
*/
}
