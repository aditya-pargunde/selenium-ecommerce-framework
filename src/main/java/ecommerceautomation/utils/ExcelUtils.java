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
        FileInputStream file = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(file);
        sheet = workbook.getSheet(sheetName);
    }

    // Returns all rows as list of string arrays
    public List<String[]> getAllRows() {
        List<String[]> data = new ArrayList<>();
        Iterator<Row> rowIterator = sheet.iterator();

        boolean header = true;
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (header) { header = false; continue; } // skip header

            int cellCount = row.getLastCellNum();
            String[] rowData = new String[cellCount];

            for (int i = 0; i < cellCount; i++) {
                Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                rowData[i] = cell.toString();
            }
            data.add(rowData);
        }
        return data;
    }

    // Optional: get first row (for valid login)
    public String[] getFirstRow() {
        return getAllRows().get(0);
    }
}
