package TestCases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

    public WebDriver baseDriver;

    @BeforeSuite
    public void beforeClass(){
        baseDriver = new ChromeDriver();
        baseDriver.manage().window().maximize();
        baseDriver.get("https://www.demoblaze.com/");

    }

    @AfterSuite
    public void afterClass(){
        if (baseDriver != null) {
            baseDriver.quit();
        }
    }

}
