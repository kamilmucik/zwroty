package pl.estrix.backend.print.executor;

import org.springframework.stereotype.Component;
import pl.estrix.common.dto.model.PrintFileDto;
import pl.estrix.common.dto.model.PrinterDto;

@Component
public class UpdatePrintFileCommandExecutor extends BasePrintFileCommandExecutor{

    public PrintFileDto update(PrintFileDto dto) {
        return this.mapEntityToDto(
                repository.save(this.mapDtoToEntity(dto)
                ));
    }
}
