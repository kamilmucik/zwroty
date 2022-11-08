package pl.estrix.backend.imageversion.executor;

import org.springframework.stereotype.Component;
import pl.estrix.common.dto.model.ProductImageVersionDto;

@Component
public class CreateProductImageVersionCommandExecutor extends BaseProductImageVersionCommandExecute {

    public ProductImageVersionDto create(ProductImageVersionDto storeDto) {
        return this.mapEntityToDto(
                repository.save(this.mapDtoToEntity(storeDto)
                ));
    }
}
