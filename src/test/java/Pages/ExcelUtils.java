
package Pages;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtils {

    public static Object[][] readExcelData(String filePath, String sheetName) throws IOException {
        FileInputStream file = new FileInputStream(new File(filePath));
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheet(sheetName);
        int rowCount = sheet.getPhysicalNumberOfRows();
        int colCount = sheet.getRow(0).getPhysicalNumberOfCells();
        Object[][] data = new Object[rowCount - 1][colCount];

        for (int i = 1; i < rowCount; i++) { // بدءًا من 1 لتجاهل عنوان الأعمدة
            Row row = sheet.getRow(i);
            for (int j = 0; j < colCount; j++) {
                Cell cell = row.getCell(j);
                if (cell != null) {
                    switch (cell.getCellType()) {
                        case STRING:
                            data[i - 1][j] = cell.getStringCellValue();
                            break;
                        case NUMERIC:
                            data[i - 1][j] = String.valueOf((int) cell.getNumericCellValue()); // تحويل الرقم إلى نص
                            break;
                        case BOOLEAN:
                            data[i - 1][j] = String.valueOf(cell.getBooleanCellValue());
                            break;
                        case FORMULA:
                            data[i - 1][j] = cell.getCellFormula();
                            break;
                        default:
                            data[i - 1][j] = "";
                    }
                } else {
                    data[i - 1][j] = ""; // في حالة وجود خلية فارغة
                }
            }
        }
        workbook.close();
        file.close();
        return data;
    }
}


/*
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtils {
    public static Object[][] readExcelData(String fileName, String sheetName) throws IOException, InvalidFormatException {
        // تحديد المسار النسبي بناءً على مكان تشغيل الكود
        String filePath = System.getProperty("user.dir") + File.separator + "data" + File.separator + fileName;

        FileInputStream file = new FileInputStream(new File(filePath));
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheet(sheetName);
        int rowCount = sheet.getPhysicalNumberOfRows();
        int colCount = sheet.getRow(0).getPhysicalNumberOfCells();
        Object[][] data = new Object[rowCount - 1][colCount];

        for (int i = 1; i < rowCount; i++) { // بدءًا من 1 لتجاهل عنوان الأعمدة
            Row row = sheet.getRow(i);
            for (int j = 0; j < colCount; j++) {
                Cell cell = row.getCell(j);
                if (cell != null) {
                    switch (cell.getCellType()) {
                        case STRING:
                            data[i - 1][j] = cell.getStringCellValue();
                            break;
                        case NUMERIC:
                            data[i - 1][j] = String.valueOf((int) cell.getNumericCellValue()); // تحويل الرقم إلى نص
                            break;
                        case BOOLEAN:
                            data[i - 1][j] = String.valueOf(cell.getBooleanCellValue());
                            break;
                        case FORMULA:
                            data[i - 1][j] = cell.getCellFormula();
                            break;
                        default:
                            data[i - 1][j] = "";
                    }
                } else {
                    data[i - 1][j] = ""; // في حالة وجود خلية فارغة
                }
            }
        }
        workbook.close();
        file.close();
        return data;
    }
}

/*
public class TestExcelData {
    public static void main(String[] args) {
        try {
            Object[][] data = ExcelDataReader.readExcelData("data.xlsx", "Sheet1");

            for (Object[] row : data) {
                for (Object value : row) {
                    System.out.print(value + "\t");
                }
                System.out.println();
            }
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }
    }
}*/

/*
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtils {
    public static Object[][] readExcelData(String fileName, String sheetName) throws IOException, InvalidFormatException {
        // تحديد المسار النسبي للملف داخل فولدر "data"
        String filePath = System.getProperty("user.dir") + File.separator + "data" + File.separator + fileName;

        // التأكد من وجود الملف
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("الملف غير موجود: " + filePath);
        }

        // قراءة ملف Excel
        FileInputStream fileInputStream = new FileInputStream(file);
        Workbook workbook = WorkbookFactory.create(fileInputStream);
        Sheet sheet = workbook.getSheet(sheetName);

        int rowCount = sheet.getPhysicalNumberOfRows();
        int colCount = sheet.getRow(0).getPhysicalNumberOfCells();
        Object[][] data = new Object[rowCount - 1][colCount];

        for (int i = 1; i < rowCount; i++) { // بدءًا من 1 لتجاهل عنوان الأعمدة
            Row row = sheet.getRow(i);
            for (int j = 0; j < colCount; j++) {
                Cell cell = row.getCell(j);
                data[i - 1][j] = getCellValue(cell);
            }
        }

        workbook.close();
        fileInputStream.close();
        return data;
    }

    // دالة مساعدة لاستخراج القيمة من أي نوع من الخلايا
    private static Object getCellValue(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return (int) cell.getNumericCellValue(); // تحويل الرقم إلى int
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
}
*/