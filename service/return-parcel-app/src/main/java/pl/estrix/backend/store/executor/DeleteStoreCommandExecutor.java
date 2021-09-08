package pl.estrix.backend.store.executor;

import org.springframework.stereotype.Component;
import pl.estrix.common.dto.model.StoreDto;

@Component
public class DeleteStoreCommandExecutor extends BaseStoreCommandExecutor {

    public void delete(StoreDto storeDto){
        repository.delete(storeDto.getId());
    }
}
