package pl.estrix.backend.imageversion.executor;

import org.springframework.stereotype.Component;
import pl.estrix.common.dto.model.ProductImageVersionRevisionDto;

import javax.transaction.Transactional;

@Component
public class UpdateProductImageVersionRevisionCommandExecutor extends BaseProductImageVersionRevisionCommandExecute {

    public ProductImageVersionRevisionDto update(ProductImageVersionRevisionDto storeDto) {
        return this.mapEntityToDto(
                repository.save(this.mapDtoToEntity(storeDto)
                ));
    }

    public void setMainImage(Long id){
        repository.setMainImage(id);
    }

    public void updateDescription (Long id, String description){
        repository.updateDescription(id, description);
    }
}
