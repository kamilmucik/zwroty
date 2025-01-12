package pl.estrix.backend.print.repository.impl;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.Projections;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.base.QueryDslRepositorySupportBase;
import pl.estrix.backend.print.dao.PrintFile;
import pl.estrix.backend.print.dao.Printer;
import pl.estrix.backend.print.dao.QPrintFile;
import pl.estrix.backend.print.dao.QPrinter;
import pl.estrix.backend.print.repository.PrintFileRepositoryCustom;
import pl.estrix.backend.print.repository.PrinterRepositoryCustom;
import pl.estrix.common.dto.PrinterSearchCriteriaDto;

import java.util.List;

public class PrintFileRepositoryImpl
        extends QueryDslRepositorySupportBase
        implements PrintFileRepositoryCustom {

    private static final QPrintFile qPrintFile = new QPrintFile("printFile");

    public PrintFileRepositoryImpl() {
        super(PrintFile.class);
    }

    @Override
    public List<PrintFile> find(PrinterSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        JPQLQuery query = getQueryForFind(searchCriteria);
        query.orderBy(qPrintFile.id.desc());
        addPagingCriteriaToQuery(query, pagingCriteria);
        return query.list(Projections.bean(
                PrintFile.class,
                qPrintFile.id,
                qPrintFile.name,
                qPrintFile.path,
                qPrintFile.printer,
                qPrintFile.active
        ));
    }

    @Override
    public long findCount(PrinterSearchCriteriaDto searchCriteria) {
        JPQLQuery query = getQueryForFind(searchCriteria);
        return query.count();
    }

    private JPQLQuery getQueryForFind(PrinterSearchCriteriaDto searchParams) {
        BooleanBuilder builder = new BooleanBuilder();
        JPQLQuery query = from(qPrintFile);

        builder.and(qPrintFile.active.eq(true));

        if (searchParams.getPrinter() != null) {
            builder.and(qPrintFile.printer.eq(searchParams.getPrinter()));
        }

        query.where(builder);
        return query;
    }
}
