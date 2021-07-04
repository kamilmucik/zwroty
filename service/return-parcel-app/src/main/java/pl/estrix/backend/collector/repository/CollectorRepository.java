package pl.estrix.backend.collector.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import pl.estrix.backend.collector.dao.Collector;

public interface CollectorRepository extends JpaRepository<Collector, Long>, QueryDslPredicateExecutor<Collector> {
}
