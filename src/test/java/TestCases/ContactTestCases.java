package TestCases;

import Pages.ContactPage;
import Pages.ExcelUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.IOException;


public class ContactTestCases extends BaseTest{

    ContactPage contactPage; // لم يتم تهيئته هنا

    @BeforeMethod
    public void setup() {
        contactPage = new ContactPage(baseDriver); // قم بتمرير driver أثناء الإنشاء
    }

    //------------------------------------------------ Contact -----------------------------------------------
    @DataProvider(name = "ContactData")
    public Object[][] getContactData() throws IOException, InvalidFormatException {
        return ExcelUtils.readExcelData("D:\\Testing\\Technical\\Java\\Projects\\AbdullahDemoblazeContact\\excelData.xlsx", "Sheet3");
    }

    @Test(priority = 3, dataProvider = "ContactData")
    public void testContact(String contactEmail, String contactName, String message, String testType) throws InterruptedException {
        contactPage.contactValidation(contactEmail, contactName, message, testType);
    }

    @Test(priority = 4)
    public void testContactClosingViaXsign() throws InterruptedException {
        contactPage.closingValidation("x");
    }

    @Test(priority = 5)
    public void testContactClosingViaCloseButton() throws InterruptedException {
        contactPage.closingValidation("close");
    }

    @Test(priority = 6)
    public void testContactClickingOnSendMessageButtonWithEmptyData() throws InterruptedException {
        contactPage.clickingOnSendMessageButtonWithEmptyDataValidation();
    }
}
