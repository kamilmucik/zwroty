package pl.estrix.serviece;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import org.springframework.stereotype.Service;
import pl.estrix.model.PrinterDto;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.PrinterName;
import javax.print.attribute.standard.PrinterURI;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
public class PrinterServie {

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
                System.out.println("=====>" + service[i].getName());
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
