package pl.estrix.backend.collector.executor;

import org.springframework.stereotype.Component;
import pl.estrix.common.dto.model.CollectorDto;

@Component
public class DeleteCollectorCommandExecutor extends BaseCollectorCommandExecutor {

    public void delete(CollectorDto dto){
        repository.delete(dto.getId());
    }
}
