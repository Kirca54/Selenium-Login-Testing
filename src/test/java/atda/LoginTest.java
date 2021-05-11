package atda;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import po.LoginPage;
import po.ProductPage;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
//chromedriverot go izvadiv od proektot deka mnogu zafakja, a na stranata mozma samo 5MB da prikacam
public class LoginTest {

    private WebDriver driver;

    @BeforeTest
    public void setup() {
        driver = getDriver();
    }


    @Test
    public void TestTest(){
        driver.get("https://www.facebook.com/");
        final WebElement username = driver.findElement(By.id("email"));
        username.sendKeys("mailtest");
    }

    //ist error message dava ako ima empty username ili ako se pogresni username i password vneseni
    //T2 empty username
    @Test
    public void EmptyUsername() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        assertTrue(loginPage.isLoaded());
        loginPage.login("", "blablabla");
        String errorMessage = loginPage.getErrorMessage();
        assertEquals(errorMessage, "The email or mobile number you entered isn’t connected to an account. Find your account and log in.");
    }

    //T3 successful login
    @Test
    public void shouldLogin() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        assertTrue(loginPage.isLoaded());
        loginPage.login("david.kirovski@gmail.com", "testtest");// ova mi pomina so mojot fb password
        assertTrue(new ProductPage(driver).isLoaded());
    }

    //T1 invalid credentials
    @Test
    public void canNotLoginWithInvalidUserName() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        assertTrue(loginPage.isLoaded());
        loginPage.login("standard_user", "secret");
        String errorMessage = loginPage.getErrorMessage();
        assertEquals(errorMessage, "The email or mobile number you entered isn’t connected to an account. Find your account and log in.");

    }

    private WebDriver getDriver() {
        System.setProperty("webdriver.chrome.driver","src/main/resources/drivers/chromedriver.exe");
        return new ChromeDriver();
    }

    @AfterTest
    public void teardown() {
        driver.quit();
    }

}
