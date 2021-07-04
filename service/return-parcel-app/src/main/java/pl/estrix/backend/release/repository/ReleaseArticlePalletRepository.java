package pl.estrix.backend.release.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import pl.estrix.backend.release.dao.ReleaseArticlePallet;

public interface ReleaseArticlePalletRepository extends ReleaseArticlePalletRepositoryCustom,
        JpaRepository<ReleaseArticlePallet, Long>,
        QueryDslPredicateExecutor<ReleaseArticlePallet> {
}
