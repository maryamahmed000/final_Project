package Pages;

import TestCases.BaseTest;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class ContactPage extends BaseTest {

    public WebDriver driver;
    public ContactPage(WebDriver driver) {
        this.driver = driver;
    }

    private final String contactButtonCSS = "#navbarExample > ul > li:nth-child(2) > a";
    private final String contactEmailTextboxCSS = "#recipient-email";
    private final String contactNameTextboxCSS = "#recipient-name";
    private final String messageTextboxCSS = "#message-text";
    private final String sendMessageButtonCSS = "#exampleModal > div > div > div.modal-footer > button.btn.btn-primary";
    private final String closeButtonCSS = "#exampleModal > div > div > div.modal-footer > button.btn.btn-secondary";
    private final String closeXsignCSS = "#exampleModal > div > div > div.modal-header > button";

    public boolean waitThenClickCSS(String buttonCSS, int timeInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeInSeconds));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(buttonCSS))); // انتظر حتى يصبح العنصر قابلًا للنقر
        if (button.isDisplayed() && button.isEnabled()) {
            button.click();
            return true;
        } else {
            System.out.println(" الزر غير متاح.");
            return false;
        }
    }

    public void waitThenWrite(String textBox_Position_CSS, String data, int timeInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeInSeconds));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(textBox_Position_CSS))).sendKeys(data);
    }

    public void contact(String contactEmail, String contactName, String message)
    {
        waitThenClickCSS(contactButtonCSS, 10);
        waitThenWrite(contactEmailTextboxCSS, contactEmail, 10);
        waitThenWrite(contactNameTextboxCSS, contactName, 10);
        waitThenWrite(messageTextboxCSS, message, 10);
        waitThenClickCSS(sendMessageButtonCSS, 10);
    }

    public void contactValidation(String contactEmail, String contactName, String message, String testType) throws InterruptedException {
        contact(contactEmail, contactName, message);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String actualAlertText = alert.getText();
        alert.accept();
        switch (testType) {
            case "valid":
                System.out.println("⚠️ اختبار ارسال رسالة بفورمات بيانات صحيحة:");
                Assert.assertEquals(actualAlertText, "Thanks for the message!!");
                System.out.println("✅ الاختبار ناجح: تم ارسال رسالة بفورمات بيانات صحيحة");
                break;

            case "invalidContactEmail":
                System.out.println("⚠️ اختبار ارسال رسالة بفورمات بريد غير صحيح:");
                Assert.assertTrue(!actualAlertText.equals("Thanks for the message!!"),
                        "❌ الاختبار فشل: تم ارسال رسالة بفورمات بريد غير صحيح!");
                System.out.println("✅ الاختبار ناجح: لم يتم ارسال رسالة بفورمات بريد غير صحيح");
                break;

            case "invalidContactName":
                System.out.println("⚠️ اختبار ارسال رسالة بفورمات اسم مرسل غير صحيح:");
                Assert.assertTrue(!actualAlertText.equals("Thanks for the message!!"),
                        "❌ الاختبار فشل: تم ارسال رسالة بفورمات اسم غير صحيح!");
                System.out.println("✅ الاختبار ناجح: لم يتم ارسال رسالة بفورمات اسم غير صحيح");
                break;

            case "invalidMessage":
                System.out.println("⚠️ اختبار ارسال رسالة بفورمات رسالة غير صحيحة:");
                Assert.assertTrue(!actualAlertText.equals("Thanks for the message!!"),
                        "❌ الاختبار فشل: تم ارسال رسالة بفورمات رسالة غير صحيحة!");
                System.out.println("✅ الاختبار ناجح: لم يتم ارسال رسالة بفورمات رسالة غير صحيحة");
                break;

            default:
                System.out.println("❌ نوع الاختبار غير معروف: " + testType);
                Assert.fail("TestType غير معرف في البيانات!");
                break;
        }
    }

    public void closingValidation(String closignType)
    {
        waitThenClickCSS(contactButtonCSS,10);
        if(closignType.equals("x")) {
            System.out.println("⚠️ اختبار الضغط علي زر X لغلق صفحة ال Contact ");
            boolean check = waitThenClickCSS(closeXsignCSS, 10);
            if(check) {
                Assert.assertTrue(true);
                System.out.println("✅ الاختبار ناجح: تم غلق صفحة ال Contact");
            }
            else {
                Assert.fail();
                System.out.println("❌ الاختبار فشل : لم يتم غلق صفحة ال Contact");
            }
        }
        else {
            System.out.println("⚠️ اختبار الضغط علي زر Close لغلق صفحة ال  Contact ");
            boolean check = waitThenClickCSS(closeButtonCSS, 10);
            if(check) {
                Assert.assertTrue(true);
                System.out.println("✅ الاختبار ناجح: تم غلق صفحة ال Contact ");
            }
            else {
                Assert.fail();
                System.out.println("❌ الاختبار فشل : لم يتم غلق صفحة ال Contact");
            }
        }
    }

    public void clickingOnSendMessageButtonWithEmptyDataValidation() {
        System.out.println("⚠️ اختبار الضغط علي زر send message مع عدم ادخال اي بيانات ");
        waitThenClickCSS(contactButtonCSS, 10);
        waitThenClickCSS(sendMessageButtonCSS, 10);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String actualAlertText = alert.getText();
        alert.accept();
        if (!actualAlertText.equals("Thanks for the message!!")) {
            Assert.assertTrue(true);
            System.out.println("✅ الاختبار ناجح: تم رفض ارسال رساله ببيانات فارغه ");
        } else {
            System.out.println("❌ الاختبار فشل : لم يتم رفض ارسال رساله ببيانات فارغه");
            Assert.fail();
        }
    }

}
