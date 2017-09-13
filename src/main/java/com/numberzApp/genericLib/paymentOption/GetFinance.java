package com.numberzApp.genericLib.paymentOption;

import com.gargoylesoftware.htmlunit.Page;
import com.numberzApp.genericLib.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

/**
 * Created by Kuldeep on 9/1/2017.
 */
public class GetFinance {

    private static final Logger LOG = new Logger(GetFinance.class);

    WebDriver driver;
    String actualLoanPageTitle;
    String expectedLoanPageTitle = "numberz - Cashflow. Simplified.";



    @FindBy(xpath = "//div/h3[text()='Numberz.in']")
    WebElement numberzDotInTextWE;

    @FindBy(xpath = "//*[@id='app']/..//span[contains(text(),'Invoice')]/..//span[2]")
    WebElement invoiceNumberWE;

    @FindBy(xpath = "//*[@id='app']/..//span[text()='Due Date']/..//span[2]")
    WebElement dueDateWE;

    @FindBy(xpath = "//*[@id='app']/..//span[text()='Phone']/..//span/input")
    WebElement phoneWE;

    @FindBy(xpath = "//*[@id='app']/..//span[text()='Email']/..//span/input")
    WebElement emailWE;

    @FindBy(xpath = "//*[@id='app']/..//span[text()='Balance']/..//span[2]")
    WebElement balanceWE;

    @FindBy(xpath = "//*[@id='app']/..//span[text()='Net Total']/..//span[2]")
    WebElement netTotalWE;

    @FindBy(xpath = "//*[@id='app']/..//span[text()='Loan Amount']/..//span/input")
    WebElement loanAmountWE;

    @FindBy(xpath = "//button[text()='Get Finance']")
    WebElement getGetFinanceBtnOnLoanPage;


    public GetFinance(WebDriver driver){
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        PageFactory.initElements(driver,this);
    }
    public String  getInvoiceNum(){
        String invoiceNumber = invoiceNumberWE.getText();
        LOG.debug("Invoice Number on Get Finance Page : "+invoiceNumber);
        return invoiceNumber;
    }

    public String getDueDate(){
        String dueDate = dueDateWE.getText();
        LOG.debug("Due Date on Get Finance Page : "+dueDate);
        return dueDate;
    }

    public String getPhoneNum(){
        String phone = phoneWE.getAttribute("value");
        LOG.debug("Phone Number on Get Finance Page : "+phone);
        return phone;
    }

    public String getEmailAddr(){
        String email = emailWE.getAttribute("value");
        LOG.debug("Email Address on Get Finance page : "+email);
        return email;
    }

    public String getBalance(){
        //String balan
        LOG.debug("Balance on Get Finance page : "+balanceWE.getText());
        return balanceWE.getText();
    }

    public String getNetTotal(){
        LOG.debug("Net Total on Get Finance Page : "+netTotalWE.getText());
        return netTotalWE.getText();
    }

    public String getLoanAmount(){
        LOG.debug("Loan Amount on Get Finance Page : "+loanAmountWE.getAttribute("value"));
        return loanAmountWE.getAttribute("value");
    }

    public boolean isFinancePageDisplayed(){
        actualLoanPageTitle = driver.getTitle();
        LOG.debug("Actual Title of Loan Page :" + actualLoanPageTitle);
        LOG.debug("Expected Title of Loan Page :" + expectedLoanPageTitle);
        Assert.assertTrue(expectedLoanPageTitle.equals(actualLoanPageTitle), "Loan page title not matching, unable to navigate to expected Loan page");
        Assert.assertTrue(getGetFinanceBtnOnLoanPage.isDisplayed(), "Get Finance button not displayed on Loan Page");
        return true;
    }

    public void getFinancePageDetails() {
        LOG.debug("Getting Get Finance Page details ");
        getInvoiceNum();
        getDueDate();
        getPhoneNum();
        getEmailAddr();
        getBalance();
        getNetTotal();
        getLoanAmount();

    }

}
