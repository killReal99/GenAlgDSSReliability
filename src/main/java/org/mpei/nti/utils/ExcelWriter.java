package org.mpei.nti.utils;

import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.*;
import org.mpei.nti.substation.substationStructures.IED;
import org.mpei.nti.substation.substationStructures.SubstationMeasures;
import org.mpei.nti.substation.substationStructures.SubstationMeasuresPerYear;

import java.io.*;
import java.util.List;

public class ExcelWriter {

    public static void writeToExcel(List<SubstationMeasures> population) throws IOException {
        String path = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "results.xlsx";
        ZipSecureFile.setMinInflateRatio(0);
        FileInputStream fileInputStream = new FileInputStream(path);
        Workbook workbook = WorkbookFactory.create(fileInputStream);
        Sheet sheet = workbook.getSheet("Лист1");

        int rowIndex = 1;
        int cellIndex = 0;

        if (population.get(0).isIedRosseti() && population.get(0).isLanRosseti()) {
            cellIndex = 3;
        } else if (population.get(0).isIedRosseti() && !population.get(0).isLanRosseti()) {
            cellIndex = 5;
        } else if (!population.get(0).isIedRosseti() && population.get(0).isLanRosseti()) {
            cellIndex = 7;
        } else if (!population.get(0).isIedRosseti() && !population.get(0).isLanRosseti()) {
            cellIndex = 9;
        }

        CellStyle cellStyleGreen = sheet.getWorkbook().createCellStyle();
        cellStyleGreen.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        cellStyleGreen.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle cellStyleRed = sheet.getWorkbook().createCellStyle();
        cellStyleRed.setFillForegroundColor(IndexedColors.RED.getIndex());
        cellStyleRed.setFillPattern(FillPatternType.SOLID_FOREGROUND);


        for (SubstationMeasuresPerYear substationMeasuresPerYear : population.get(0).getSubstationMeasuresPerYear()) {
            rowIndex = setCellStyle(sheet, rowIndex, cellIndex,
                    substationMeasuresPerYear.getOrganizationalMeasures().getD1() == 1, cellStyleGreen, cellStyleRed);
            rowIndex = setCellStyle(sheet, rowIndex, cellIndex,
                    substationMeasuresPerYear.getOrganizationalMeasures().getD6() == 1, cellStyleGreen, cellStyleRed);
            rowIndex = setCellStyle(sheet, rowIndex, cellIndex,
                    substationMeasuresPerYear.getOrganizationalMeasures().getD10() == 1, cellStyleGreen, cellStyleRed);
            rowIndex = setCellStyle(sheet, rowIndex, cellIndex,
                    substationMeasuresPerYear.getOrganizationalMeasures().getD12() == 1, cellStyleGreen, cellStyleRed);
            rowIndex = setCellStyle(sheet, rowIndex, cellIndex,
                    substationMeasuresPerYear.getOrganizationalMeasures().getD16() == 1, cellStyleGreen, cellStyleRed);
            rowIndex = setCellStyle(sheet, rowIndex, cellIndex,
                    substationMeasuresPerYear.getOrganizationalMeasures().getD22() == 1, cellStyleGreen, cellStyleRed);
            rowIndex = setCellStyle(sheet, rowIndex, cellIndex,
                    substationMeasuresPerYear.getImprosedMeasures().getD3() == 1, cellStyleGreen, cellStyleRed);
            rowIndex = setCellStyle(sheet, rowIndex, cellIndex,
                    substationMeasuresPerYear.getImprosedMeasures().getD7() == 1, cellStyleGreen, cellStyleRed);
            rowIndex = setCellStyle(sheet, rowIndex, cellIndex,
                    substationMeasuresPerYear.getImprosedMeasures().getD11() == 1, cellStyleGreen, cellStyleRed);
            rowIndex = setCellStyle(sheet, rowIndex, cellIndex,
                    substationMeasuresPerYear.getImprosedMeasures().getD19() == 1, cellStyleGreen, cellStyleRed);
            rowIndex = setCellStyle(sheet, rowIndex, cellIndex,
                    substationMeasuresPerYear.getImprosedMeasures().getD20() == 1, cellStyleGreen, cellStyleRed);
            rowIndex = setCellStyle(sheet, rowIndex, cellIndex,
                    substationMeasuresPerYear.getImprosedMeasures().getD21() == 1, cellStyleGreen, cellStyleRed);
            rowIndex = setCellStyle(sheet, rowIndex, cellIndex,
                    substationMeasuresPerYear.getImprosedMeasures().getD21() == 1, cellStyleGreen, cellStyleRed);

            for (IED ied : substationMeasuresPerYear.getIedList()) {
                rowIndex = setCellStyle(sheet, rowIndex, cellIndex, ied.getD2() == 1, cellStyleGreen, cellStyleRed);
                rowIndex = setCellStyle(sheet, rowIndex, cellIndex, ied.getD4() == 1, cellStyleGreen, cellStyleRed);
                rowIndex = setCellStyle(sheet, rowIndex, cellIndex, ied.getD5() == 1, cellStyleGreen, cellStyleRed);
                rowIndex = setCellStyle(sheet, rowIndex, cellIndex, ied.getD8() == 1, cellStyleGreen, cellStyleRed);
                rowIndex = setCellStyle(sheet, rowIndex, cellIndex, ied.getD9() == 1, cellStyleGreen, cellStyleRed);
                rowIndex = setCellStyle(sheet, rowIndex, cellIndex, ied.getD13() == 1, cellStyleGreen, cellStyleRed);
                rowIndex = setCellStyle(sheet, rowIndex, cellIndex, ied.getD14() == 1, cellStyleGreen, cellStyleRed);
                rowIndex = setCellStyle(sheet, rowIndex, cellIndex, ied.getD15() == 1, cellStyleGreen, cellStyleRed);
                rowIndex = setCellStyle(sheet, rowIndex, cellIndex, ied.getD17() == 1, cellStyleGreen, cellStyleRed);
                rowIndex = setCellStyle(sheet, rowIndex, cellIndex, ied.getD18() == 1, cellStyleGreen, cellStyleRed);
                rowIndex = setCellStyle(sheet, rowIndex, cellIndex, ied.getD23() == 1, cellStyleGreen, cellStyleRed);
            }
        }

        try (OutputStream fileOutputStream = new FileOutputStream(path)) {
            workbook.write(fileOutputStream);
        }
        workbook.close();
    }

    public static int setCellStyle(Sheet sheet, int rowIndex, int cellIndex, boolean colorIndex, CellStyle cellStyleGreen,
                                   CellStyle cellStyleRed) {
        Row row = sheet.getRow(rowIndex);
        Cell cell = row.getCell(cellIndex);
        if (colorIndex) {
            cell.setCellStyle(cellStyleGreen);
        } else {
            cell.setCellStyle(cellStyleRed);
        }
        rowIndex++;
        return rowIndex;
    }

}
