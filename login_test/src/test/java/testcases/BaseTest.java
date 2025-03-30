package testcases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
    protected WebDriver base_driver;



    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        base_driver = new ChromeDriver();
        base_driver.manage().window().maximize();
        base_driver.get("https://www.demoblaze.com/");
    }

    @AfterMethod
    public void tearDown() {
        if (base_driver != null) {
            base_driver.quit();
        }
    }
}