package pl.estrix.backend.release.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import pl.estrix.backend.release.dao.ReleaseArticle;
import pl.estrix.backend.store.dao.Store;

import java.time.LocalDate;

public interface ReleaseArticleRepository extends ReleaseArticleRepositoryCustom,
        JpaRepository<ReleaseArticle, Long>,
        QueryDslPredicateExecutor<ReleaseArticle> {

    @Query("SELECT o FROM ReleaseArticle o WHERE o.releaseDate = :releaseDate")
    ReleaseArticle findByReleaseDate(@Param("releaseDate") LocalDate releaseDate);
}
