package pl.estrix.backend.print.repository.impl;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.Projections;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.base.QueryDslRepositorySupportBase;
import pl.estrix.backend.print.dao.Printer;
import pl.estrix.backend.print.dao.QPrinter;
import pl.estrix.backend.print.repository.PrinterRepositoryCustom;
import pl.estrix.common.dto.PrinterSearchCriteriaDto;
import pl.estrix.common.dto.model.PrinterDto;

import java.util.List;

public class PrinterRepositoryImpl
        extends QueryDslRepositorySupportBase
        implements PrinterRepositoryCustom {

    private static final QPrinter qPrinter = new QPrinter("printer");

    public PrinterRepositoryImpl() {
        super(Printer.class);
    }

    @Override
    public List<Printer> find(PrinterSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        JPQLQuery query = getQueryForFind(searchCriteria);
        query.orderBy(qPrinter.id.asc());
        addPagingCriteriaToQuery(query, pagingCriteria);
        return query.list(Projections.bean(
                Printer.class,
                qPrinter.id,
                qPrinter.name,
                qPrinter.active,
                qPrinter.isDefault,
                qPrinter.path,
                qPrinter.lastUpdate
        ));
    }

    @Override
    public long findCount(PrinterSearchCriteriaDto searchCriteria) {
        JPQLQuery query = getQueryForFind(searchCriteria);
        return query.count();
    }

    private JPQLQuery getQueryForFind(PrinterSearchCriteriaDto searchParams) {
        BooleanBuilder builder = new BooleanBuilder();
        JPQLQuery query = from(qPrinter);

        query.where(builder);
        return query;
    }
}
