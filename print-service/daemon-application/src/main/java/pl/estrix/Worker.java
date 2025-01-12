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
    @Value("${station.name}")
    private String stationName;

    @Autowired
    private PrinterServie printerServie;

    @Scheduled(cron="0/5 * * * * *")
    public void runTask() {
        System.out.println();
        //1. sprawdź czy jest jakiś plik
        //2. pobierz plik
        try (BufferedInputStream inputStream = new BufferedInputStream(new URL(restAPIUrl + "print/download/" + stationName).openStream());
             FileOutputStream fileOS = new FileOutputStream(temporaryDir+ "/label.pdf")) {
            byte data[] = new byte[1024];
            int byteContent;
            while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
                fileOS.write(data, 0, byteContent);
            }

            printerServie.print(printerName,temporaryDir+ "/label.pdf");
        } catch (IOException e) {
            // handles IO exceptions
        }

        //3. drukuj plik
        //4. usun plik tymczasowy
    }
}