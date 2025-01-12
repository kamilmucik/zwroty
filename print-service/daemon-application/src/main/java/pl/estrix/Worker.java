package pl.estrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.estrix.entity.Setting;
import pl.estrix.repository.SettingRepository;
import pl.estrix.serviece.PrinterServie;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Optional;

@Component
public class Worker {

    private static Logger LOG = LoggerFactory.getLogger(Worker.class);

    @Autowired
    private PrinterServie printerServie;
    @Autowired
    private SettingRepository repository;

    @Scheduled(cron="0/5 * * * * *")
    public void runTask() {
        LOG.info("Run print task");
        Optional<Setting> stationName = repository.getByName("station_name");
        Optional<Setting> serviceEndpoint = repository.getByName("service_endpoint");
        Optional<Setting> tmpDirectory = repository.getByName("temporary_dir");
        Optional<Setting> printer = repository.getByName("printer_name");
        Optional<Setting> debug = repository.getByName("debug");

        //1. sprawdź czy jest jakiś plik
        //2. pobierz plik

        try (BufferedInputStream inputStream =
                     new BufferedInputStream(new URL(serviceEndpoint.get().getValue() + "print/download/" + stationName.get().getValue()).openStream());
             FileOutputStream fileOS = new FileOutputStream(tmpDirectory.get().getValue() + "label.pdf")) {
            byte data[] = new byte[1024];
            int byteContent;
            while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
                fileOS.write(data, 0, byteContent);
            }

            LOG.info("\t\tdrukuje{}: {}", printer.get().getValue(), tmpDirectory.get().getValue() + "label.pdf");

            printerServie.print(printer.get().getValue(),tmpDirectory.get().getValue() + "label.pdf");
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }

        //3. drukuj plik
        //4. usun plik tymczasowy
    }
}
