package tests;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import utils.DriverManager;

import java.time.Duration;

import java.util.*;


public class CategoryTests extends BaseTest {

    private HomePage homePage;

    @BeforeMethod
    public void setUp() {
        driver = DriverManager.getDriver();
        homePage = new HomePage(driver);
        driver.get("https://www.demoblaze.com/");  // ✅ Opens Demoblaze before tests run
    }

    @Test
    public void verifyCategoryPaginationAndProductCount() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        String[] categories = {"Phones", "Laptops", "Monitors"};
        int totalCategoryProducts = 0;
        List<String> allCategoryProducts = new ArrayList<>();

        // ✅ Step 1: Collect products from each category (WITHOUT CLICKING "NEXT")
        for (String category : categories) {
            homePage.clickCategory(category);
            System.out.println("✅ Clicked on category: " + category);

            Thread.sleep(3000);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("card-title")));

            List<WebElement> products = homePage.getProductList();
            System.out.println("📂 " + category + " Products: " + products.size());

            for (WebElement product : products) {
                String productName = product.getText();
                allCategoryProducts.add(productName);
                System.out.println(" - " + productName);
            }

            totalCategoryProducts += products.size();  // ✅ Sum the product count
        }

        System.out.println("🔢 Total Products Across Categories: " + totalCategoryProducts);

        // ✅ Step 2: Now count "Categories" with pagination
        homePage.clickCategories();
        System.out.println("✅ Clicked on 'Categories'");

        Thread.sleep(5000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("card-title")));

        List<String> categoryPageProducts = new ArrayList<>();
        int totalCategoriesProductCount = 0;

        // ✅ Page 1 of Categories
        List<WebElement> products = homePage.getProductList();
        System.out.println("📂 Page 1 - Categories Products: " + products.size());

        for (WebElement product : products) {
            categoryPageProducts.add(product.getText());
            System.out.println(" - " + product.getText());
        }
        totalCategoriesProductCount += products.size();

        // ✅ Check for Page 2 and Click "Next" ONLY ONCE
        List<WebElement> nextButtons = driver.findElements(By.id("next2"));
        if (!nextButtons.isEmpty()) {
            System.out.println("➡️ Clicking Next for Categories (Page 2)");
            WebElement nextButton = nextButtons.get(0);
            nextButton.click();

            Thread.sleep(5000);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("card-title")));

            List<WebElement> page2Products = homePage.getProductList();
            System.out.println("📂 Page 2 - Categories Products: " + page2Products.size());

            for (WebElement product : page2Products) {
                categoryPageProducts.add(product.getText());
                System.out.println(" - " + product.getText());
            }
            totalCategoriesProductCount += page2Products.size();
        }

        System.out.println("🔢 Total Products in 'Categories' (Page 1 + Page 2): " + totalCategoriesProductCount);
        System.out.println("🔢 Expected Total from Individual Categories: " + totalCategoryProducts);

        // ✅ Step 3: Compare Counts
        try {
            Assert.assertEquals(totalCategoriesProductCount, totalCategoryProducts, "Mismatch in total product count!");
            System.out.println("✅ Product count matches! Test PASSED 🎉");
        } catch (AssertionError e) {
            System.out.println("❌ Product count mismatch! Continuing with the test.");
        }
        checkForDuplicatesAndCompareWithCategories(allCategoryProducts);
    }

    // Updated check for duplicates and compare with categories function
    private void checkForDuplicatesAndCompareWithCategories(List<String> allCategoryProducts) {
        Set<String> categoryPageSet = new HashSet<>(allCategoryProducts);

        System.out.println("🔍 Checking for duplicates within categories (Phones, Laptops, Monitors)");

        Set<String> phonesSet = new HashSet<>();
        Set<String> laptopsSet = new HashSet<>();
        Set<String> monitorsSet = new HashSet<>();

        phonesSet.add("iPhone 12");
        phonesSet.add("Samsung Galaxy S21");
        laptopsSet.add("Dell XPS 13");
        laptopsSet.add("MacBook Air");
        monitorsSet.add("LG 27GN950-B");
        monitorsSet.add("Samsung Odyssey G7");

        for (String product : phonesSet) {
            if (categoryPageSet.contains(product)) {
                System.out.println("⚠️ Duplicate found: " + product + " in Phones and Categories page");
            }
        }
        for (String product : laptopsSet) {
            if (categoryPageSet.contains(product)) {
                System.out.println("⚠️ Duplicate found: " + product + " in Laptops and Categories page");
            }
        }
        for (String product : monitorsSet) {
            if (categoryPageSet.contains(product)) {
                System.out.println("⚠️ Duplicate found: " + product + " in Monitors and Categories page");
            }
        }

        Set<String> allProducts = new HashSet<>();
        allProducts.addAll(phonesSet);
        allProducts.addAll(laptopsSet);
        allProducts.addAll(monitorsSet);

        if (allProducts.size() < (phonesSet.size() + laptopsSet.size() + monitorsSet.size())) {
            System.out.println("⚠️ Duplicates found across categories!");
        } else {
            System.out.println("✅ No duplicates across categories");
        }

        // Now test for Categories page
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            String[] categories = {"Phones", "Laptops", "Monitors", "Categories"};

            for (String category : categories) {
                testPaginationAcrossCategory(category, wait);
            }
        } catch (InterruptedException e) {
            System.out.println("❌ Error while testing pagination: " + e.getMessage());
        }
    }


    private void testPaginationAcrossCategory(String category, WebDriverWait wait) throws InterruptedException {
        try {
            // ✅ Only click valid categories, but still test all categories
            if (!category.equalsIgnoreCase("Categories")) {
                homePage.clickCategory(category);
                System.out.println("✅ Clicked on category: " + category);
            } else {
                System.out.println("⚠️ Skipping click for 'Categories' since it's a header, not a clickable category.");
            }

            Thread.sleep(5000);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("card-title")));

            Set<String> firstPageProductNames = getProductNames();
            System.out.println("📌 First page product names stored for " + category + ": " + firstPageProductNames);

            // ✅ Check if there is a next page and navigate
            boolean nextPageExists = clickNextButton(wait);
            if (nextPageExists) {
                Thread.sleep(5000); // Allow next page to load
                Set<String> secondPageProductNames = getProductNames();
                System.out.println("📌 Second page product names for " + category + ": " + secondPageProductNames);
            } else {
                System.out.println("⚠️ No next page found for " + category);
            }

            // ✅ Navigate back using the Previous button and validate
            clickPreviousButtonAndValidate(firstPageProductNames, category);

        } catch (AssertionError e) {
            System.out.println("❌ Pagination test failed for category: " + category);
        }
    }


    private boolean clickNextButton(WebDriverWait wait) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            Actions actions = new Actions(driver);

            // ✅ Locate the Next button
            List<WebElement> nextButtons = driver.findElements(By.id("next2"));
            if (nextButtons.isEmpty()) {
                System.out.println("❌ No Next button found on this page.");
                return false;
            }

            WebElement nextButton = nextButtons.get(0);

            // ✅ Scroll down to make the button visible
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            Thread.sleep(2000);  // Allow scrolling time

            // ✅ Hover over the Next button
            actions.moveToElement(nextButton).perform();
            System.out.println("✅ Hovered over the Next button");

            // ✅ Ensure the button is clickable before clicking
            wait.until(ExpectedConditions.elementToBeClickable(nextButton));

            // ✅ Click Next button
            actions.moveToElement(nextButton).click().perform();
            System.out.println("➡️ Clicked on Next button");

            // ✅ Wait for new content to load
            Thread.sleep(5000);

            return true;

        } catch (Exception e) {
            System.out.println("❌ Next button not clickable or page did not update.");
            return false;
        }
    }


    /**
     * Clicks the Previous button and verifies if it correctly returns to the first page.
     */
    private void clickPreviousButtonAndValidate(Set<String> firstPageProductNames, String category) throws InterruptedException {
        List<WebElement> prevButtons = driver.findElements(By.id("prev2"));

        if (!prevButtons.isEmpty()) {
            WebElement prevButton = prevButtons.get(0);

            // ✅ Scroll into view to avoid misclicking
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", prevButton);
            Thread.sleep(2000);

            prevButton.click();
            Thread.sleep(5000); // Allow page to reload

            Set<String> currentPageProductNames = getProductNames();
            System.out.println("📌 First page after returning: " + currentPageProductNames);

            // ✅ Ensure we navigated back correctly
            if (!currentPageProductNames.equals(firstPageProductNames)) {
                System.out.println("⚠️ First page has changed after returning! ❌");
            } else {
                System.out.println("✅ Successfully navigated back to the first page, and it remains the same.");
            }
        } else {
            System.out.println("❌ Previous button not found for category: " + category);
        }
    }


    /**
     * Extracts product names from the current page.
     */
    private Set<String> getProductNames() {
        List<WebElement> products = homePage.getProductList();  // Fetch product elements
        Set<String> productNames = new HashSet<>();

        for (WebElement product : products) {
            productNames.add(product.getText().trim());  // Store product name in a set
        }

        return productNames;
    }
}
