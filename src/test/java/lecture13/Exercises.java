package lecture13;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class Exercises {

        public static final String PASSWORD = "Password1.1Drowssap";
        private WebDriver driver;

        @BeforeSuite
        protected final void setupTestSuite() {
            WebDriverManager.chromedriver().setup();
           /* WebDriverManager.firefoxdriver().setup();
            WebDriverManager.edgedriver().setup();*/
        }

        @BeforeMethod
        protected final void setUpTest() {
            this.driver = new ChromeDriver();
            this.driver.manage().window().maximize();
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        }

        @AfterMethod
        protected final void tearDownTest() {
            if (this.driver != null) {
                this.driver.close();
            }
        }

        @DataProvider(name = "getRegisteredUsers")
        public Object[][] getUsers() {
            return new Object[][]{{"DimitarTarkalanov", "Dimitar1.Tarkalanov1", "DimitarTarkalanov"}, //login with username
                   /* {"testMail1@gmail.com", "Dimitar1.Tarkalanov1", "DimitarTarkalanov"}, //login with email
                    {"testAdmin@gmail.com", "Admin1.User1", "AdminUser"}, //login with admin user
                    {"manager@gmail.com", "Manager1.Use1", "ManagerUser"} //login with manager user*/
            };
        }


        @Test(groups = "second",dataProvider = "getRegisteredUsers")
        public void testLogoutFunctionality(String user, String password, String name) {
            driver.get("http://training.skillo-bg.com:4300/posts/all");
            WebElement loginLink = driver.findElement(By.id("nav-link-login"));
            loginLink.click();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.urlToBe("http://training.skillo-bg.com:4300/users/login"));


            WebElement signInElement = driver.findElement(By.xpath("//*[text()='Sign in']"));
            wait.until(ExpectedConditions.visibilityOf(signInElement));

            WebElement userNameField = driver.findElement(By.id("defaultLoginFormUsername"));
            userNameField.sendKeys(user);

            WebElement passwordField = driver.findElement(By.id("defaultLoginFormPassword"));
            passwordField.sendKeys(password);

            WebElement signInButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("sign-in-button")));
            signInButton.click();

            WebElement profileLink = wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-link-profile")));
            profileLink.click();

            wait.until(ExpectedConditions.urlContains("http://training.skillo-bg.com:4300/users/"));



            WebElement logout =driver.findElement(By.xpath("//ul[@class='navbar-nav my-ml d-none d-md-block']//li[@class='nav-item ng-star-inserted']//a[@class='nav-link']"));
            By locator=By.xpath("//ul[@class='navbar-nav my-ml d-none d-md-block']//li[@class='nav-item ng-star-inserted']//a[@class='nav-link']");

                    wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            logout.click();
            wait.until(ExpectedConditions.visibilityOf(signInElement));

            Boolean isTextDisplayed = wait.until(ExpectedConditions.textToBe(By.tagName("h2"), name));
            Assert.assertTrue(isTextDisplayed, "The signIn form is not displayed!");


        }

}
