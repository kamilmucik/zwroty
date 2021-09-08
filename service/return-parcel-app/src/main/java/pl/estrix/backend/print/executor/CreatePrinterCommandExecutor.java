package pl.estrix.backend.print.executor;

import org.springframework.stereotype.Component;
import pl.estrix.common.dto.model.PrinterDto;

@Component
public class CreatePrinterCommandExecutor extends BasePrinterCommandExecutor{

    public PrinterDto create(PrinterDto dto) {
        return this.mapEntityToDto(
                repository.save(this.mapDtoToEntity(dto)
                ));
    }
}
