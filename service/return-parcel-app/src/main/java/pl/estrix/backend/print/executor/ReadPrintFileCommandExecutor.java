package pl.estrix.backend.print.executor;

import org.springframework.stereotype.Component;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.print.dao.PrintFile;
import pl.estrix.backend.print.dao.Printer;
import pl.estrix.common.base.ListResponseDto;
import pl.estrix.common.dto.PrinterSearchCriteriaDto;
import pl.estrix.common.dto.model.PrintFileDto;
import pl.estrix.common.dto.model.PrinterDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReadPrintFileCommandExecutor extends BasePrintFileCommandExecutor{

    public ListResponseDto<PrintFileDto> find(PrinterSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        List<PrintFile> result = repository.find(searchCriteria, pagingCriteria);
        List<PrintFileDto> queryResultList = result
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());

        return createListResponseDto(pagingCriteria, () -> queryResultList, () -> (int) repository.findCount(searchCriteria));
    }

    public PrintFileDto findById(Long id) {
        return mapEntityToDto(repository.findOne(id));
    }

    public PrintFileDto findByName(String name) {
        return mapEntityToDto(repository.findByName(name));
    }
}
