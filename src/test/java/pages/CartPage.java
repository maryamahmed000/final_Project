package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.By;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CartPage extends BasePage {

    public CartPage(WebDriver driver) {
        super(driver); // ✅ Fix: Only pass driver
    }



    @FindBy(id = "cartur") // ✅ Updated to correctly open the cart
    private WebElement cartButton;

    @FindBy(id = "totalp") // ✅ Matches the total price element
    private WebElement totalPrice;



    /**
     * Opens the cart page.
     */
    public void openCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartButton)).click();
    }

    /**
     * Retrieves the names of all products in the cart.
     */
    public Set<String> getCartProductNames() {
        List<WebElement> cartItems = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//td[2]")));
        return cartItems.stream().map(WebElement::getText).collect(Collectors.toSet());
    }

    /**
     * Calculates the expected total price from a list of product prices.
     */
    public int getExpectedTotal(List<Integer> productPrices) {
        return productPrices.stream().mapToInt(Integer::intValue).sum();
    }

    /**
     * Retrieves the total cart price displayed.
     */
    public double getTotalCartPrice() {
        String totalPriceText = wait.until(ExpectedConditions.visibilityOf(totalPrice)).getText().trim();
        try {
            return Double.parseDouble(totalPriceText);
        } catch (NumberFormatException e) {
            throw new RuntimeException("❌ Failed to parse total cart price: " + totalPriceText);
        }
    }

    /**
     * Gets all product names in a List format.
     */
    public List<String> getCartProductNamesList() {
        return driver.findElements(By.xpath("//td[2]")).stream().map(WebElement::getText).toList();
    }

    /**
     * Checks if the cart is empty.
     */
    public boolean isCartEmpty() {
        return driver.findElements(By.xpath("//td[2]")).isEmpty();
    }

    /**
     * Checks if a specific product exists in the cart.
     */
    public boolean isProductInCart(String productName, int expectedPrice, String expectedImgUrl) {
        List<WebElement> cartRows = driver.findElements(By.cssSelector("#tbodyid tr"));

        for (WebElement row : cartRows) {
            String title = row.findElement(By.xpath("./td[2]")).getText();
            String price = row.findElement(By.xpath("./td[3]")).getText();
            String imgSrc = row.findElement(By.tagName("img")).getAttribute("src");

            if (title.equals(productName) && Integer.parseInt(price) == expectedPrice && imgSrc.equals(expectedImgUrl)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes specific products from the cart, starting from the bottom.
     */
    public void removeSpecificItems(Set<String> addedProducts) {
        List<WebElement> cartRows = driver.findElements(By.cssSelector("#tbodyid tr"));

        for (int i = cartRows.size() - 1; i >= 0; i--) { // Remove from bottom to top
            WebElement row = cartRows.get(i);
            String title = row.findElement(By.xpath("./td[2]")).getText();

            if (addedProducts.contains(title)) {
                row.findElement(By.xpath(".//td[4]/a")).click(); // Click delete button
                addedProducts.remove(title);
                wait.until(ExpectedConditions.stalenessOf(row)); // Wait for removal
            }
        }
    }
}