package pl.estrix.backend.settings.executor;

import org.springframework.beans.factory.annotation.Autowired;
import pl.estrix.backend.base.BaseCommandExecutor;
import pl.estrix.backend.settings.dao.Setting;
import pl.estrix.backend.settings.repository.SettingRepository;
import pl.estrix.common.dto.model.SettingDto;

public class BaseSettingCommandExecutor extends BaseCommandExecutor<Setting, SettingDto> {

    @Autowired
    protected SettingRepository repository;

    @Override
    protected Class<SettingDto> getDtoClass() {
        return SettingDto.class;
    }

    public Setting mapDtoToEntity(SettingDto dto) {
        Setting entity = new Setting();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setValue(dto.getValue());
        return entity;
    }

    public SettingDto mapEntityToDto(Setting entity) {
        if (entity == null){
            return null;
        }
        SettingDto dto = new SettingDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setValue(entity.getValue());
        return dto;
    }

}
