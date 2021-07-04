package pl.estrix.backend.shipment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import pl.estrix.backend.shipment.dao.Shipment;

public interface ShipmentRepository extends JpaRepository<Shipment, Long>, QueryDslPredicateExecutor<Shipment> {

    Shipment findByNumber(String number);

}
