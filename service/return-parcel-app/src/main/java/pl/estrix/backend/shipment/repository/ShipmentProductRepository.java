package pl.estrix.backend.shipment.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import pl.estrix.backend.shipment.dao.ShipmentProduct;

import javax.transaction.Transactional;

public interface ShipmentProductRepository extends JpaRepository<ShipmentProduct, Long>, QueryDslPredicateExecutor<ShipmentProduct> {

    Integer countByShipmentId(Long shipmentId);

    @Query("SELECT o FROM ShipmentProduct o WHERE o.shipmentId = :shipmentNumber AND o.ean = :ean")
    ShipmentProduct findByArtNumberAndEan(@Param("shipmentNumber") Long shipmentNumber, @Param("ean") String ean);


    @Transactional
    @Modifying
    @Query("DELETE FROM ShipmentProduct sp WHERE sp.artReturn = :artReturn")
    void deleteByArtReturn(@Param("artReturn") String artReturn);
}
