package com.numberzApp_Tests.invoices.sendInvoice;

import com.numberzApp.customerModal.CustomerPage;
import com.numberzApp.emailModal.EmailPage;
import com.numberzApp.genericLib.CsvUtils;
import com.numberzApp.genericLib.GmailLoginPage;
import com.numberzApp.genericLib.Logger;
import com.numberzApp.genericLib.TestLogHelper;
import com.numberzApp.genericLib.paymentOption.GetFinance;
import com.numberzApp.home.HomePage;
import com.numberzApp.invoices.InvoicesPage;
import com.numberzApp.invoices.TaxInvoicePage;
import com.numberzApp.login.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Kuldeep on 8/28/2017.
 */
public class SendInvoiceToExistingCustTest {

    private static final Logger LOG = new Logger(SendInvoiceToExistingCustTest.class);


    WebDriver driver;

    LoginPage loginPage;
    HomePage homePage;
    InvoicesPage invoicesPage;
    TaxInvoicePage taxInvoicePage;
    EmailPage emailPage;
    CustomerPage customerPage;
    GmailLoginPage gmailLoginPage;
    GetFinance getFinance;

    private Object[][] testCases;

    @BeforeMethod
    public void setup() throws Exception
    {
        TestLogHelper.startTestLogging(this.getClass().getName());
        String loginUrl ="https://app.numberz.in";
        String browser = "chrome";
        //String browser = "firefox";
        String browserType ="webdriver."+browser+".driver";
        if(browser == "firefox"){
            browserType = "webdriver.gecko.driver";
            browser = "gecko";
        }
        System.setProperty(browserType, "./src/main/resources/"+browser+"driver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        LOG.debug("Launching "+browser+" Browser with url : "+loginUrl);
        driver.get(loginUrl);
        loginPage = new LoginPage(driver);

        /*TODO : Will remove hardcoded username and password */
        loginPage.loginToNumberzApp("kspuwal@gmail.com","Kul@12345");

    }

    @DataProvider
    public Object[][] getData() throws IOException {
        // Get test cases from csv
        testCases = CsvUtils.getTestData(System.getProperty("user.dir")
                +"/src/test/java/com/numberzApp_Tests/invoices/sendInvoice/SendInvoiceToExistingCustTest.csv");
        return testCases;
    }

    @Test(dataProvider = "getData")
    public void invoicesTest(String testDesc, String customerName, String itemName) throws InterruptedException
    {   LOG.logUseCase(testDesc);
        LOG.debug("Navigating to Home Page");
        homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isDisplayed(),"Unable to navigate to Home page");
        homePage.navigateToInvoicesPage();
        invoicesPage = new InvoicesPage(driver);
       // Assert.assertTrue(invoicesPage.isDisplay(),"Unable to navigate to Invoices page");
        invoicesPage.clickOnAddInvoiceButton();
        taxInvoicePage = new TaxInvoicePage(driver);
        taxInvoicePage.selectExistingCustomer(customerName);
        taxInvoicePage.selectExistingItem(itemName);
        /*Thread.sleep(2000);
        taxInvoicePage.clickOnAddNewCustomerOption();*/
        taxInvoicePage.clickOnSendButton();

        //Navigate to Email Modal /Page and reusing the same WebDriver
        emailPage = new EmailPage(driver);
        emailPage.clickOnExpandButton();
        //Get Email Subject from Email Modal and store it in "expectedSub" as a String
        String expectedSub = emailPage.getEmailSubject();
        emailPage.checkEmailAddrPresent();
        emailPage.clickOnSendButton();

        //Navigate to Gmail Login  Page and reusing the same WebDriver
        gmailLoginPage = new GmailLoginPage(driver);
        /*TODO : Will remove this hardcoded email id and Password in future and get this from .csv file */
        gmailLoginPage.loginToGmail("kautoqa@gmail.com","9056976717");
        Assert.assertTrue(gmailLoginPage.checkInvoiceReceivedOrNot(expectedSub), " Invoice email not received");
        gmailLoginPage.openGmailBasedOnSubject(expectedSub);
        //gmailLoginPage.clickOnGetFinanceButton();
        gmailLoginPage.clickOnGetFinanceAndSwitchToLoanWindow();

        //Navigate to Get Finance Page and reusing the same WebDriver
        getFinance = new GetFinance(driver);
        Assert.assertTrue(getFinance.isFinancePageDisplayed(), "Get Finance Page not displayed ");
        getFinance.getFinancePageDetails();
        LOG.debug("Verified Get Finance option working fine");
    }


    @AfterClass
    public void closeAllBrowser() throws InterruptedException {
        Thread.sleep(10000);
        driver.close();
        driver.quit();
    }
}
