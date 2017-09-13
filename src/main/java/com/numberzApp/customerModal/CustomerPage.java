package com.numberzApp.customerModal;

import com.numberzApp.genericLib.Logger;
import com.sun.xml.internal.bind.v2.TODO;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by Surender on 8/29/2017.
 */
public class CustomerPage {

    private static final Logger LOG = new Logger(CustomerPage.class);

    WebDriver driver;

    @FindBy(xpath = "//div[2]/button[text()='Save']")
    WebElement saveButton;

    @FindBy(xpath = "//button[text()='Yes']")
    WebElement yesButton;

    @FindBy(xpath = "//button[text()='No']")
    WebElement noButton;

    @FindBy(xpath = "//span[contains(text(),'You have not entered GSTIN')]")
    WebElement gstinPopUp;

    @FindBy(xpath = "//span[contains(text(),'Billing address')]")
    WebElement billingAddressPopUp;
    @FindBy(xpath = "//div[2]/button[text()='Cancel']")
    WebElement cancelButton;

    @FindBy(xpath = "//input[@placeholder=\"Customer Name\"]")
    WebElement customerNameInputBox;

    @FindBy(xpath = "//input[@placeholder=\"Contact Name\"]")
    WebElement contactPersonNameInputBox;

    @FindBy(xpath = "//input[@placeholder=\"Email\"]")
    WebElement emailInputBox;

    @FindBy(xpath = "//input[@placeholder=\"Phone\"]")
    WebElement phoneInputBox;

    @FindBy(id = "shipping-address")
    WebElement sameAsBillingAddressCheckBox;

    //-------------------------   xxxxxxxxxxxxx   ----------------------
    // Customer Billing Address WebElement on Tax Invoice page

    @FindBy(xpath = "//div[5]/div[1]/div/div[2]/input[contains(@placeholder,'Address Line 1')]")
    WebElement billingAddressLine1InputBox;

    @FindBy(xpath = "//div[5]/div[1]/div/div[2]/input[contains(@placeholder,'Address Line 2')]")
    WebElement billingAddressLine2InputBox;

    @FindBy(xpath = "//div[5]/div[1]/div/div[2]/span/input[contains(@placeholder,'City')]")
    WebElement billingCityInputBox;

    @FindBy(xpath = "//div[5]/div[1]/div/div[2]/span/input[contains(@placeholder,'Pincode')]")
    WebElement billingPinCodeInputBox;

    @FindBy(xpath = "//div[5]/div[1]/div/div[2]/..//div[text()='State']")
    WebElement billingSelectStateWE;

    @FindBy(className = "//div[5]/div[1]/div/div[2]/..//div[@title='India']")
    WebElement billingSelectCountryWE;

    //----------------      xxxxxxxxxxxxx  --------------------------


    public CustomerPage(WebDriver driver){
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        PageFactory.initElements(driver,this);
    }

    // Actions / Methods on Customer's Page

    public void enterCustomerName(String customerName){
        customerNameInputBox.sendKeys(customerName);
    }

    public void enterContactPersonName(String contactPersonName){
        contactPersonNameInputBox.sendKeys(contactPersonName);
    }

    public void enterEmailAddress(String email){
        emailInputBox.sendKeys(email);
    }

    public void enterPhone(String phone){
        phoneInputBox.sendKeys(phone);
    }

    public void enterBillingAddress1(String billingAddress1){
        billingAddressLine1InputBox.sendKeys(billingAddress1);
    }

    public void enterBillingAddress2(String billingAddress2){
        billingAddressLine2InputBox.sendKeys(billingAddress2);
    }

    public void enterBillingCity(String billingCity){
        billingCityInputBox.sendKeys(billingCity);
    }

    public void enterBillingPinCode(String billingPinCode){
        billingPinCodeInputBox.sendKeys(billingPinCode);
    }

    public void selectBillingState(String billingState){
        billingSelectStateWE.click();
        driver.findElement(By.xpath("//div[5]/div[1]/div/div[2]/..//div[text()='"+billingState+"']")).click();
    }

    public void selectBillingCountry(String billingCountry){
        billingSelectCountryWE.click();
        driver.findElement(By.xpath("//div[5]/div[1]/div/div[2]/..//div[text()='"+billingCountry+"']")).click();
    }



    public void addCustomerBillingAddress(String address1, String address2, String city,String pinCode, String state){
        enterBillingAddress1(address1);
        enterBillingAddress2(address2);
        enterBillingCity(city);
        enterBillingPinCode(pinCode);
        selectBillingState(state);
        //selectBillingCountry(country);

    }

    public void clickOnSameAsBillingAddress(){
        sameAsBillingAddressCheckBox.click();
    }

    public void enterCustomerDetails(String customerName, String contactPersonName, String emailAddress, String phone,
                                     String address1, String address2, String city,String pinCode, String state){

        enterCustomerName(customerName);
        enterContactPersonName(contactPersonName);
        enterEmailAddress(emailAddress);
        enterPhone(phone);
        addCustomerBillingAddress(address1,address2,city,pinCode,state);

    }

    public void enterCustomerDetailsWithSameBillingAndShippingAddress(String customerName, String contactPersonName,
                                                                      String emailAddress, String phone,String address1,
                                                                      String address2, String city,String pinCode,
                                                                      String state) throws InterruptedException {

        enterCustomerDetails(customerName,contactPersonName,emailAddress,phone,address1,address2,city,pinCode,state);
        sameAsBillingAddressCheckBox.click();
        clickOnSaveButton();
        gstinPopUp.isDisplayed();
        yesButton.click();
        Thread.sleep(5000);

        /*TODO :
    Verify in Automation both Billing and Shipping address are same or not.
    Once user click on same as Billing CheckBox , same address should be updated on Shipping Address
*/
    }

    public void clickOnSaveButton(){
        saveButton.click();
    }


}
