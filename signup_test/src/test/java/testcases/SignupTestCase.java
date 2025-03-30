package testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.SignupPage;

import java.time.Duration;

public class SignupTestCase extends BaseTest {
    @Test
    public void testSignupWithValidCredentials() {
        SignupPage signupPage = new SignupPage(driver);


        signupPage.openSignupModal();


        String username = "Moamen" + System.currentTimeMillis(); // اسم مستخدم فريد
        signupPage.enterUsername(username);
        signupPage.enterPassword("12345");
        signupPage.clickSignupButton();


        String alertText = signupPage.getAlertText();
        Assert.assertTrue(alertText.contains("Sign up successful"), "Signup failed: " + alertText);
    }

    @Test
    public void testSignupWithExistingUsername() {
        SignupPage signupPage = new SignupPage(driver);


        signupPage.openSignupModal();


        signupPage.enterUsername("Maryam Ahmed"); // اسم مستخدم مسجل مسبقًا
        signupPage.enterPassword("mero");
        signupPage.clickSignupButton();


        String alertText = signupPage.getAlertText();
        Assert.assertTrue(alertText.contains("This user already exist"), "Expected: User exists | Actual: " + alertText);
    }

    @Test
    public void testSignupWithEmptyFields() {
        SignupPage signupPage = new SignupPage(driver);


        signupPage.openSignupModal();
        signupPage.enterUsername("");
        signupPage.enterPassword("");
        signupPage.clickSignupButton();

        String alertText = signupPage.getAlertText();
        Assert.assertTrue(alertText.contains("Please fill out Username and Password"), "Expected: Fields required | Actual: " + alertText);
    }


    @Test
    public void testCloseSignupModalWithoutData() {
        SignupPage signupPage = new SignupPage(driver);
        signupPage.openSignupModal();

        // إغلاق النافذة يدويًا (مثال باستخدام زر الإغلاق "X")
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#signInModal .btn-secondary"))).click();

        // التحقق من إغلاق النافذة
        boolean isModalClosed = wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("signInModal")));
        Assert.assertTrue(isModalClosed, "Modal did not close");
    }



}
