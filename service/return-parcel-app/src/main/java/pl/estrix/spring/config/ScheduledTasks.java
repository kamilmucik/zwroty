package pl.estrix.spring.config;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.estrix.backend.dump.MySqlDumpService;
import pl.estrix.backend.settings.service.SettingService;
import pl.estrix.common.dto.model.SettingsDto;

@Component
public class ScheduledTasks {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.url}")
    private String url;

    private String baseName;

    Pattern databaseFinderPattern = Pattern.compile("[\\d\\w]+[?]");

    @Autowired
    private MySqlDumpService mySqlDumpService;

    @Autowired
    private SettingService settingService;

    @Scheduled(fixedRate = 60000)
    public void reportCurrentTime() throws ParseException {
        final Matcher match = databaseFinderPattern.matcher(url);
        if (match.find()) {
            baseName = match.group(0);
            if (baseName.endsWith("?")){
                baseName = baseName.substring(0,baseName.length()-1);
            }
        }

        SettingsDto selected = settingService.getSetting();
        if (selected.getNeedBackup()){
            Date currentDate = new Date();
            Date backupDate = dateFormat.parse(selected.getHour());
            if (dateFormat.format(currentDate).equals(dateFormat.format(backupDate))) {
                mySqlDumpService.dump(selected.getPath(),baseName,username,password);
            }
        }
    }
}
