package pl.estrix.backend.collector.executor;

import org.springframework.stereotype.Component;
import pl.estrix.common.dto.model.CollectorDto;

@Component
public class CreateCollectorCommandExecutor extends BaseCollectorCommandExecutor{

    public CollectorDto create(CollectorDto dto) {
        return this.mapEntityToDto(
                repository.save(this.mapDtoToEntity(dto)
                ));
    }
}
