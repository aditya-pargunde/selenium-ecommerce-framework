package ecommerceautomation.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelUtils {

    private Sheet sheet;

    public ExcelUtils(String filePath, String sheetName) throws Exception {
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("Excel file path cannot be null or empty.");
        }

        try (FileInputStream file = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(file)) {
            sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new RuntimeException("Sheet '" + sheetName + "' not found in Excel file: " + filePath);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to open or read Excel file: " + filePath, e);
        }
    }

    // Returns all rows as list of string arrays, skipping blank rows
    public List<String[]> getAllRows() {
        List<String[]> data = new ArrayList<>();
        Iterator<Row> rowIterator = sheet.iterator();

        boolean header = true;
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (header) { 
                header = false; 
                continue; // skip header
            }

            int cellCount = row.getLastCellNum();
            String[] rowData = new String[cellCount];

            boolean isEmptyRow = true;
            for (int i = 0; i < cellCount; i++) {
                Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                String value = cell.toString().trim();
                rowData[i] = value;
                if (!value.isEmpty()) {
                    isEmptyRow = false; // found actual data
                }
            }

            if (!isEmptyRow) { // ✅ only add non-empty rows
                data.add(rowData);
            }
        }
        return data;
    }

    public Object[][] getAllRowsAsObjectArray() {
        List<String[]> allRows = getAllRows();
        Object[][] data = new Object[allRows.size()][];
        for (int i = 0; i < allRows.size(); i++) {
            data[i] = allRows.get(i);
        }
        return data;
    }

    // ✅ Returns first non-empty row (ignores blanks)
    public String[] getFirstRow() {
        List<String[]> rows = getAllRows();
        if (rows.isEmpty()) {
            throw new RuntimeException("No valid data rows found in sheet.");
        }
        return rows.get(0);
    }
}
