package pl.estrix.backend.print.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import pl.estrix.backend.print.dao.Printer;

public interface PrinterRepository extends PrinterRepositoryCustom,JpaRepository<Printer, Long>, QueryDslPredicateExecutor<Printer> {

    Printer findByName(String name);
}
