package pl.estrix.backend.settings.executor;

import org.springframework.stereotype.Component;
import pl.estrix.common.dto.model.SettingDto;

@Component
public class ReadSettingCommandExecutor extends BaseSettingCommandExecutor {

    public SettingDto findByName(String name) {
        return mapEntityToDto(repository.findByName(name));
    }
}
