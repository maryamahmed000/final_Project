package TestCases;

import Pages.CartPage;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.List;

public class CartTestCases extends BaseTest {

    CartPage CP;

    @DataProvider(name = "cartData")
    public Object[][] getData(Method method) {
        String excelPath = "E:\\D\\Software testing\\AllCartInputs.xlsx";
        CP = new CartPage(driver);
        CP.loadExcel(excelPath, "sheet1");

        int rowCount = CP.getRowCount();
        int colCount = CP.getColCount();

        Object data[][] = new Object[rowCount - 1][colCount];

        for (int i = 1; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                data[i - 1][j] = CP.getCellData(i, j);
            }
        }
        return data;
    }

    @Test(dataProvider = "cartData", priority = 1)
    public void CartInputs(String NameInput, String CreditCardInput, String YearInput, String CountryInput,
                           String CityInput, String MonthInput) {
        //driver.get("https://www.demoblaze.com/");
        Wait<WebDriver> wait1 = new WebDriverWait(driver, Duration.ofSeconds(50));
        WebElement CartButton = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("cartur")));
        CartButton.click();
        //Wait<WebDriver> wait2 = new WebDriverWait(driver, Duration.ofSeconds(50));
        WebElement PlaceOrderButton = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
                "#page-wrapper > div > div.col-lg-1 > button")));
        PlaceOrderButton.click();
        WebElement name = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
        name.sendKeys(NameInput);
        //driver.findElement(By.id("name")).sendKeys(NameInput);
        driver.findElement(By.id("card")).sendKeys(CreditCardInput);
        driver.findElement(By.id("year")).sendKeys(YearInput);
        driver.findElement(By.id("country")).sendKeys(CountryInput);
        driver.findElement(By.id("city")).sendKeys(CityInput);
        driver.findElement(By.id("month")).sendKeys(MonthInput);
        driver.findElement(By.cssSelector
                ("#orderModal > div > div > div.modal-footer > button.btn.btn-primary")).click();
        WebElement OkConfButton = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
                "body > div.sweet-alert.showSweetAlert.visible > div.sa-button-container > div > button")));
        OkConfButton.click();
        //driver.quit();
    }

    @Test(priority = 2)
    public void CartPurchaseWithoutRequiredFields() {
        driver.findElement(By.id("cartur")).click();
        driver.findElement(By.cssSelector("#page-wrapper > div > div.col-lg-1 > button")).click();
        Wait<WebDriver> wait3 = new WebDriverWait(driver, Duration.ofSeconds(50));
        WebElement Purchase = wait3.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector
                ("#orderModal > div > div > div.modal-footer > button.btn.btn-primary")));
        Purchase.click();

        Wait<WebDriver> wait4 = new WebDriverWait(driver, Duration.ofSeconds(20));
        Alert alert = wait4.until(ExpectedConditions.alertIsPresent());
        alert.accept();

        //Close button on place order popup
        driver.findElement(By.cssSelector
                ("#orderModal > div > div > div.modal-footer > button.btn.btn-secondary")).click();

    }

    @Test(priority = 3)
    public void X_icon() {
        driver.findElement(By.cssSelector("#page-wrapper > div > div.col-lg-1 > button")).click();
        Wait<WebDriver> wait7 = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement CloseButton = wait7.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector
                ("#orderModal > div > div > div.modal-header > button > span")));
        CloseButton.click();
    }

    @Test (priority = 4)
    public void OrderConfValues(){
        driver.findElement(By.cssSelector("#navbarExample > ul > li.nav-item.active > a")).click();
        Wait<WebDriver> wait20 = new WebDriverWait(driver, Duration.ofSeconds(50));
        WebElement Button1 = wait20.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
                "#tbodyid > div:nth-child(1) > div > div > h4 > a")));
        Button1.click();
        WebElement Button2 = wait20.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
                "#tbodyid > div.row > div > a")));
        Button2.click();
        //driver.findElement(By.cssSelector("#tbodyid > div.row > div > a")).click();
        driver.findElement(By.cssSelector("#cartur")).click();
        Wait<WebDriver> wait1 = new WebDriverWait(driver, Duration.ofSeconds(50));
        WebElement PlaceOrder = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector
                ("#page-wrapper > div > div.col-lg-1 > button")));
        PlaceOrder.click();
        //driver.findElement(By.cssSelector("#page-wrapper > div > div.col-lg-1 > button")).click();

        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        WebElement NameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
        NameField.sendKeys("Salma Ayman");

        driver.findElement(By.id("card")).sendKeys("0000022");

        //Get place order popup values
        String expectedAmount = driver.findElement(By.id("totalp")).getText().trim().replaceAll
                ("[^0-9]", "");
        System.out.println(expectedAmount);
        String expectedName = driver.findElement(By.id("name")).getAttribute("value");
        String expectedCardNo = driver.findElement(By.id("card")).getAttribute("value");

        driver.findElement(By.cssSelector("#orderModal > div > div > div.modal-footer > button.btn.btn-primary")).click();

        Wait<WebDriver> wait2 = new WebDriverWait(driver, Duration.ofSeconds(50));
        WebElement ConfirmMessData = wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath
                ("/html/body/div[10]/p")));
        String Text = ConfirmMessData.getText();    //All text in confirmation message
        System.out.println("All Confirmation Message data ");
        System.out.println(Text);

        // Extract values from the confirmation message
        String[] lines = Text.split("\n");
        // Extract values
        String actualAmount = lines[1].split(": ")[1].trim().replaceAll("[^0-9]", "");
        String actualCardNumber = lines[2].split(": ")[1].trim();
        String actualName = lines[3].split(": ")[1].trim();
        //Make assertion
        Assert.assertEquals(actualAmount,expectedAmount);
        Assert.assertEquals(actualCardNumber, expectedCardNo);
        Assert.assertEquals(actualName, expectedName);
    }

    @Test (priority = 5)
    public void ValidateTotalPrice(){
        List<WebElement> priceElements = driver.findElements(By.cssSelector("#tbodyid tr td:nth-child(3)"));
        int expectedTotal = 0;
        for (WebElement priceElement : priceElements) {
            expectedTotal += Integer.parseInt(priceElement.getText().trim());
        }
        System.out.println("Sum of prices of products are: " + expectedTotal);

        // Get displayed total price
        WebElement totalPriceElement = driver.findElement(By.cssSelector("#totalp"));
        int actualTotal = Integer.parseInt(totalPriceElement.getText().trim());
        System.out.println("Total price displayed are: " + actualTotal);

        // Validate total price
        Assert.assertEquals(actualTotal, expectedTotal, "Total price validation failed!");
        System.out.println("Total price validation passed!");

    }

}
