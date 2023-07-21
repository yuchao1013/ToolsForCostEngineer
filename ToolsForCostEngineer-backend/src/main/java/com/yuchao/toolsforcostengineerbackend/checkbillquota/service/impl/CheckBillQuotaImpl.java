package com.yuchao.toolsforcostengineerbackend.checkbillquota.service.impl;
import com.yuchao.toolsforcostengineerbackend.checkbillquota.entity.CheckBillQuotaResult;
import com.yuchao.toolsforcostengineerbackend.checkbillquota.entity.TitlePosition;
import com.yuchao.toolsforcostengineerbackend.checkbillquota.service.CheckBillQuota;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.yuchao.toolsforcostengineerbackend.utils.DecimalUtils.strToDecimal;
import static com.yuchao.toolsforcostengineerbackend.utils.FileUtils.transferToFile;

/**
 * @Classname CheckBillQuotaImpl
 * @Description TODO
 * @Date 2023/7/18 22:08
 * @Created by YuChao
 * @Version 1.0
 */
@Service
public class CheckBillQuotaImpl implements CheckBillQuota {
    private final TitlePosition titlePosition;
    @Autowired
    public CheckBillQuotaImpl(TitlePosition titlePosition) {
        this.titlePosition = titlePosition;
    }

    @Override
    public List<CheckBillQuotaResult> getResult(MultipartFile excelFile) {
        File file = transferToFile(excelFile);
        ArrayList<CheckBillQuotaResult> resultList = new ArrayList<>();

        try {
            POIFSFileSystem fileSystem = new POIFSFileSystem(file);
            Workbook workbook = new HSSFWorkbook(fileSystem);
            fileSystem.close();
            file.delete();
            int numberOfSheets = workbook.getNumberOfSheets();
            String projectName = null;
            for (int sheetIndex = 0; sheetIndex < numberOfSheets; sheetIndex++) {
                Sheet sheet = workbook.getSheetAt(sheetIndex);
                projectName = findProjectName(sheet);
                int numberOfRows = sheet.getPhysicalNumberOfRows();
                // 初始化一个目标工程量值，每次找到清单项，将其工程量作为目标值，用于与定额工程量对比。
                BigDecimal targetCount = null;
                String billSNAndName = null;
                String SNValue = null;
                String nameValue = null;
                for (int rowNum = 0; rowNum < numberOfRows; rowNum++) {
                    Row row = sheet.getRow(rowNum);
                    if (row == null)
                        continue;
                    // 判断是否为清单
                    if (isBill(row, titlePosition)) {
                        targetCount = getCountValue(row, titlePosition);
                        if (targetCount == null)
                            targetCount = BigDecimal.valueOf(0.0);
                        SNValue = row.getCell(titlePosition.getSNColNum()).getStringCellValue();
                        nameValue = row.getCell(titlePosition.getNameColNum()).getStringCellValue();
                    }
                    // 判断是否为定额
                    if (isQuota(row, titlePosition)) {
                        // 取出原始定额工程量
                        BigDecimal quotaCount = getCountValue(row, titlePosition);
                        if (quotaCount == null)
                            quotaCount = BigDecimal.valueOf(0.0);
                        // 取出定额单位所包含的倍数
                        Integer unitMultiple = getUnitMultiple(row, titlePosition);
                        // 计算出定额实际工程量
                        BigDecimal quotaCountValue = quotaCount.multiply(BigDecimal.valueOf(unitMultiple));
                        // 清单工程量不为0，并且定额和清单工程量不相等，报出sheet名称+当前清单序号和名称
                        if (BigDecimal.valueOf(0.0).compareTo(targetCount) != 0 && quotaCountValue.compareTo(targetCount) != 0) {
                            // 读取定额名称
                            Cell cell = row.getCell(titlePosition.getNameColNum());
                            String quotaName = "";
                            if (cell != null) quotaName = cell.getStringCellValue();
                            CheckBillQuotaResult result = new CheckBillQuotaResult(projectName, SNValue, nameValue, targetCount, quotaName, quotaCount);
                            resultList.add(result);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }

    private Boolean isBill(Row row, TitlePosition titlePosition){
        if (row==null) return false;
        Cell SNcell = row.getCell(titlePosition.getSNColNum());
        Cell idCell = row.getCell(titlePosition.getIdColNum());
        Cell countCell = row.getCell(titlePosition.getCountColNum());
        String SNValue = SNcell == null? "" : getCellValueToString(SNcell); //序号 单元格值
        String idValue = idCell == null? "" : getCellValueToString(idCell); //编码 单元格值
        String countValue = countCell == null? "" : getCellValueToString(countCell); //工程量 单元格值
        //序号非空 && 编码非空 && 工程量非空，再判断工程量是数值，则该行为定额行
        if (!("".equals(SNValue)) && !("".equals(idValue)) && !("".equals(countValue))) {
            return isNumber(countValue);
        }
        return false;
    }
    //通过行和各表头位置，判断是否为定额行
    private Boolean isQuota(Row row, TitlePosition titlePosition){
        if (row==null) return false;
        Cell SNcell = row.getCell(titlePosition.getSNColNum());
        Cell idCell = row.getCell(titlePosition.getIdColNum());
        Cell countCell = row.getCell(titlePosition.getCountColNum());
        String SNValue = SNcell == null? "": getCellValueToString(SNcell); //序号 单元格值
        String idValue = idCell == null? "": getCellValueToString(idCell); //编码 单元格值
        String countValue = countCell == null? "": getCellValueToString(countCell); //工程量 单元格值
        //序号为空 && 编码非空 && 工程量非空，再判断工程量是数值，则该行为定额行
        if ("".equals(SNValue) && !("".equals(idValue)) && !("".equals(countValue))) {
            return isNumber(countValue);
        }
        return false;
    }

    //提取工程量数值
    private BigDecimal getCountValue(Row row, TitlePosition titlePosition){
        if (row==null) return null;
        Cell cell = row.getCell(titlePosition.getCountColNum());
        if (cell == null) return null;
        CellType cellType = cell.getCellType();
        switch (cellType){
            case NUMERIC -> {
                double numericCellValue = cell.getNumericCellValue();
                return BigDecimal.valueOf(numericCellValue);
            }
            case STRING -> {
                String stringCellValue = cell.getStringCellValue();
                if ("".equals(stringCellValue)) return null;
                double doubleValue = Double.parseDouble(stringCellValue);
                return BigDecimal.valueOf(doubleValue);
            }
            case FORMULA -> {
//                Workbook workbook = row.getSheet().getWorkbook();
//                FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
//                formulaEvaluator.evaluateFormulaCell(cell);
                try {
                    String stringCellValue = cell.getStringCellValue();
                    return strToDecimal(stringCellValue);
                } catch (Exception e) {
                    double doubleValue = cell.getNumericCellValue();
                    String strValue = NumberToTextConverter.toText(doubleValue);
                    return new BigDecimal(strValue);
                }
            }
            default -> {
                return null;
            }
        }
    }


    //提取单位，检查单位是否有倍数，并返回倍数
    private Integer getUnitMultiple(Row row, TitlePosition titlePosition){
        int multiple = 1;
        Cell cell = row.getCell(titlePosition.getUnitColNum());
        if (cell == null) return multiple;
        String unitStr = cell.getStringCellValue();
        if ("".equals(unitStr)) return multiple;
        if (unitStr.startsWith("10000")) {
            multiple = 10000;
        }else if (unitStr.startsWith("1000")) {
            multiple = 1000;
        }else if (unitStr.startsWith("100")) {
            multiple = 100;
        }else if (unitStr.startsWith("10")) {
            multiple = 10;
        }
        return multiple;
    }

    //提取sheet内的单位工程名称
    private String findProjectName(Sheet sheet){
        if (sheet == null) return null;
        int numberOfRows = sheet.getPhysicalNumberOfRows();
        for (int rowIndex = 0; rowIndex < numberOfRows; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) continue;
            int numberOfCells = row.getPhysicalNumberOfCells();
            for (int colIndex = 0; colIndex < numberOfCells; colIndex++) {
                Cell cell = row.getCell(colIndex);
                if (cell == null) continue;
                String celStr = cell.getStringCellValue();
                if (celStr.startsWith("工程名称：")){
                    return celStr.substring(5);
                }
            }
        }
        return null;
    }

    //#暂不用# 查找字符串所在列号
//    public static Integer findColNUm(String str, Sheet sheet){
//        return 0;
//    }

    private Boolean isNumber(String str){
        try {
            Double.parseDouble(str);
            return true;
        }catch (Exception e){
            return false;
        }
    }



    //把单元格的值提取为字符串String
    private String getCellValueToString(Cell cell){
        if (cell == null) return "";
        CellType cellType = cell.getCellType();
        switch (cellType){
            case NUMERIC -> {
                double numericCellValue = cell.getNumericCellValue();
                return String.valueOf(numericCellValue);
            }
            case STRING -> {
                return cell.getStringCellValue();
            }
            case FORMULA -> {
                try {
                    return cell.getStringCellValue();
                } catch (Exception e) {
                    double doubleValue = cell.getNumericCellValue();
                    return NumberToTextConverter.toText(doubleValue); //无精度缺失
                }
            }
            case BOOLEAN -> {
                boolean booleanCellValue = cell.getBooleanCellValue();
                return String.valueOf(booleanCellValue);
            }
            case ERROR -> {
                return "ERROR";
            }
            default -> {
                return "";
            }
        }
    }
}