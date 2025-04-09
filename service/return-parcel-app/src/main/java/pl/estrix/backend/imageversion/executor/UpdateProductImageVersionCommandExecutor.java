package pl.estrix.backend.imageversion.executor;

import org.springframework.stereotype.Component;
import pl.estrix.common.dto.model.ProductImageVersionDto;

import javax.transaction.Transactional;

@Component
public class UpdateProductImageVersionCommandExecutor extends BaseProductImageVersionCommandExecute{

    @Transactional
    public ProductImageVersionDto update(ProductImageVersionDto storeDto) {
        return this.mapEntityToDto(
                repository.save(this.mapDtoToEntity(storeDto)
                ));
    }

}
