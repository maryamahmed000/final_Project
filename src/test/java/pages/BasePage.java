package pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;
    protected Actions actions;

    // ✅ Ensure wait is initialized properly
    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // ✅ Fix: Always initialize WebDriverWait
        this.js = (JavascriptExecutor) driver;
        this.actions = new Actions(driver);
    }

    /**
     * Waits for an element to be visible
     */
    protected WebElement waitForElement(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Clicks an element after waiting for it
     */
    protected void click(WebElement element) {
        waitForElement(element).click();
    }

    /**
     * Enters text into an input field
     */
    protected void enterText(WebElement element, String text) {
        WebElement inputField = waitForElement(element);
        inputField.clear();
        inputField.sendKeys(text);
    }

    /**
     * Scrolls to an element
     */
    protected void scrollToElement(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
    }

    /**
     * Hovers over an element
     */
    protected void hoverOverElement(WebElement element) {
        actions.moveToElement(element).perform();
    }

    /**
     * Checks if an element is displayed
     */
    protected boolean isElementDisplayed(WebElement element) {
        try {
            return waitForElement(element).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ✅ Add getText() method
    protected String getText(WebElement element) {
        return element.getText().trim();
    }

    public void acceptAlert() {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();
    }
}
