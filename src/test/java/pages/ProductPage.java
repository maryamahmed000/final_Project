package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage extends BasePage {

    private WebDriverWait wait;

    @FindBy(xpath = "//a[text()='Add to cart']")
    private WebElement addToCartButton;

    @FindBy(css = ".name")
    private WebElement productTitle;

    @FindBy(css = ".price-container")
    private WebElement productPrice;

    @FindBy(css = "#imgp img")
    private WebElement productImage;

    public ProductPage(WebDriver driver) {
        super(driver); // ✅ Fix: Only pass driver
    }

    /**
     * Clicks the "Add to Cart" button and waits for alert.
     */
    public void addToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();
        wait.until(ExpectedConditions.alertIsPresent()).accept(); // Accept alert confirmation
    }

    /**
     * Returns the product name.
     */
    public String getProductName() {
        return wait.until(ExpectedConditions.visibilityOf(productTitle)).getText();
    }

    /**
     * Returns the product price.
     */
    public String getProductPrice() {
        return wait.until(ExpectedConditions.visibilityOf(productPrice)).getText();
    }

    /**
     * Returns the product image URL.
     */
    public String getProductImage() {
        return wait.until(ExpectedConditions.visibilityOf(productImage)).getAttribute("src");
    }

    /**
     * Selects a product by name.
     */
    public void selectProduct(String productName) {
        WebElement product = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(),'" + productName + "')]")));
        product.click();
    }
}
