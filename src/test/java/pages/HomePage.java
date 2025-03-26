package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;


public class HomePage extends BasePage {
    private By phonesCategory = By.xpath("//a[contains(text(),'Phones')]");
    private By laptopsCategory = By.xpath("//a[contains(text(),'Laptops')]");
    private By monitorsCategory = By.xpath("//a[contains(text(),'Monitors')]");
    private By allProducts = By.className("card-title");
    private By nextButton = By.id("next2");
    private By cartButton = By.id("cartur");
    private By searchBox = By.id("searchBox");


    public HomePage(WebDriver driver) {
        super(driver); // ✅ Fix: Only pass driver
    }



    public void clickCategory(String category) {
        By categoryLocator;

        switch (category.toLowerCase()) {
            case "phones":
                categoryLocator = phonesCategory;
                break;
            case "laptops":
                categoryLocator = laptopsCategory;
                break;
            case "monitors":
                categoryLocator = monitorsCategory;
                break;
            default:
                throw new IllegalArgumentException("Invalid category: " + category);
        }

        wait.until(ExpectedConditions.elementToBeClickable(categoryLocator)).click();
    }

    public List<WebElement> getProductList() {
        return driver.findElements(allProducts);
    }

    public boolean isNextButtonPresent() {
        return !driver.findElements(nextButton).isEmpty() && driver.findElement(nextButton).isDisplayed();
    }

    public void clickNextButton() {
        if (isNextButtonPresent()) {
            wait.until(ExpectedConditions.elementToBeClickable(nextButton)).click();
        }
    }

    public void searchAndSelectProduct(String productName) {
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox));
        searchInput.clear();
        searchInput.sendKeys(productName, Keys.RETURN);

        wait.until(ExpectedConditions.visibilityOfElementLocated(allProducts));

        List<WebElement> productResults = driver.findElements(allProducts);
        for (WebElement product : productResults) {
            if (product.getText().equalsIgnoreCase(productName)) {
                wait.until(ExpectedConditions.elementToBeClickable(product)).click();
                return;
            }
        }

        throw new NoSuchElementException("Product '" + productName + "' not found in search results!");
    }

    public void goToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartButton)).click();
    }

    public void open() {
        driver.get("https://www.demoblaze.com/");
    }

    public void selectProduct(String productName) {
        By productLocator = By.xpath("//a[contains(text(),'" + productName + "')]");
        wait.until(ExpectedConditions.elementToBeClickable(productLocator)).click();
    }
    public void clickCategories() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5)); // ✅ Wait for element to be clickable
        WebElement categoriesButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("cat"))); // ✅ Ensure it is found
        categoriesButton.click();
    }
}