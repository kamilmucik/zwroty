package pl.estrix.backend.store.executor;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import pl.estrix.backend.base.BaseCommandExecutor;
import pl.estrix.backend.store.dao.Store;
import pl.estrix.backend.store.repository.StoreRepository;
import pl.estrix.backend.store.repository.StoreRepositoryCustom;
import pl.estrix.common.dto.model.StoreDto;

@Data
public class BaseStoreCommandExecutor extends BaseCommandExecutor<Store, StoreDto> {

    @Autowired
    protected StoreRepository repository;

    @Autowired
    protected StoreRepositoryCustom customRepository;

    @Override
    protected Class<StoreDto> getDtoClass() {
        return StoreDto.class;
    }

    public Store mapDtoToEntity(StoreDto dto) {
        Store entity = new Store();
        entity.setId(dto.getId());
        entity.setMinVolume(dto.getMinVolume());
        entity.setMaxVolume(dto.getMaxVolume());
        entity.setPlace(dto.getPlace());
        entity.setDescription(dto.getDescription());
        if (dto.getGroup() == null){
            entity.setGroup(1);
        }else {
            entity.setGroup(dto.getGroup());
        }
        return entity;
    }

    public StoreDto mapEntityToDto(Store entity) {
        StoreDto dto = new StoreDto();
        dto.setId(entity.getId());
        dto.setMinVolume(entity.getMinVolume());
        dto.setMaxVolume(entity.getMaxVolume());
        dto.setPlace(entity.getPlace());
        dto.setDescription(entity.getDescription());
        dto.setGroup(entity.getGroup());
        return dto;
    }
}
