package pl.estrix.backend.collector.executor;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import pl.estrix.backend.base.BaseCommandExecutor;
import pl.estrix.backend.collector.dao.Collector;
import pl.estrix.backend.collector.repository.CollectorRepository;
import pl.estrix.backend.collector.repository.CollectorRepositoryCustom;
import pl.estrix.common.dto.model.CollectorDto;

@Data
public class BaseCollectorCommandExecutor extends BaseCommandExecutor<Collector, CollectorDto> {

    @Autowired
    protected CollectorRepository repository;

    @Autowired
    protected CollectorRepositoryCustom customRepository;

    @Override
    protected Class<CollectorDto> getDtoClass() {
        return CollectorDto.class;
    }

    public Collector mapDtoToEntity(CollectorDto dto) {
        Collector entity = new Collector();
        entity.setId(dto.getId());
        entity.setNumber(dto.getNumber());
        entity.setSessionId(dto.getSessionId());
        return entity;
    }

    public CollectorDto mapEntityToDto(Collector entity) {
        CollectorDto dto = new CollectorDto();
        dto.setId(entity.getId());
        dto.setNumber(entity.getNumber());
        dto.setSessionId(entity.getSessionId());
        return dto;
    }
}
