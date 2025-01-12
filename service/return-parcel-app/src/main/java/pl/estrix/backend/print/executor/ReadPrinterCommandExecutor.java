package pl.estrix.backend.print.executor;

import com.mysema.query.types.Predicate;
import org.springframework.stereotype.Component;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.print.dao.Printer;
import pl.estrix.common.base.ListResponseDto;
import pl.estrix.common.dto.PrinterSearchCriteriaDto;
import pl.estrix.common.dto.model.PrinterDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReadPrinterCommandExecutor extends BasePrinterCommandExecutor{

    public ListResponseDto<PrinterDto> find(PrinterSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        List<Printer> result = repository.find(searchCriteria, pagingCriteria);
        List<PrinterDto> queryResultList = result
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());

        return createListResponseDto(pagingCriteria, () -> queryResultList, () -> (int) repository.findCount(searchCriteria));
    }

    public PrinterDto findById(Long id) {
        return mapEntityToDto(repository.findOne(id));
    }
    public PrinterDto findByName(String name) {
        return mapEntityToDto(repository.findByName(name));
    }
}
