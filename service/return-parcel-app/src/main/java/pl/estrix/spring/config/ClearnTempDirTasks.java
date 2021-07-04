package pl.estrix.spring.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.estrix.backend.settings.service.SettingService;
import pl.estrix.common.dto.model.SettingsDto;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class ClearnTempDirTasks {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClearnTempDirTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

    @Autowired
    private SettingService settingService;
    /**
     * 1_000 - 1s
     * 60_000 - 1min
     * 3_600_000 - 1godz
     * 86_400_000 - 1dzien
     * @throws ParseException
     */
    @Scheduled(fixedRate = 86_400_000)
    public void reportCurrentTime() throws ParseException {
        SettingsDto settingsDto = settingService.getSetting();

        File folder = new File(settingsDto.getTempDirectory());
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles!=null)
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {


                    long diffInMillies = Math.abs(new Date().getTime() - new Date(listOfFiles[i].lastModified()).getTime());
                    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                    if (diff > 30){
                        LOGGER.info("Usuwam plik: " + listOfFiles[i].getName());
                        //TODO: odkomentować czyszczenie
                        listOfFiles[i].delete();
                    }

                }
            }
    }
}
