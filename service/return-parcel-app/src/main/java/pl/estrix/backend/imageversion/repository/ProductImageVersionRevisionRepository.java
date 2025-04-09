package pl.estrix.backend.imageversion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import pl.estrix.backend.imageversion.dao.ProductImageVersion;
import pl.estrix.backend.imageversion.dao.ProductImageVersionRevision;

import javax.transaction.Transactional;
import java.util.List;

public interface ProductImageVersionRevisionRepository extends ProductImageVersionRevisionRepositoryCustom,
        JpaRepository<ProductImageVersionRevision, Long>,
        QueryDslPredicateExecutor<ProductImageVersionRevision> {

    @Transactional
    @Modifying
    @Query("DELETE FROM ProductImageVersionRevision pivr WHERE pivr.productImageVersion.id = :versionId")
    void deleteByVersionId(@Param("versionId") Long versionId);

    @Transactional
    @Modifying
    @Query("UPDATE ProductImageVersionRevision pivr SET pivr.main = false WHERE pivr.hashGroup = :hashGroup")
    void disableMain(@Param("hashGroup") String hash);

    @Transactional
    @Modifying
    @Query("UPDATE ProductImageVersionRevision pivr SET pivr.main = true WHERE pivr.id = :id")
    void setMainImage(@Param("id") Long id);
    @Transactional
    @Modifying
    @Query("UPDATE ProductImageVersionRevision pivr SET pivr.description = :description WHERE pivr.id = :id")
    void updateDescription(@Param("id") Long id, @Param("description") String description);


    @Query("SELECT pivr FROM ProductImageVersionRevision pivr WHERE pivr.hashGroup = :hashGroup ORDER BY pivr.id DESC")
    List<ProductImageVersionRevision> findLastByHash(@Param("hashGroup") String hash);

}
