package pl.estrix.backend.print.executor;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import pl.estrix.backend.base.BaseCommandExecutor;
import pl.estrix.backend.print.dao.Printer;
import pl.estrix.backend.print.repository.PrinterRepository;
import pl.estrix.common.dto.model.PrinterDto;

@Data
public class BasePrinterCommandExecutor extends BaseCommandExecutor<Printer, PrinterDto> {

    @Autowired
    protected PrinterRepository repository;

    @Override
    protected Class<PrinterDto> getDtoClass() {
        return PrinterDto.class;
    }

    public Printer mapDtoToEntity(PrinterDto dto) {
        Printer entity = new Printer();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setActive(dto.getActive());
        entity.setIsDefault(dto.getIsDefault());
        entity.setPath(dto.getPath());
        return entity;
    }

    public PrinterDto mapEntityToDto(Printer entity) {
        if (entity == null){
            return null;
        }
        PrinterDto dto = new PrinterDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setActive(entity.getActive());
        dto.setIsDefault(entity.getIsDefault());
        dto.setPath(entity.getPath());
        return dto;
    }
}
