package pl.estrix.backend.settings.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.estrix.backend.settings.executor.CreateSettingCommandExecutor;
import pl.estrix.backend.settings.executor.ReadSettingCommandExecutor;
import pl.estrix.backend.settings.executor.UpdateSettingCommandExecutor;
import pl.estrix.common.dto.model.SettingDto;
import pl.estrix.common.dto.model.SettingsDto;

import javax.transaction.Transactional;

@Service
public class SettingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SettingService.class);

    @Autowired
    private ReadSettingCommandExecutor readExecutor;
    @Autowired
    private CreateSettingCommandExecutor createExecutor;
    @Autowired
    private UpdateSettingCommandExecutor updateExecutor;

    @Transactional
    public SettingsDto getSetting(){
        SettingsDto result = new SettingsDto();

        SettingDto tempDirectorySettingDto = readExecutor.findByName("tempDirectory");
        if (tempDirectorySettingDto == null) {
            tempDirectorySettingDto = createExecutor.create(SettingDto
                    .builder()
                    .name("tempDirectory")
                    .value("C:/temp/")
                    .build());
        }
        result.setTempDirectory(tempDirectorySettingDto.getValue());
        SettingDto versionDirectorySettingDto = readExecutor.findByName("versionDirectory");
        if (versionDirectorySettingDto == null) {
            versionDirectorySettingDto = createExecutor.create(SettingDto
                    .builder()
                    .name("versionDirectory")
                    .value("C:/temp/")
                    .build());
        }
        result.setVersionDirectory(versionDirectorySettingDto.getValue());

        SettingDto pathSettingDto = readExecutor.findByName("path");
        if (pathSettingDto == null) {
            pathSettingDto = createExecutor.create(SettingDto
                    .builder()
                    .name("path")
                    .value("C:/temp/")
                    .build());
        }
        result.setPath(pathSettingDto.getValue());

        SettingDto hourSettingDto = readExecutor.findByName("hour");
        if (hourSettingDto == null) {
            hourSettingDto = createExecutor.create(SettingDto
                    .builder()
                    .name("hour")
                    .value("01:00")
                    .build());
        }
        result.setHour(hourSettingDto.getValue());

        SettingDto backupSettingDto = readExecutor.findByName("doBackup");
        if (backupSettingDto == null) {
            backupSettingDto = createExecutor.create(SettingDto
                    .builder()
                    .name("doBackup")
                    .value("1")
                    .build());
        }
        result.setNeedBackup(backupSettingDto.getValue().equals("1"));

        return result;
    }

    @Transactional
    public void savetSetting(SettingsDto settingsDto){
        SettingDto tempDirectorySettingDto = readExecutor.findByName("tempDirectory");
        if (tempDirectorySettingDto != null) {
            tempDirectorySettingDto.setValue(settingsDto.getTempDirectory());
            updateExecutor.update(tempDirectorySettingDto);
        }
        SettingDto versionDirectorySettingDto = readExecutor.findByName("versionDirectory");
        if (versionDirectorySettingDto != null) {
            versionDirectorySettingDto.setValue(settingsDto.getVersionDirectory());
            updateExecutor.update(versionDirectorySettingDto);
        } else {
            createExecutor.create(SettingDto
                   .builder()
                   .name("versionDirectory")
                   .value(settingsDto.getVersionDirectory())
                   .build());
        }

        SettingDto pathSettingDto = readExecutor.findByName("path");
        if (pathSettingDto != null) {
            pathSettingDto.setValue(settingsDto.getPath());
            updateExecutor.update(pathSettingDto);
        }

        SettingDto hourSettingDto = readExecutor.findByName("hour");
        if (hourSettingDto != null) {
            hourSettingDto.setValue(settingsDto.getHour());
            updateExecutor.update(hourSettingDto);
        }

        SettingDto backupSettingDto = readExecutor.findByName("doBackup");
        if (backupSettingDto != null) {
            backupSettingDto.setValue(settingsDto.getNeedBackup()?"1":"0");
            updateExecutor.update(backupSettingDto);
        }
    }
}
