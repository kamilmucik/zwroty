package pl.estrix.backend.collector.executor;

import org.springframework.stereotype.Component;
import pl.estrix.common.dto.model.CollectorDto;

@Component
public class UpdateCollectorCommandExecutor extends BaseCollectorCommandExecutor {

    public CollectorDto update(CollectorDto dto) {
        return this.mapEntityToDto(
                repository.save(this.mapDtoToEntity(dto)
                ));
    }
}
