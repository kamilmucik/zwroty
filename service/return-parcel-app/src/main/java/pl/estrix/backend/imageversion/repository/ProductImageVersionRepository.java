package pl.estrix.backend.imageversion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import pl.estrix.backend.imageversion.dao.ProductImageVersion;

public interface ProductImageVersionRepository extends ProductImageVersionRepositoryCustom,
        JpaRepository<ProductImageVersion, Long>,
        QueryDslPredicateExecutor<ProductImageVersion> {
}
