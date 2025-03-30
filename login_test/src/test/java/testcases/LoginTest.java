package testcases;

import pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
    LoginPage loginPage;


    @Test(priority = 1)
    public void testValidLogin() {
        LoginPage loginPage = new LoginPage(base_driver);

        loginPage.openLoginModal();
        loginPage.enterUsername("Maryam Ahmed");
        loginPage.enterPassword("mero");
        loginPage.clickOnLogin();

        Assert.assertTrue(loginPage.isLoginSuccessful(), "Login failed!");
    }

    @Test(priority = 2)
    public void testLoginWithInvalidUsername() {
        loginPage = new LoginPage(base_driver);
        loginPage.openLoginModal();
        loginPage.enterUsername("non_existing_user");
        loginPage.enterPassword("mero");
        loginPage.clickOnLogin();
        String alertText = loginPage.getAlertText();
        Assert.assertTrue(alertText.contains("User does not exist"), "Expected: User does not exist | Actual: " + alertText);
    }

   /* @Test(priority = 3)
    public void testLoginWithInvalidPassword() {
        LoginPage loginPage = new LoginPage(base_driver);
        loginPage.openLoginModal();

        loginPage.enterUsername("MaryanAhmed");
        loginPage.enterPassword("88888");
        loginPage.clickOnLogin();

        String alertText = loginPage.getAlertText();
        Assert.assertTrue(
                alertText.contains("Invalid password"), // تعديل النص هنا ليطابق التنبيه الفعلي
                "Expected: Invalid password | Actual: " + alertText
        );
    }*/



    @Test(priority = 4)
    public void testLoginWithEmptyUsername() {
        loginPage = new LoginPage(base_driver);
        loginPage.openLoginModal(); // فتح النافذة تلقائيًا
        loginPage.enterUsername("");
        loginPage.enterPassword("mero");
        loginPage.clickOnLogin();
        String alertText = loginPage.getAlertText();
        Assert.assertTrue(alertText.contains("Please fill out Username"), "Expected: Username required | Actual: " + alertText);
    }

    @Test(priority = 5)
    public void testLoginWithEmptyPassword() {
        loginPage = new LoginPage(base_driver);


        loginPage.openLoginModal();

        loginPage.enterUsername("MaryanAhmed");
        loginPage.enterPassword("");
        loginPage.clickOnLogin();


        String alertText = loginPage.getAlertText();
        Assert.assertTrue(
                alertText.contains("Please fill out Username and Password"),
                "Expected: Please fill out Username and Password | Actual: " + alertText
        );
    }
}