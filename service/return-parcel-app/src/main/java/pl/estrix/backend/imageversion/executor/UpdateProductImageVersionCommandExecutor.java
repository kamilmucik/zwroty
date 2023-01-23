package pl.estrix.backend.imageversion.executor;

import org.springframework.stereotype.Component;
import pl.estrix.common.dto.model.ProductImageVersionDto;

@Component
public class UpdateProductImageVersionCommandExecutor extends BaseProductImageVersionCommandExecute{

    public ProductImageVersionDto update(ProductImageVersionDto storeDto) {
        return this.mapEntityToDto(
                repository.save(this.mapDtoToEntity(storeDto)
                ));
    }

}
