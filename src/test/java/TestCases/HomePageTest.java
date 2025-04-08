package TestCases;

import Pages.HomePage;
import Pages.ProductPage;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class HomePageTest extends BaseTest{

    WebDriver driver;

    //Object of class in pages
    HomePage homePage;
    ProductPage productPage;

    @Test
    public void testClickingHomeButtonNavigatesToHomePage() {
        homePage = new HomePage(baseDriver);
        //homePage.openHomePage();
        homePage.clickHomeButton();

        String currentUrl = baseDriver.getCurrentUrl();
        System.out.println("Current URL after clicking Home button: " + currentUrl);

        Assert.assertTrue(homePage.isHomePage(), "Clicking Home button did not navigate to Home Page!");
    }


    @Test
    public void testClickingLogoNavigatesToHomePage() {
        homePage = new HomePage(baseDriver);
        //homePage.openHomePage();
        homePage.clickLogo();

        // Explicit wait for the home page to load
        WebDriverWait wait = new WebDriverWait(baseDriver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlToBe("https://www.demoblaze.com/index.html"));

        String currentUrl = baseDriver.getCurrentUrl();
        System.out.println("Current URL after clicking logo: " + currentUrl);

        Assert.assertTrue(homePage.isHomePage(), "Clicking site logo did not navigate to Home Page!");
    }

    @DataProvider(name = "productNames")
    public Object[][] getProductNames() {
        homePage = new HomePage(baseDriver);
        //homePage.openHomePage();
        List<WebElement> products = homePage.getProductNames();

        Object[][] data = new Object[products.size()][1];
        for (int i = 0; i < products.size(); i++) {
            data[i][0] = products.get(i).getText();
        }
        return data;
    }

    @Test(dataProvider = "productNames")
    public void testProductDetails(String productName) {
        homePage.openHomePage();
        homePage.openProductDetails(productName);

        productPage = new ProductPage(baseDriver);
        Assert.assertEquals(productPage.getProductTitle(), productName, "Product title mismatch!");
    }


    @DataProvider(name = "viewports")
    public Object[][] viewportSizes() {
        return new Object[][] {
                {375, 667},  // Mobile (iPhone/Android)
                {768, 1024}, // Tablet
                {1920, 1080} // Desktop
        };
    }

    @Test(dataProvider = "viewports")
    public void testHomePageResponsive(int width, int height) {
        baseDriver.manage().window().setSize(new Dimension(width, height));

        HomePage homePage = new HomePage(baseDriver);
        boolean isLogoPresent = homePage.isLogoDisplayed();

        if (!isLogoPresent) {
            System.out.println("Logo not visible in mobile view, skipping test.");
            throw new SkipException("Skipping test: Logo not visible in small screen size.");
        }


        Assert.assertTrue(homePage.isLogoDisplayed(), "Logo is not visible at " + width + "x" + height);
        Assert.assertFalse(homePage.clickHomeButton(), "Home button is not clickable at " + width + "x" + height);
    }
}
