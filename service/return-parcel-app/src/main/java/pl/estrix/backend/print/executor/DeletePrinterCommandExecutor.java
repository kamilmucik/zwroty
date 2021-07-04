package pl.estrix.backend.print.executor;

import org.springframework.stereotype.Component;
import pl.estrix.common.dto.model.PrinterDto;

@Component
public class DeletePrinterCommandExecutor extends BasePrinterCommandExecutor{

    public void delete(PrinterDto dto){
        repository.delete(dto.getId());
    }
    public void delete(Long id){
        repository.delete(id);
    }
}
