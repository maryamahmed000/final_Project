package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class CartTests extends BaseTest {

    private WebDriverWait wait;
    private double expectedTotalPrice = 0.0;
    private Set<String> addedProducts = new HashSet<>(); // Track added products

    @Test(priority = 1)
    public void testAddAllItemsToCartWithoutRepetition() throws InterruptedException {
        HomePage homePage = new HomePage(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        String[] categories = {"Phones", "Laptops", "Monitors"};

        for (String category : categories) {
            homePage.clickCategory(category);
            Thread.sleep(2000); // Ensure category page loads

            List<WebElement> products = driver.findElements(By.className("card-title"));
            if (products.isEmpty()) {
                Assert.fail("No products found in category: " + category);
            }

            // ✅ Loop until all products in the category are added
            for (int i = 0; i < products.size(); i += 3) {
                products = driver.findElements(By.className("card-title")); // Refresh elements after navigation
                WebElement product = products.get(i);
                String productName = product.getText();

                // ✅ Skip if product is already added
                if (addedProducts.contains(productName)) {
                    System.out.println("⚠ Skipping duplicate product: " + productName);
                    continue;
                }

                product.click(); // Open product details page

                // ✅ Wait for price element
                WebElement priceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[contains(@class, 'price-container')]")));

                // ✅ Extract and convert price
                String priceText = priceElement.getText().split(" ")[0].replaceAll("[^0-9.]", "");
                double productPrice = Double.parseDouble(priceText);
                expectedTotalPrice += productPrice;

                // ✅ Click "Add to cart" and handle alert
                driver.findElement(By.xpath("//a[text()='Add to cart']")).click();
                wait.until(ExpectedConditions.alertIsPresent());
                driver.switchTo().alert().accept();

                // ✅ Track added product to prevent repetition
                addedProducts.add(productName);
                System.out.println("✔ Added " + productName + " ($" + productPrice + ") to cart.");

                // ✅ Click "PRODUCT STORE" home button instead of back navigation
                WebElement homeButton = driver.findElement(By.id("nava"));
                homeButton.click();
                Thread.sleep(2000);

                // ✅ Ensure products are visible again before next iteration
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("card-title")));
            }
        }
    }

    @Test(priority = 2, dependsOnMethods = "testAddAllItemsToCartWithoutRepetition")
    public void testVerifyCartItems() throws InterruptedException {
        driver.findElement(By.id("cartur")).click();
        Thread.sleep(3000);

        List<WebElement> cartRows = driver.findElements(By.xpath("//tbody/tr"));
        Assert.assertEquals(cartRows.size(), addedProducts.size(), "Not all products were added to the cart!");

        double actualTotalPrice = 0.0;

        for (WebElement row : cartRows) {
            WebElement productTitle = row.findElement(By.xpath(".//td[2]"));
            WebElement productPrice = row.findElement(By.xpath(".//td[3]"));
            WebElement productImage = row.findElement(By.xpath(".//td[1]//img"));

            Assert.assertTrue(productTitle.isDisplayed(), "Product title not displayed!");
            Assert.assertTrue(productPrice.isDisplayed(), "Product price not displayed!");
            Assert.assertTrue(productImage.isDisplayed(), "Product image not displayed!");

            actualTotalPrice += Double.parseDouble(productPrice.getText());

            System.out.println("✔ Verified product: " + productTitle.getText() + " | Price: " + productPrice.getText());
        }

        System.out.println("✔ All cart items verified successfully!");
    }

    @Test(priority = 3, dependsOnMethods = "testVerifyCartItems")
    public void testVerifyTotalPrice() {
        WebElement totalPriceElement = driver.findElement(By.id("totalp"));
        double displayedTotalPrice = Double.parseDouble(totalPriceElement.getText());

        Assert.assertEquals(displayedTotalPrice, expectedTotalPrice, "Displayed total price is incorrect!");
        System.out.println("✔ Total price verified: Expected = " + expectedTotalPrice + ", Displayed = " + displayedTotalPrice);
    }

    @Test(priority = 4, dependsOnMethods = "testVerifyTotalPrice")
    public void testDeleteItemsFromCart() throws InterruptedException {
        // ✅ Initialize `wait` if not already done
        if (wait == null) {
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        }

        while (true) {
            List<WebElement> cartRows = driver.findElements(By.xpath("//tbody/tr"));

            if (cartRows.isEmpty()) {
                System.out.println("✔ Cart is now empty.");
                break; // ✅ Stop when no items are left
            }

            // ✅ Get last item (bottom-most)
            WebElement lastRow = cartRows.get(cartRows.size() - 1);
            WebElement productTitleElement = lastRow.findElement(By.xpath(".//td[2]"));
            String deletedProductName = productTitleElement.getText();

            WebElement deleteButton = lastRow.findElement(By.xpath(".//td[4]/a"));
            deleteButton.click();
            Thread.sleep(2000); // Allow deletion to process

            // ✅ Wait until the item is no longer visible
            wait.until(ExpectedConditions.stalenessOf(lastRow));

            // ✅ Assert item is removed from cart
            List<WebElement> updatedCartRows = driver.findElements(By.xpath("//tbody/tr"));
            for (WebElement row : updatedCartRows) {
                String remainingProduct = row.findElement(By.xpath(".//td[2]")).getText();
                Assert.assertNotEquals(remainingProduct, deletedProductName, "Deleted product still exists in the cart!");
            }

            System.out.println("✔ Deleted: " + deletedProductName);
        }

        // ✅ Final assertion to confirm cart is empty
        Assert.assertTrue(driver.findElements(By.xpath("//tbody/tr")).isEmpty(), "Cart is not empty!");
        System.out.println("✔ All items deleted successfully.");
    }
}
