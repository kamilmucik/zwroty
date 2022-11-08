package pl.estrix.backend.imageversion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import pl.estrix.backend.imageversion.dao.ProductImageVersion;
import pl.estrix.backend.imageversion.dao.ProductImageVersionRevision;

public interface ProductImageVersionRevisionRepository extends ProductImageVersionRevisionRepositoryCustom,
        JpaRepository<ProductImageVersionRevision, Long>,
        QueryDslPredicateExecutor<ProductImageVersionRevision> {
}
