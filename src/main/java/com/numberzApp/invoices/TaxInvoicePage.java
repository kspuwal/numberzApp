package com.numberzApp.invoices;

import com.numberzApp.genericLib.Logger;
import org.openqa.selenium.By;
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
public class TaxInvoicePage {

    private static final Logger LOG = new Logger(TaxInvoicePage.class);

    WebDriver driver;

    //-------------------------   xxxxxxxxxxxxx   ----------------------
    //Tax Invoice Button's WebElement : Print, Save, Cancel and Send
    @FindBy(id = "create-invoice-preview-btn")
    WebElement taxInvoicePrintBtn;

    @FindBy(id = "create-invoice-save-btn")
    WebElement taxInvoiceSaveBtn;

    @FindBy(id = "create-invoice-cancel-btn")
    WebElement taxInvoiceCancelBtn;

    @FindBy(id = "create-invoice-send-btn")
    WebElement taxInvoiceSendBtn;



    //-------------------------   xxxxxxxxxxxxx   ----------------------
    //WebElement for Add or Update a Customer details on Tax Invoice page

    @FindBy(xpath = "//div[@id='app']/..//div[contains(@class,'select-customer')]/div")
    WebElement selectCustInputBox;

    @FindBy(xpath = "//input[contains(@placeholder,'Invoice #')]")
    WebElement invoiceNoInputBox;

    @FindBy(xpath = "//input[contains(@placeholder,'Invoice Date')]")
    WebElement invoiceDateWE;

    @FindBy(id = "create-invoice-payment-terms")
    WebElement selectPaymentTermsWE;

    @FindBy(xpath = "//input[contains(@placeholder,'Due Date')]")
    WebElement dueDateWE;

    //-------------------------   xxxxxxxxxxxxx   ----------------------
    // Customer Address WebElement on Tax Invoice page

    @FindBy(xpath = "//input[contains(@placeholder,'Address Line 1')]")
    WebElement addressLine1InputBox;

    @FindBy(xpath = "//input[contains(@placeholder,'Address Line 2')]")
    WebElement addressLine2InputBox;

    @FindBy(xpath = "//input[contains(@placeholder,'city')]")
    WebElement cityInputBox;

    @FindBy(xpath = "//div[text()='State']")
    WebElement selectStateWE;

    @FindBy(className = "Select-value")
    WebElement selectCountryWE;

    @FindBy(xpath = "//input[contains(@placeholder,'Pincode')]")
    WebElement pinCodeInputBox;


    //-------------------------   xxxxxxxxxxxxx   ----------------------
    //WebElement for Add or Update an Item  an Item on Tax Invoice page

    @FindBy(xpath = "//*[@id='LineItemTable']/..//div[text()='Select or Add an item']")
    WebElement selectItemInputBox;

    @FindBy(xpath = "//*[@id='LineItemTable']/..//div[class='optionRenderer']")
    WebElement addItemOptionWE;

    @FindBy(id ="line-item-description-0")
    WebElement itemDescriptionWE;

    @FindBy(id = "line-item-price-0")
    WebElement itemPriceWE;

    @FindBy(id = "line-item-qty-0")
    WebElement itemQtyWE;

    @FindBy(id = "line-item-unit-0")
    WebElement itemUnitWE;

    @FindBy(id = "line-item-amount-0")
    WebElement itemAmountWE;

    @FindBy(xpath = "//div[text()='Select ...']")
    WebElement selectTaxWE;


    //-------------------------   xxxxxxxxxxxxx   ----------------------
    //WebElement for Tax Calculation ( CGST / SGST ) for Item's on Tax Invoice page
    // Total Amount in Rupees

    @FindBy(xpath = "//*[@id='app']/..//label[2]/span[2]")
    WebElement totalBigNumGetText;

    //Total small Amount
    //Note : Please join Big + Small Number to get total amount
    @FindBy(xpath = "//*[@id='app']/..//label[2]/span[3]")
    WebElement totalSmallNumGetText;

    @FindBy(xpath = "//div/label[contains(text(),'CGST')]")
    WebElement getFirstItemCGST;

    @FindBy(xpath = "//div/label[contains(text(),'SGST')]")
    WebElement getFirstItemSGST;

    @FindBy(id = "create-invoice-adv-payment")
    WebElement getAdvancePaymentValue;

    //Note : Please join Big + Small Number to get Net Total amount
    @FindBy(xpath = "//*[@id='InvoiceTotalSection-netTotal']/label[2]/span[2]")
    WebElement netTotalBigNumGetText;

    @FindBy(xpath = "//*[@id='InvoiceTotalSection-netTotal']/label[2]/span[3]")
    WebElement netTotalSmallNumGetText;


    public TaxInvoicePage(WebDriver driver){
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        PageFactory.initElements(driver, this); // Initialize all web Elements using PageFactory.initElements()
    }

    public void selectExistingCustomer( String customerName){
        LOG.debug("In selectExistingCustomer(): Select existing Customer ");
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.scrollTo(0,"+selectCustInputBox.getLocation().y+")");
        selectCustInputBox.click();
        driver.findElement(By.xpath("//*[@id='app']/..//div/span[text()='"+customerName+"']")).click();
    }

    public void clickOnAddNewCustomerOption(){
        LOG.debug("Click on Add New Customer option in the Invoice To drop-down");
        Assert.assertTrue(selectCustInputBox.isDisplayed(),"Select or Add Customers input box not displayed");
        Actions actions = new Actions(driver);
        actions.moveToElement(selectCustInputBox).perform();
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.scrollTo(0,"+selectCustInputBox.getLocation().x+")");
        Assert.assertTrue(selectCustInputBox.isDisplayed(),"Customer Input box not displayed ");
        selectCustInputBox.click();
        driver.findElement(By.xpath("//*[@id='app']/..//div/span[text()='Add New Customer']")).click();

    }

    public void selectExistingItem( String itemName){
        LOG.debug("In selectExistingCustomer(): Select existing Customer ");
        Actions actions = new Actions(driver);
        actions.moveToElement(selectCustInputBox).perform();
        /*JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.scrollBy(0,250)", "");
        js.executeScript("window.scrollTo(0,"+selectItemInputBox.getLocation().x+")");*/
        Assert.assertTrue(selectItemInputBox.isDisplayed(),"Item Input box not displayed ");
        selectItemInputBox.click();
        driver.findElement(By.xpath("//*[@id='LineItemTable']/..//div/span[text()='"+itemName+"']")).click();
    }


    public void clickOnSendButton() throws InterruptedException {
        LOG.debug("Click on SEND button on Tax Invoice Page");
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.scrollTo(0,"+taxInvoiceSendBtn.getLocation().y+")");
        taxInvoiceSendBtn.click();
        Thread.sleep(3000);
    }













}
