package pl.estrix.backend.imageversion.executor;

import org.springframework.stereotype.Component;
import pl.estrix.common.dto.model.ProductImageVersionRevisionDto;

@Component
public class CreateProductImageVersionRevisionCommandExecutor extends BaseProductImageVersionRevisionCommandExecute{

    public ProductImageVersionRevisionDto create(ProductImageVersionRevisionDto storeDto) {
        return this.mapEntityToDto(
                repository.save(this.mapDtoToEntity(storeDto)
                ));
    }
}
