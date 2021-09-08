package pl.estrix.backend.print.repository;

import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.print.dao.Printer;
import pl.estrix.common.dto.PrinterSearchCriteriaDto;
import pl.estrix.common.dto.model.PrinterDto;

import java.util.List;

public interface PrinterRepositoryCustom {

    List<Printer> find(PrinterSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria);

    long findCount(PrinterSearchCriteriaDto searchCriteria);
}
