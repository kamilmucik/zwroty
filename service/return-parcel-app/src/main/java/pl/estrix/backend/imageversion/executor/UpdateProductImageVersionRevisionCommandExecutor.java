package pl.estrix.backend.imageversion.executor;

import org.springframework.stereotype.Component;
import pl.estrix.common.dto.model.ProductImageVersionRevisionDto;

@Component
public class UpdateProductImageVersionRevisionCommandExecutor extends BaseProductImageVersionRevisionCommandExecute {

    public ProductImageVersionRevisionDto update(ProductImageVersionRevisionDto storeDto) {
        return this.mapEntityToDto(
                repository.save(this.mapDtoToEntity(storeDto)
                ));
    }
}
