package pl.estrix.backend.imageversion.executor;

import org.springframework.stereotype.Component;
import pl.estrix.common.dto.model.ProductImageVersionDto;

@Component
public class DeleteProductImageVersionCommandExecutor extends BaseProductImageVersionCommandExecute{


    public void delete(ProductImageVersionDto dto){
        repository.delete(dto.getId());
    }

}
