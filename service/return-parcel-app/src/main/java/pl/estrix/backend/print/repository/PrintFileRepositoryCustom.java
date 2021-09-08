package pl.estrix.backend.print.repository;

import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.print.dao.PrintFile;
import pl.estrix.backend.print.dao.Printer;
import pl.estrix.common.dto.PrinterSearchCriteriaDto;

import java.util.List;

public interface PrintFileRepositoryCustom {

    List<PrintFile> find(PrinterSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria);

    long findCount(PrinterSearchCriteriaDto searchCriteria);
}
