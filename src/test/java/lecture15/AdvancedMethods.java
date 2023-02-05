package lecture15;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
public class AdvancedMethods {
    WebDriver driver;

    @AfterMethod
    void cleanUp() {
        this.driver.quit();
    }

    @BeforeMethod
    void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void addRemoveElements(){
        driver.get("https://the-internet.herokuapp.com/add_remove_elements/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement text =driver.findElement(By.tagName("h3"));
        wait.until(ExpectedConditions.visibilityOf(text));

        WebElement addButton= driver.findElement(By.cssSelector("*[onclick='addElement()']"));
        addButton.click();

        WebElement removeElement= driver.findElement(By.cssSelector("button[class='added-manually']"));
        wait.until(ExpectedConditions.visibilityOf(removeElement));
        removeElement.click();

        Assert.assertFalse(removeElement.isDisplayed());
    }

    @Test
    public void basicAuth(){
       driver.get("http://admin:admin@the-internet.herokuapp.com/basic_auth");

        WebElement text= driver.findElement(By.xpath("//p"));
        Assert.assertEquals(text.getText(),"Congratulations! You must have the proper credentials.");
    }

    @Test
    public void challengingDOM () {
        driver.get("http://the-internet.herokuapp.com/challenging_dom");
        WebElement button1 = driver.findElement(By.xpath("//a[@class='button']"));

        button1.click();
        WebElement button2 = driver.findElement(By.xpath("//a[@class='button alert']"));

        button2.click();

        WebElement button3 = driver.findElement(By.xpath("//a[@class='button success']"));

        button3.click();

        List<WebElement> columnHeaders = driver.findElements(By.xpath("//table/thead//th"));
        int tableColumnsCount = columnHeaders.size();
        List<WebElement> cells = driver.findElements(By.xpath("//table/tbody//td"));
        int cellsCount = cells.size();
        int rowsCount = cellsCount / tableColumnsCount;
        String table[][] = new String[rowsCount][tableColumnsCount];
        WebElement tableElements[][] = new WebElement[rowsCount][tableColumnsCount];

        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < tableColumnsCount; j++) {
                String currXPath = String.format("//table/tbody//tr[%s]//td[%s]", i + 1, j + 1);
                table[i][j] = driver.findElement(By.xpath(currXPath)).getText();
                tableElements[i][j] = driver.findElement(By.xpath(currXPath));

            }
        }
    }

    @Test
    public void checkboxes(){
        driver.get("https://the-internet.herokuapp.com/checkboxes");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement text =driver.findElement(By.xpath("//h3"));
        Assert.assertEquals(text.getText(), "Checkboxes");

        WebElement label=driver.findElement(By.xpath("//form//input[@type='checkbox']"));
        label.click();
        Assert.assertTrue(label.isSelected());

        WebElement label2= driver.findElement(By.xpath("//input[@type='checkbox']/following-sibling::input"));
        Assert.assertTrue(label2.isSelected());
        label2.click();
        Assert.assertTrue(!label2.isSelected());

       /* WebElement checkbox1 = driver.findElement(By.xpath("(//form/input[@type='checkbox'])[1]"));
        boolean checkbox1State = checkbox1.isSelected();
        checkbox1.click();
        WebElement checkbox2 = driver.findElement(By.xpath("(//form/input[@type='checkbox'])[2]"));
        boolean checkbox2State = checkbox2.isSelected();
        checkbox2.click();
        Assert.assertEquals(checkbox1State, !checkbox1.isSelected());
        Assert.assertEquals(checkbox2State, !checkbox2.isSelected());*/

    }

    @Test //pop up window alert
    public void contextMenu(){
        driver.get("https://the-internet.herokuapp.com/context_menu");

        // Right click the button to launch right click menu options
        Actions action = new Actions(driver);

        WebElement menu = driver.findElement(By.cssSelector("#hot-spot"));
        action.contextClick(menu).perform();

        // Accept the alert displayed
        driver.switchTo().alert().accept();
    }

    @Test
    public void disappearingElements(){
        driver.get("https://the-internet.herokuapp.com/disappearing_elements");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement text =driver.findElement(By.cssSelector("#content > div > h3"));
        wait.until(ExpectedConditions.visibilityOf(text));

        WebElement home = driver.findElement(By.linkText("Home"));
        home.click();
        wait.until(ExpectedConditions.urlToBe("https://the-internet.herokuapp.com/"));
        driver.navigate().back();

        WebElement about = driver.findElement(By.linkText("About"));
        about.click();
        wait.until(ExpectedConditions.urlToBe("https://the-internet.herokuapp.com/about/"));
        driver.navigate().back();

        WebElement contactUs = driver.findElement(By.linkText("Contact Us"));
        contactUs.click();
        wait.until(ExpectedConditions.urlToBe("https://the-internet.herokuapp.com/contact-us/"));
        driver.navigate().back();

        WebElement portfolio = driver.findElement(By.linkText("Portfolio"));
        portfolio.click();
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl, "https://the-internet.herokuapp.com/portfolio/" );
        driver.navigate().back();

        wait.until(ExpectedConditions.urlToBe("https://the-internet.herokuapp.com/disappearing_elements"));
        WebElement gallery = driver.findElement(By.linkText("Gallery"));
        gallery.click();
        String actualUrl2 = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl2, "https://the-internet.herokuapp.com/gallery/" );
        driver.navigate().back();

    }

}