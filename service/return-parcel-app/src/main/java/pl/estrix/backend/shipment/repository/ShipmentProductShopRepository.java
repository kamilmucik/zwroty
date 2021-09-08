package pl.estrix.backend.shipment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import pl.estrix.backend.shipment.dao.ShipmentProduct;
import pl.estrix.backend.shipment.dao.ShipmentProductShop;

import javax.transaction.Transactional;

public interface ShipmentProductShopRepository extends JpaRepository<ShipmentProductShop, Long>, QueryDslPredicateExecutor<ShipmentProductShop> {

    @Transactional
    @Modifying
    @Query("DELETE FROM ShipmentProductShop sps WHERE sps.productId = :productId")
    void deleteByProductId(@Param("productId") Long productId);

    @Transactional
    @Modifying
    @Query("DELETE FROM ShipmentProductShop sps WHERE sps.artReturn = :artReturn")
    void deleteByArtReturn(@Param("artReturn") String artReturn);
}
