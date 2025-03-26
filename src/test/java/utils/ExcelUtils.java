package utils;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ExcelUtils {
    private Workbook workbook;
    private Sheet sheet;
    private FileInputStream fis;

    public ExcelUtils(String excelPath, String sheetName) {
        try {
            fis = new FileInputStream(excelPath);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new IllegalArgumentException("Sheet '" + sheetName + "' not found in " + excelPath);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found at path " + excelPath);
        } catch (IOException e) {
            System.err.println("Error: Unable to read the Excel file " + excelPath);
        }
    }

    public String getCellData(int rowNum, int colNum) {
        DataFormatter formatter = new DataFormatter();

        if (sheet == null) {
            return "Error: Sheet not loaded";
        }

        Row row = sheet.getRow(rowNum);
        if (row == null || row.getCell(colNum) == null) {
            return "Error: Cell is empty";
        }

        return formatter.formatCellValue(row.getCell(colNum));
    }

    public int getRowCount() {
        return (sheet != null) ? sheet.getPhysicalNumberOfRows() : 0;
    }

    public int getColCount() {
        return (sheet != null && sheet.getRow(0) != null) ? sheet.getRow(0).getPhysicalNumberOfCells() : 0;
    }

    public void closeWorkbook() {
        try {
            if (workbook != null) {
                workbook.close();
            }
            if (fis != null) {
                fis.close();
            }
        } catch (IOException e) {
            System.err.println("Error closing the Excel file: " + e.getMessage());
        }
    }
}
