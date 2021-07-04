package pl.estrix.backend.print.executor;

import org.springframework.stereotype.Component;
import pl.estrix.common.dto.model.PrintFileDto;
import pl.estrix.common.dto.model.PrinterDto;

@Component
public class DeletePrintFileCommandExecutor extends BasePrintFileCommandExecutor{

    public void delete(PrintFileDto dto){
        repository.delete(dto.getId());
    }
    public void delete(Long id){
        repository.delete(id);
    }
}
