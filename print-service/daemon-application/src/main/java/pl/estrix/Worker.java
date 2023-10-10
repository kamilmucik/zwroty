package pl.estrix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.estrix.serviece.PrinterServie;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

@Component
public class Worker {

    @Value("${restapi.url}")
    private String restAPIUrl;

    @Value("${printer.default}")
    private String printerName;

    @Value("${temporary.dir}")
    private String temporaryDir;

    @Value("${debug}")
    private String debug;

    @Autowired
    private PrinterServie printerServie;

    @Scheduled(cron="0/5 * * * * *")
    public void runTask() {
//        System.out.println();
//        System.out.println("Runing at " + new Date());
        if ("true".equals(debug)) {
            System.out.println("restAPIUrl " + restAPIUrl + "print/download");
            System.out.println("printerName " + printerName);
            System.out.println("temporaryDir " + temporaryDir);
//        System.out.println(restAPIUrl + "print/download");
        }

        //1. sprawdź czy jest jakiś plik
        //2. pobierz plik
//        try {
            try (BufferedInputStream inputStream = new BufferedInputStream(new URL(restAPIUrl + "print/download").openStream());
//            try (BufferedInputStream inputStream = new BufferedInputStream(new URL("http://localhost:8080/print/download").openStream());
                 FileOutputStream fileOS = new FileOutputStream(temporaryDir+ "/label.pdf")) {
                byte data[] = new byte[1024];
                int byteContent;
                while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
                    fileOS.write(data, 0, byteContent);
                }

                if ("true".equals(debug)) {
                    System.out.println("\t\tdrukuje: " + printerName);
                }
//                printerServie.print("Brother DCP-7057",temporaryDir+ "/label.pdf");
                printerServie.print(printerName,temporaryDir+ "/label.pdf");
                if ("true".equals(debug)) {
                    System.out.println("\t\twydrukował: " + printerName);
                }
            } catch (IOException e) {
                e.printStackTrace();
//                System.out.println("temporaryDir " + e.toString());
                // handles IO exceptions
            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        //3. drukuj plik
        //4. usun plik tymczasowy
    }
}
