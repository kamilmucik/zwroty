package pl.estrix.backend.imageversion.executor;

import org.springframework.stereotype.Component;
import pl.estrix.common.dto.model.ProductImageVersionRevisionDto;

@Component
public class DeleteProductImageVersionRevisionCommandExecutor extends BaseProductImageVersionRevisionCommandExecute{

    public void delete(ProductImageVersionRevisionDto dto){
        repository.delete(dto.getId());
    }

    public void deleteByVersionId(Long versionId){
        repository.deleteByVersionId(versionId);
    }

}
