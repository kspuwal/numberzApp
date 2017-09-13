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
import com.sun.xml.internal.bind.v2.TODO;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 * Created by Surender on 8/29/2017.
 */
public class SendInvoiceToNewCustomerTest {

    private static final Logger LOG = new Logger(SendInvoiceToNewCustomerTest.class);
    //org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(SendInvoiceToNewCustomerTest.class);

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
        if(browser == "firefox"){
            System.setProperty("webdriver.gecko.driver", "./src/main/resources/geckodriver.exe");
            driver = new FirefoxDriver();
        }
        else if (browser == "chrome")
        {
            System.setProperty("webdriver.chrome.driver", "./src/main/resources/chromedriver.exe");
            /*System.setProperty("webdriver.chrome.logfile", "D:\\chromedriver.log");
            System.setProperty("webdriver.chrome.verboseLogging", "true");
            DesiredCapabilities caps = DesiredCapabilities.chrome();
            LoggingPreferences logPrefs = new LoggingPreferences();
            logPrefs.enable(LogType.BROWSER, java.util.logging.Level.ALL);
            caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);*/
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        }

        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        LOG.debug("Launching "+browser+" Browser with url : "+loginUrl);
        driver.get(loginUrl);
        loginPage = new LoginPage(driver);

        /*TODO : Will remove hardcoded username and password */
        loginPage.loginToNumberzApp("kspuwal@gmail.com","Kul@12345");

    }

    /*@BeforeMethod
    public void navigateToHomePage() throws InterruptedException {
        homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isDisplayed(),"Unable to navigate to Home page");
        homePage.navigateToInvoicesPage();
    }
*/
    @DataProvider
    public Object[][] getData() throws IOException {
        // Get test cases from csv
        testCases = CsvUtils.getTestData(System.getProperty("user.dir")
                +"/src/test/java/com/numberzApp_Tests/invoices/sendInvoice/SendInvoiceToNewCustomerTest.csv");
        return testCases;
    }

    @Test(dataProvider = "getData")
    public void sendInvoicesByAddingNewCustTest(String testDesc, String customerName,String contactPersonName,
                             String emailAddress, String phone,String address1,
                             String address2, String city,String pinCode,
                             String state,String itemName, String getFinanceOption) throws InterruptedException {
        LOG.debug("----------------------XXXXXXXXXXXXXXXX-----------------------------------");
        //LOG.logUseCase(testDesc);
        LOG.debug(testDesc);
        LOG.debug("*********************************************************************");


        //Navigate to Home Page and reusing the same WebDriver
        homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isDisplayed(), "Unable to navigate to Home page");
        homePage.navigateToInvoicesPage();
        DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd HH:mm:ss");
        Date date = new Date();
        //Reinitialising CustomerName
        customerName = customerName + "_" + dateFormat.format(date);
        System.out.println(customerName);
        LOG.debug("Navigating to Invoices Page from Home Page");

        //Navigate to Invoices Page and reusing the same WebDriver
        invoicesPage = new InvoicesPage(driver);
        //Assert.assertTrue(invoicesPage.isDisplay(),"Unable to navigate to Invoices page");
        invoicesPage.clickOnAddInvoiceButton();

        //Navigate to Tax Invoice  Page and reusing the same WebDriver
        taxInvoicePage = new TaxInvoicePage(driver);
        taxInvoicePage.clickOnAddNewCustomerOption();

        //Navigate to Customer Page and reusing the same WebDriver
        customerPage = new CustomerPage(driver);
        customerPage.enterCustomerDetailsWithSameBillingAndShippingAddress(customerName, contactPersonName, emailAddress, phone, address1, address2, city, pinCode, state);

        //Navigate to Tax Invoice Page and reusing the same WebDriver
        taxInvoicePage = new TaxInvoicePage(driver);
        taxInvoicePage.selectExistingItem(itemName);
        taxInvoicePage.clickOnSendButton();

        //Navigate to Email Modal /Page and reusing the same WebDriver
        emailPage = new EmailPage(driver);
        emailPage.clickOnExpandButton();
        //Get Email Subject from Email Modal and store it in "expectedSub" as a String
        String expectedSub = emailPage.getEmailSubject();
        emailPage.checkEmailAddrPresent();
        if (getFinanceOption.equalsIgnoreCase("OFF")) {
            emailPage.clickOnGetFinanceToggle();
        }
        emailPage.clickOnSendButton();

        //Navigate to Gmail Login  Page and reusing the same WebDriver
        gmailLoginPage = new GmailLoginPage(driver);
        /*TODO : Will remove this hardcoded email id and Password in future and get this from .csv file */
        gmailLoginPage.loginToGmail("kautoqa@gmail.com", "9056976717");
        Assert.assertTrue(gmailLoginPage.checkInvoiceReceivedOrNot(expectedSub), " Invoice email not received");
        gmailLoginPage.openGmailBasedOnSubject(expectedSub);

        if (!getFinanceOption.equalsIgnoreCase("OFF")) {
            //gmailLoginPage.clickOnGetFinanceButton();
            gmailLoginPage.clickOnGetFinanceAndSwitchToLoanWindow();

            //Navigate to Get Finance Page and reusing the same WebDriver
            getFinance = new GetFinance(driver);
            Assert.assertTrue(getFinance.isFinancePageDisplayed(), "Get Finance Page not displayed ");
            getFinance.getFinancePageDetails();
            LOG.debug("Verified Get Finance option working fine");
        }
        else{
            LOG.debug("Verified Email received Successfully without Get Finance option");
        }
    }

    @AfterMethod
    public void closeAllBrowser() throws InterruptedException {
        //Thread.sleep(5000);
        driver.close();
        driver.quit();
    }
}
