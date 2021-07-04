package pl.estrix.backend.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import pl.estrix.backend.event.dao.ShipmentEvent;

public interface ShipmentEventRepository extends JpaRepository<ShipmentEvent, Long>, QueryDslPredicateExecutor<ShipmentEvent> {
}
