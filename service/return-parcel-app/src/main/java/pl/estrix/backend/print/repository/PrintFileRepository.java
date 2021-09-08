package pl.estrix.backend.print.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import pl.estrix.backend.print.dao.PrintFile;
import pl.estrix.backend.print.dao.Printer;

public interface PrintFileRepository extends PrintFileRepositoryCustom,JpaRepository<PrintFile, Long>, QueryDslPredicateExecutor<PrintFile> {

    PrintFile findByName(String name);
}
