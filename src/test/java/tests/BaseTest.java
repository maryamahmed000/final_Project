package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import utils.DriverManager;

public class BaseTest {
    protected WebDriver driver;
    protected String baseUrl = "https://www.demoblaze.com/#"; // 🔥 Website URL here

    @BeforeClass
    public void setUp() {
        driver = DriverManager.getDriver();
        driver.get(baseUrl); // ✅ Open website before tests
    }

    @AfterClass
    public void tearDown() {
        DriverManager.quitDriver(); // ✅ Close browser after tests
    }
}