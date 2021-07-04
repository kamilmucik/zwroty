package pl.estrix.backend.settings.executor;

import org.springframework.stereotype.Component;
import pl.estrix.common.dto.model.SettingDto;

@Component
public class UpdateSettingCommandExecutor extends BaseSettingCommandExecutor {

    public SettingDto update(SettingDto dto) {
        return this.mapEntityToDto(
                repository.save(this.mapDtoToEntity(dto)
                ));
    }
}
