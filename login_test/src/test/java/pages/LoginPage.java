package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {
    WebDriver driver;

    // Locators
    By loginLink = By.id("login2"); //  Locator
    By username = By.id("loginusername");
    By password = By.id("loginpassword");
    By loginButton = By.xpath("//button[text()='Log in']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }


    public void openLoginModal() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(loginLink)).click();
    }



    public void enterUsername(String user_name) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // زيادة المهلة
        wait.until(ExpectedConditions.visibilityOfElementLocated(username)).sendKeys(user_name);
    }


    public void enterPassword(String password) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(this.password)).sendKeys(password);
    }



    public void clickOnLogin() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }


    public String getAlertText() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.alertIsPresent());


        String alertText = driver.switchTo().alert().getText();


        driver.switchTo().alert().accept();

        return alertText;
    }


    public boolean isLoginSuccessful() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(loginLink));
    }
}