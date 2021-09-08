package pl.estrix.backend.store.executor;

import org.springframework.stereotype.Component;
import pl.estrix.common.dto.model.StoreDto;

@Component
public class CreateStoreCommandExecutor extends BaseStoreCommandExecutor{

    public StoreDto create(StoreDto storeDto) {
        return this.mapEntityToDto(
                repository.save(this.mapDtoToEntity(storeDto)
                ));
    }
}
