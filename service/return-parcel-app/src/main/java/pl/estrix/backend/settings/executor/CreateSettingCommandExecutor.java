package pl.estrix.backend.settings.executor;

import org.springframework.stereotype.Component;
import pl.estrix.common.dto.model.SettingDto;

@Component
public class CreateSettingCommandExecutor extends BaseSettingCommandExecutor {

    public SettingDto create(SettingDto dto) {
        return this.mapEntityToDto(
                repository.save(this.mapDtoToEntity(dto)
                ));
    }
}
