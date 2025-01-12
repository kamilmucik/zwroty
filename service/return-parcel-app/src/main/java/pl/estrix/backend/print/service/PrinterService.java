package pl.estrix.backend.print.service;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.print.executor.*;
import pl.estrix.backend.settings.service.SettingService;
import pl.estrix.common.base.ListResponseDto;
import pl.estrix.common.dto.PrintLabelDto;
import pl.estrix.common.dto.PrinterSearchCriteriaDto;
import pl.estrix.common.dto.model.PrintDataDto;
import pl.estrix.common.dto.model.PrintFileDto;
import pl.estrix.common.dto.model.PrinterDto;
import pl.estrix.common.dto.model.ShipmentProductDto;

import javax.print.*;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.PrinterName;
import javax.print.attribute.standard.PrinterURI;
import javax.transaction.Transactional;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

/**
 * https://stackoverflow.com/questions/9098853/java-application-to-print-on-network-printer
 * "ipp:\\\\witnw21va\\ipp\\ITDepartment-HP4050"
 *
 */
@Service
public class PrinterService {

    @Autowired
    private ReadPrinterCommandExecutor readExecutor;
    @Autowired
    private CreatePrinterCommandExecutor createExecutor;
    @Autowired
    private UpdatePrinterCommandExecutor updateExecutor;
    @Autowired
    private DeletePrinterCommandExecutor deleteExecutor;
    @Autowired
    private ReadPrintFileCommandExecutor readPrintFileExecutor;
    @Autowired
    private CreatePrintFileCommandExecutor createPrintFileExecutor;
    @Autowired
    private UpdatePrintFileCommandExecutor updatePrintFileExecutor;
    @Autowired
    private DeletePrintFileCommandExecutor deletePrintFileExecutor;
    @Autowired
    private SettingService settingService;

    private PrintService printService;

    public PrinterDto getDatefault() {
        ListResponseDto<PrinterDto> listResponseDto = readExecutor.find(PrinterSearchCriteriaDto.builder().build(),null);

        return listResponseDto.getData().stream().filter( PrinterDto::getIsDefault).findFirst().orElse(null);
    }

    public PrinterDto get(Long id) {
        return readExecutor.findById(id);
    }
    public PrinterDto findByName(String name) {
        return readExecutor.findByName(name);
    }

    public PrinterDto update(PrinterDto dto) {
        return updateExecutor.update(dto);
    }
    public PrinterDto create(PrinterDto dto) {
        return createExecutor.create(dto);
    }

    public void delete(Long id) {
        deleteExecutor.delete(id);
    }

    @Transactional
    public ListResponseDto<PrintFileDto> getItems(PrinterSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria){
        ListResponseDto<PrintFileDto> listResponseDto = readPrintFileExecutor.find(searchCriteria,pagingCriteria);

        return listResponseDto;
    }

    @Transactional
    public PrintFileDto saveOrUpdate(PrintFileDto dto){
        PrintFileDto temp = null;
        if (dto.getId() != null){
            temp = updatePrintFileExecutor.update(dto);
        } else {
            temp = createPrintFileExecutor.create(dto);
        }
        return temp;
    }


    public PrintFileDto getFile(Long id) {
        return readPrintFileExecutor.findById(id);
    }
    public void delete(PrintFileDto selectedItem) {
        deletePrintFileExecutor.delete(selectedItem.getId());
    }


    public void print(PrinterDto printerDto, String doc)  {
        String printerNameDesired = printerDto.getName();
        PrintServiceAttributeSet attr_set = new HashPrintServiceAttributeSet();
//        PrintRequestAttributeSet aset     = new HashPrintRequestAttributeSet();
        PrinterName printerName = new PrinterName(printerDto.getName(), Locale.getDefault());


//        PrinterName printerName1 = new PrinterName("Brother DCP-7057", null);
        try {
            String host = "192.255.301.147";
            String printerName2 = "HP LaserJet 5000 Series PCL6";
            String theUrl = "ipp://" + host + "/printers/" + printerName2;
            theUrl = URLEncoder.encode(theUrl, "UTF-8");
            URI myURI = new URI(theUrl);
            PrinterURI printerURI = new PrinterURI(myURI);
//            PrinterName printerName1 = new PrinterName("ipp:\\\\witnw21va\\ipp\\ITDepartment-HP4050", null);

            System.out.println("\tprinterName: " + printerName + " : " + printerDto.getPath());

            if (".".equals(printerDto.getPath())){
                System.out.println("\tprinterName: " + printerName + " : " + printerDto.getPath());
                if (printerName != null) {
                    attr_set.add(printerName);
                }
            } else {
                theUrl = URLEncoder.encode(printerDto.getPath(), "UTF-8");
                myURI = new URI(theUrl);
                printerURI = new PrinterURI(myURI);

                System.out.println("\tprinterURI: " + printerURI + " : " + theUrl);

                if (printerURI != null) {
                    attr_set.add(printerURI);
                }
            }
        }catch (UnsupportedEncodingException | URISyntaxException e){
            e.printStackTrace();
        }
    }

    public CompletableFuture<PrintLabelDto> printFileByWebServie(PrintLabelDto printLabelDto) {
        CompletableFuture<PrintLabelDto> completableFuture = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {
            ShipmentProductDto shipmentProductDto = ShipmentProductDto
                    .builder()
                    .artNumber(printLabelDto.getArtNumber())
                    .artReturn(printLabelDto.getReturnNumber())
                    .counter(printLabelDto.getCounter())
                    .palletOption(printLabelDto.getPalletOption())
                    .build();

            String fileName = printFile(shipmentProductDto,printLabelDto);

            saveOrUpdate(PrintFileDto
                    .builder()
                    .name(fileName)
                    .active(true)
                    .printer(printLabelDto.getPrinter())
                    .path(settingService.getSetting().getTempDirectory()+fileName)
                    .build());

            completableFuture.complete(printLabelDto);
            return null;
        });
        return completableFuture;
    }

    public String printFile(ShipmentProductDto selectedForPrintItem, PrintLabelDto author){
        String fileName = "label"+selectedForPrintItem.getArtNumber()+".pdf";
        try {

            JasperPrint jasperPrint = printPdf(selectedForPrintItem,author);
            JasperExportManager.exportReportToPdfFile(jasperPrint,settingService.getSetting().getTempDirectory()+fileName);
        } catch (JRException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    public JasperPrint printPdf(ShipmentProductDto selectedForPrintItem, PrintLabelDto author){
        JasperPrint jasperPrint = null;
        List<PrintDataDto> listOfUser = new ArrayList();
        listOfUser.add(new PrintDataDto("name 1","country 1"));
        try {

            JRBeanCollectionDataSource data = new JRBeanCollectionDataSource(listOfUser);
            URL defaultImage = getClass().getResource("/jasper/Blank_A4.jasper");
            String reportPath = defaultImage.getPath();

            Map parameters = new HashMap();
            /**
             * Passing ReportTitle and Author as parameters
             */
            SimpleDateFormat dt1 = new SimpleDateFormat("yyyy.MM.dd");
            SimpleDateFormat dt2 = new SimpleDateFormat("yyyyMMdd");

//            parameters.put("COMPANY", selectedForPrintItem);
            parameters.put("NRZWROTU", selectedForPrintItem.getArtReturn());
            parameters.put("NRARTYKULU", selectedForPrintItem.getArtNumber().toString());
            parameters.put("ILOSC", "" + selectedForPrintItem.getCounter());
            parameters.put("DOSTAWCA", "" + selectedForPrintItem.getCompanyName());
            parameters.put("DATAPRZYGOTOWANIA", "" + dt1.format(new Date()));
            parameters.put("Author", author.getAuthor());
            parameters.put("BARCODE",
                    leftPadZeros(dt2.format(new Date()), 8) +
                    leftPadZeros(selectedForPrintItem.getArtReturn().trim(), 8) +
                    leftPadZeros(selectedForPrintItem.getArtNumber().toString().trim(), 8) +
                    leftPadZeros(selectedForPrintItem.getCounter().toString().trim(), 6) +
                    (selectedForPrintItem.getPalletOption()!=null?selectedForPrintItem.getPalletOption():"C")+
                    leftPadZeros(""+author.getPalletCounter(), 2)
            );

            jasperPrint = JasperFillManager.fillReport(reportPath, parameters, data);
        } catch (JRException e){
            e.printStackTrace();
        }
        return jasperPrint;
    }

    public static String leftPadZeros(String str, int num) {
        return String.format("%1$" + num + "s", str).replace(' ', '0');
    }

    public List<PrinterDto> findAllPrinters(){
        List<PrinterDto> printers = new ArrayList<>();
        ListResponseDto<PrinterDto> printerDtoListResponseDto = readExecutor.find(null,null);

        if (!printerDtoListResponseDto.isEmpty()){
            printers.addAll(printerDtoListResponseDto.getData());
        }

        return printers;
    }
//    public List<PrinterDto> findPrinters(){
//        List<PrinterDto> printers = new ArrayList<>();
//        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
//        for (PrintService printService : printServices) {
//            PrinterDto printerDto = readExecutor.findByName(printService.getName());
//            if (printerDto == null){
//                printerDto = new PrinterDto();
//                printerDto.setName(printService.getName());
//                printerDto.setPath(".");
//                printerDto.setIsDefault(false);
//                printerDto.setActive(true);
//                printerDto = createExecutor.create(printerDto);
//            }
//        }
//
//        ListResponseDto<PrinterDto> printerDtoListResponseDto = readExecutor.find(null,null);
//
//        if (!printerDtoListResponseDto.isEmpty()){
//            printers.addAll(printerDtoListResponseDto.getData());
//        }
//
//        return printers;
//    }

    private PrintService findPrintService(String printerName) {
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService printServiceL : printServices) {
            if (printServiceL.getName().trim().equals(printerName)) {
                this.printService = printServiceL;
            }
        }
        return null;
    }
}
