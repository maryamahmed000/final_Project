package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage {

    WebDriver driver;
    WebDriverWait wait;

    // Locators
    By logo = By.id("nava"); // Site logo
    By homeButton = By.xpath("//*[@id=\"navbarExample\"]/ul/li[1]/a");
    By productNames = By.cssSelector(".card-title a");

    // Constructor
    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Get all product names
    public List<WebElement> getProductNames() {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productNames));
    }

    // Click on a specific product to open details page
    public void openProductDetails(String productName) {
        for (WebElement product : getProductNames()) {
            if (product.getText().equalsIgnoreCase(productName)) {
                product.click();
                return;
            }
        }
        throw new RuntimeException("Product not found: " + productName);
    }

    // Click the Home button
    public boolean clickHomeButton() {
        driver.manage().window().maximize();
        wait.until(ExpectedConditions.elementToBeClickable(homeButton)).click();
        return false;
    }

    // Click the logo to go back to Home
    public void clickLogo() {
        driver.manage().window().maximize();
        wait.until(ExpectedConditions.elementToBeClickable(logo)).click();
    }

    // Check if logo is displayed for testHomePageResponsive
    public boolean isLogoDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Increase timeout
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(logo)).isDisplayed();
        } catch (TimeoutException e) {
            System.out.println("Logo not found within timeout. Test might be failing due to viewport size.");
            return false;
        }
    }

    // Open the homepage
    public void openHomePage() {
        driver.get("https://www.demoblaze.com/");
    }

    // Verify if current URL is home page
    public boolean isHomePage() {
        String currentUrl = driver.getCurrentUrl();
        return currentUrl.equals("https://www.demoblaze.com/") || currentUrl.equals("https://www.demoblaze.com/index.html");
    }
}
