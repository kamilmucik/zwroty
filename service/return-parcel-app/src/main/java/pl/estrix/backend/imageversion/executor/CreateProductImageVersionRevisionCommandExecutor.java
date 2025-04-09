package pl.estrix.backend.imageversion.executor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import pl.estrix.common.dto.model.ProductImageVersionRevisionDto;

@Component
public class CreateProductImageVersionRevisionCommandExecutor extends BaseProductImageVersionRevisionCommandExecute{

    public ProductImageVersionRevisionDto create(ProductImageVersionRevisionDto storeDto) {
        if (StringUtils.isNotEmpty(storeDto.getHashGroup())) {
            repository.disableMain(storeDto.getHashGroup());
        }
        return this.mapEntityToDto(
                repository.save(this.mapDtoToEntity(storeDto)
                ));
    }
}
