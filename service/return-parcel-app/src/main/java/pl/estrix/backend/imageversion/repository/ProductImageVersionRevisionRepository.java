package pl.estrix.backend.imageversion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import pl.estrix.backend.imageversion.dao.ProductImageVersion;
import pl.estrix.backend.imageversion.dao.ProductImageVersionRevision;

import javax.transaction.Transactional;

public interface ProductImageVersionRevisionRepository extends ProductImageVersionRevisionRepositoryCustom,
        JpaRepository<ProductImageVersionRevision, Long>,
        QueryDslPredicateExecutor<ProductImageVersionRevision> {

    @Transactional
    @Modifying
    @Query("DELETE FROM ProductImageVersionRevision pivr WHERE pivr.productImageVersion.id = :versionId")
    void deleteByVersionId(@Param("versionId") Long versionId);

    @Query(value = "SELECT * FROM product_image_version_revision WHERE product_image_version_id = :parentId AND img_top IS NOT NULL  ORDER BY id DESC LIMIT 1", nativeQuery = true)
    ProductImageVersionRevision findLastImageByTopPosition(@Param("parentId") Long productImageVersionId);
    @Query(value = "SELECT * FROM product_image_version_revision WHERE product_image_version_id = :parentId AND img_Bottom IS NOT NULL  ORDER BY id DESC LIMIT 1", nativeQuery = true)
    ProductImageVersionRevision findLastImageByBottomPosition(@Param("parentId") Long productImageVersionId);
    @Query(value = "SELECT * FROM product_image_version_revision WHERE product_image_version_id = :parentId AND img_left IS NOT NULL  ORDER BY id DESC LIMIT 1", nativeQuery = true)
    ProductImageVersionRevision findLastImageByLeftPosition(@Param("parentId") Long productImageVersionId);
    @Query(value = "SELECT * FROM product_image_version_revision WHERE product_image_version_id = :parentId AND img_right IS NOT NULL  ORDER BY id DESC LIMIT 1", nativeQuery = true)
    ProductImageVersionRevision findLastImageByRightPosition(@Param("parentId") Long productImageVersionId);
    @Query(value = "SELECT * FROM product_image_version_revision WHERE product_image_version_id = :parentId AND img_front IS NOT NULL  ORDER BY id DESC LIMIT 1", nativeQuery = true)
    ProductImageVersionRevision findLastImageByFrontPosition(@Param("parentId") Long productImageVersionId);
    @Query(value = "SELECT * FROM product_image_version_revision WHERE product_image_version_id = :parentId AND img_back IS NOT NULL  ORDER BY id DESC LIMIT 1", nativeQuery = true)
    ProductImageVersionRevision findLastImageByBackPosition(@Param("parentId") Long productImageVersionId);

}
