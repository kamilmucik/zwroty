package pl.estrix.backend.print.executor;

import org.springframework.stereotype.Component;
import pl.estrix.common.dto.model.PrintFileDto;
import pl.estrix.common.dto.model.PrinterDto;

@Component
public class CreatePrintFileCommandExecutor extends BasePrintFileCommandExecutor{

    public PrintFileDto create(PrintFileDto dto) {
        return this.mapEntityToDto(
                repository.save(this.mapDtoToEntity(dto)
                ));
    }
}
