package pl.estrix.backend.print.executor;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import pl.estrix.backend.base.BaseCommandExecutor;
import pl.estrix.backend.print.dao.PrintFile;
import pl.estrix.backend.print.dao.Printer;
import pl.estrix.backend.print.repository.PrintFileRepository;
import pl.estrix.backend.print.repository.PrinterRepository;
import pl.estrix.common.dto.model.PrintFileDto;
import pl.estrix.common.dto.model.PrinterDto;

@Data
public class BasePrintFileCommandExecutor extends BaseCommandExecutor<PrintFile, PrintFileDto> {

    @Autowired
    protected PrintFileRepository repository;

    @Override
    protected Class<PrintFileDto> getDtoClass() {
        return PrintFileDto.class;
    }

    public PrintFile mapDtoToEntity(PrintFileDto dto) {
        PrintFile entity = new PrintFile();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setPath(dto.getPath());
        entity.setActive(dto.getActive());
        entity.setPrinter(dto.getPrinter());
        return entity;
    }

    public PrintFileDto mapEntityToDto(PrintFile entity) {
        if (entity == null){
            return null;
        }
        PrintFileDto dto = new PrintFileDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPath(entity.getPath());
        dto.setActive(entity.getActive());
        dto.setPrinter(entity.getPrinter());
        return dto;
    }
}
