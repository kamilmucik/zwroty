package pl.estrix.backend.dump;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class MySqlDumpService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MySqlDumpService.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmm");

    public void dump(String path, String dbName, String dbUser, String dbPass){
        try {
            LOGGER.info("path: " + path);
            LOGGER.info("dbName: " + dbName);
            LOGGER.info("dbUser: " + dbUser);
            LOGGER.info("dbPass: " + dbPass);

            String folderPath = path  + File.separator + "backup";
            File f1 = new File(folderPath);
            f1.mkdir();

            String savePath = folderPath + File.separator  + dateFormat.format(new Date())+"_backup.sql";

            StringBuilder stringBuilder = new StringBuilder("mysqldump");
            if (!dbUser.isEmpty()){
                stringBuilder.append(" -u");
                stringBuilder.append(dbUser);
            }
            if (!dbPass.isEmpty()){
                stringBuilder.append(" -p");
                stringBuilder.append(dbPass);
            }
            if (!dbName.isEmpty()){
                stringBuilder.append(" ");
                stringBuilder.append(dbName);
            }
            if (!savePath.isEmpty()){
                stringBuilder.append(" -r ");
                stringBuilder.append(savePath);
            }

//            String executeCmd = "mysqldump -u" + dbUser + " -p" + dbPass + " " + dbName + " -r " + savePath ;
//            String executeCmd = "mysqldump -u" + dbUser + " " + dbName + " -r " + savePath ;
            String executeCmd = stringBuilder.toString();

            Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();
            if (processComplete == 0) {
                LOGGER.info("Backup Complete");
            } else {
                LOGGER.error("Backup Failure");
            }
        } catch (IOException | InterruptedException ex) {
            LOGGER.error( "Error at Backup restored: " + ex.getMessage());
        }
    }
}
