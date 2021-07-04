package pl.estrix.backend.reports.service;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;
import pl.estrix.common.dto.GetShipmentDetailsDto;
import pl.estrix.common.dto.model.ReleaseArticleDto;
import pl.estrix.common.dto.model.ReleaseArticlePalletDto;
import pl.estrix.common.dto.model.ShipmentProductDto;
import pl.estrix.common.log.Timed;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Service
public class ExcelReportService {

    private static final String TEMP_DIR = "java.io.tmpdir";


    @Timed
    public String writeReleaseXLSXFile(ReleaseArticleDto param) {
        String excelFileName = getOutputFilePath(System.getProperty(TEMP_DIR), "wysylka_" + param.getReleaseDate().toString());

        String sheetName = "Wysyłka " + param.getReleaseDate();
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(sheetName) ;

        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("Kod palety");
        row.createCell(1).setCellValue("Numer artykułu");
        row.createCell(2).setCellValue("Numer zwrotu");
        row.createCell(3).setCellValue("Numer palety");
        row.createCell(4).setCellValue("Ilość");
        row.createCell(5).setCellValue("Flaga");
        row.createCell(6).setCellValue("EAN");

        int rowIndex = 1;
        for (ReleaseArticlePalletDto palletDto : param.getPalletDtoList()){
            row = sheet.createRow(rowIndex);

            XSSFCell cell0 = row.createCell(0);
            cell0.setCellValue(palletDto.getReleaseCode());

            XSSFCell cell1 = row.createCell(1);
            cell1.setCellValue(palletDto.getArtNumber());

            XSSFCell cell2 = row.createCell(2);
            cell2.setCellValue(palletDto.getReturnNumber());
            XSSFCell cell3 = row.createCell(3);
            cell3.setCellValue(palletDto.getPalletCounter());

            XSSFCell cell4 = row.createCell(4);
            cell4.setCellValue(palletDto.getCounter());
            XSSFCell cell5 = row.createCell(5);
            cell5.setCellValue(palletDto.getPalletFlag());
            XSSFCell cell6 = row.createCell(6);
            cell6.setCellValue(palletDto.getEan()!=null?palletDto.getEan().trim().replaceAll(" ","\r\n"):"");

            rowIndex++;
        }

        try(FileOutputStream fileOut = new FileOutputStream(excelFileName)) {
            wb.write(fileOut);
            fileOut.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        try(FileOutputStream fileOut = new FileOutputStream(excelFileName)) {
//            wb.write(fileOut);
//            fileOut.flush();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        return excelFileName;
    }

    @Timed
    public String writeGlobalReportXLSXFile(GetShipmentDetailsDto param) {
        String excelFileName = getOutputFilePath(System.getProperty(TEMP_DIR), "raport_" + param.getShipmentDto().getNumber());

        List<ShipmentProductDto> shipmentProductDtoList = param.getShipmentProductDtoList();

        String sheetName = "Ilości nadane";
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(sheetName) ;

        XSSFCellStyle style1 = wb.createCellStyle();
        style1.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style1.setBorderRight(HSSFCellStyle.BORDER_THIN);
        XSSFCellStyle style2 = wb.createCellStyle();
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);

        XSSFRow row = sheet.createRow(0);
        Cell h1 = row.createCell(0);
        h1.setCellValue("Nr art");
        h1.setCellStyle(style1);

        Cell h2 = row.createCell(1);
        h2.setCellValue("Ilość");
        h2.setCellStyle(style1);
        Cell h3 = row.createCell(2);
        h3.setCellValue("MP");
        h3.setCellStyle(style1);
        Cell h4 = row.createCell(3);
        h4.setCellValue("uszk.");
        h4.setCellStyle(style1);
        Cell h5 = row.createCell(4);
        h5.setCellValue("razem");
        h5.setCellStyle(style1);
        Cell h6 = row.createCell(5);
        h6.setCellValue("z ceną");
        h6.setCellStyle(style1);
        Cell h7 = row.createCell(6);
        h7.setCellValue("Nazwa");
        h7.setCellStyle(style1);
        Cell h8 = row.createCell(7);
        h8.setCellValue("EAN");
        h8.setCellStyle(style1);


        int rowIndex = 1;
        for (ShipmentProductDto shipmentProductDto : shipmentProductDtoList){
            String[] eans = shipmentProductDto.getEan().trim().replaceAll("  ", " ").split(" ");
            int artRow = 0;
            for(String ean : eans){
                if (ean.isEmpty()) continue;
                row = sheet.createRow(rowIndex);

                if (artRow == 0){
                    XSSFCell cell0 = row.createCell(0);
                    cell0.setCellValue(shipmentProductDto.getArtNumber());
                    cell0.setCellStyle(style2);
                    XSSFCell cell1 = row.createCell(1);
                    cell1.setCellValue(shipmentProductDto.getCounter());
                    cell1.setCellStyle(style2);
                    XSSFCell cell2 = row.createCell(2);
                    cell2.setCellValue(shipmentProductDto.getScanCorrect());
                    cell2.setCellStyle(style2);
                    XSSFCell cell3 = row.createCell(3);
                    cell3.setCellValue(shipmentProductDto.getScanError());
                    cell3.setCellStyle(style2);

                    XSSFCell cell4 = row.createCell(4);
                    String strFormula= "SUM(C"+(rowIndex+1)+":D"+(rowIndex+1)+")";
                    cell4.setCellType(HSSFCell.CELL_TYPE_FORMULA);
                    cell4.setCellFormula(strFormula);
                    cell4.setCellStyle(style2);

                    XSSFCell cell5 = row.createCell(5);
                    cell5.setCellValue(shipmentProductDto.getScanLabel());
                    cell5.setCellStyle(style2);
                }else{
                    XSSFCell cell0 = row.createCell(0);
                    cell0.setCellValue("");
                    cell0.setCellStyle(style2);
                    XSSFCell cell1 = row.createCell(1);
                    cell1.setCellValue("");
                    cell1.setCellStyle(style2);
                    XSSFCell cell2 = row.createCell(2);
                    cell2.setCellValue("");
                    cell2.setCellStyle(style2);
                    XSSFCell cell3 = row.createCell(3);
                    cell3.setCellValue("");
                    cell3.setCellStyle(style2);

                    XSSFCell cell4 = row.createCell(4);
                    cell4.setCellValue("");
                    cell4.setCellStyle(style2);

                    XSSFCell cell5 = row.createCell(5);
                    cell5.setCellValue("");
                    cell5.setCellStyle(style2);
                }

                XSSFCell cell5 = row.createCell(6);
                cell5.setCellValue(shipmentProductDto.getName());
                cell5.setCellStyle(style2);

                XSSFCell cell6 = row.createCell(7);
                cell6.setCellValue(ean);
                cell6.setCellStyle(style2);

                rowIndex++;
                artRow++;
            }
        }

        String sheetName2 = "Dane logistyczne";
        XSSFSheet sheet2 = wb.createSheet(sheetName2) ;
        XSSFRow row2 = sheet2.createRow(0);
        h1 = row2.createCell(0);
        h1.setCellValue("Nr art");
        h1.setCellStyle(style1);
        h2 = row2.createCell(1);
        h2.setCellValue("Nazwa");
        h2.setCellStyle(style1);
        h3 = row2.createCell(2);
        h3.setCellValue("EAN");
        h3.setCellStyle(style1);

        rowIndex = 1;
        for (ShipmentProductDto shipmentProductDto : shipmentProductDtoList){
            String[] eans = shipmentProductDto.getEan().trim().replaceAll("  ", " ").split(" ");
            for(String ean : eans){
                if (ean.isEmpty()) continue;
                row2 = sheet2.createRow(rowIndex);

                XSSFCell cell0 = row2.createCell(0);
                cell0.setCellValue(shipmentProductDto.getArtNumber());
                cell0.setCellStyle(style2);

                XSSFCell cell5 = row2.createCell(1);
                cell5.setCellValue(shipmentProductDto.getName());
                cell5.setCellStyle(style2);

                XSSFCell cell6 = row2.createCell(2);
                cell6.setCellValue(ean);
                cell6.setCellStyle(style2);

                rowIndex++;
            }
        }

        try(FileOutputStream fileOut = new FileOutputStream(excelFileName)) {
            wb.write(fileOut);
            fileOut.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return excelFileName;
    }

    public String writeWeightSortXLSXFile(GetShipmentDetailsDto param) {
        String excelFileName = getOutputFilePath(System.getProperty(TEMP_DIR), "magazyn_" + param.getShipmentDto().getNumber());

        List<ShipmentProductDto> shipmentProductDtoList = param.getShipmentProductDtoList();

        Collections.sort(shipmentProductDtoList, (o1, o2) -> {
            Double sumO1 = o1.getArtValume() * o1.getCounter();
            Double sumO2 = o2.getArtValume() * o2.getCounter();
            return sumO1 < sumO2 ? 1 : -1;
        });

        String sheetName = "Zwrot_"+param.getShipmentDto().getNumber();//name of sheet
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(sheetName) ;

        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("Nazwa");
        row.createCell(1).setCellValue("EAN");
        row.createCell(2).setCellValue("Sztuk");
        row.createCell(3).setCellValue("Waga");
        row.createCell(4).setCellValue("Objętość");
        row.createCell(5).setCellValue("Suma");

        int rowIndex = 1;
        for (ShipmentProductDto shipmentProductDto : shipmentProductDtoList){
            row = sheet.createRow(rowIndex);

            XSSFCell cell0 = row.createCell(0);
            cell0.setCellValue(shipmentProductDto.getName());

            XSSFCell cell1 = row.createCell(1);
            cell1.setCellValue(shipmentProductDto.getEan());

            XSSFCell cell2 = row.createCell(2);
            cell2.setCellValue(shipmentProductDto.getCounter());

            XSSFCell cell3 = row.createCell(3);
            cell3.setCellValue(shipmentProductDto.getWeight());

            XSSFCell cell4 = row.createCell(4);
            cell4.setCellValue(shipmentProductDto.getArtValume());

            XSSFCell cell5 = row.createCell(5);
            Double sum = shipmentProductDto.getArtValume() * shipmentProductDto.getCounter();
            cell5.setCellValue(sum);

            rowIndex++;
        }

        try(FileOutputStream fileOut = new FileOutputStream(excelFileName)) {
            wb.write(fileOut);
            fileOut.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return excelFileName;
    }

    public String writeXLSXFile(GetShipmentDetailsDto param) {
        String excelFileName = getOutputFilePath(System.getProperty(TEMP_DIR), "raport_zwrot_" + param.getShipmentDto().getNumber());

        String sheetName = "Zwrot_"+param.getShipmentDto().getNumber();//name of sheet
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(sheetName) ;

        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("Nr. Art.");
        row.createCell(1).setCellValue("Nazwa");
        row.createCell(2).setCellValue("EAN");
        row.createCell(3).setCellValue("Sztuk");
        row.createCell(4).setCellValue("Dobre");
        row.createCell(5).setCellValue("Uszkodzone");

        int rowIndex = 1;
        for (ShipmentProductDto shipmentProductDto : param.getShipmentProductDtoList()){
            row = sheet.createRow(rowIndex);

            XSSFCell cell0 = row.createCell(0);
            cell0.setCellValue(shipmentProductDto.getArtNumber());

            XSSFCell cell1 = row.createCell(1);
            cell1.setCellValue(shipmentProductDto.getName());

            XSSFCell cell2 = row.createCell(2);
            cell2.setCellValue(shipmentProductDto.getEan());

            XSSFCell cell3 = row.createCell(3);
            cell3.setCellValue(shipmentProductDto.getCounter());

            XSSFCell cell4 = row.createCell(4);
            cell4.setCellValue(shipmentProductDto.getScanCorrect());

            XSSFCell cell5 = row.createCell(5);
            cell5.setCellValue(shipmentProductDto.getScanError());

            rowIndex++;
        }

        try(FileOutputStream fileOut = new FileOutputStream(excelFileName)) {
            wb.write(fileOut);
            fileOut.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return excelFileName;
    }

    public String getOutputFilePath(String outputDir, String fileName) {
        StringBuilder builder = new StringBuilder();
        builder.append(fileName);
        builder.append("_");
        builder.append(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss").format(LocalDateTime.now()));
        builder.append(".xlsx");
        return FilenameUtils.separatorsToUnix(FilenameUtils.concat(outputDir, builder.toString()));
    }
}
