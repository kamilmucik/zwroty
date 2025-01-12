package pl.estrix.serviece;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.estrix.model.PrinterDto;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PrinterServie {

    private static Logger LOG = LoggerFactory.getLogger(PrinterServie.class);

    public List<PrinterDto> findPrinters(){
        List<PrinterDto> printers = new ArrayList<>();
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService printService : printServices) {
            printers.add(PrinterDto
                    .builder()
                    .name(printService.getName())
                    .build());
        }
        return printers;
    }

    public void print(String printerNameDesired, String doc){
//        String printerNameDesired = "Brother DCP-7057";
        // Get all printer services
        PrintService[] service = PrinterJob.lookupPrintServices(); // list of
        int count = service.length;
        PrintService printer = null;
        // loop to get required printer serices
        for (int i = 0; i < count; i++) {
            if (service[i].getName().equalsIgnoreCase(printerNameDesired)) {
                LOG.info("=====>" + service[i].getName());
                printer = service[i];
            }
        }
        if (!printer.getName().isEmpty()) {
            List<String> supplierNames = Arrays.asList(doc);
            for (String supplierName : supplierNames) {

                File file = new File(doc);
//                File file = new File("C:/temp/" + supplierName);
                // create the docuemnt with file on local machine
                PDDocument document = null;
                try {
                    document = PDDocument.load(file);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                // create the job
                PrinterJob job = PrinterJob.getPrinterJob();
                // set unique name to each file
                job.setJobName(supplierName);
                try {
                    job.setPrintService(printer);
                } catch (PrinterException e) {
                    e.printStackTrace();
                }
                job.setPageable(new PDFPageable(document));
                try {
                    job.print();
                } catch (PrinterException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

}
