package pl.estrix.backend.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import pl.estrix.backend.store.dao.Store;

public interface StoreRepository extends JpaRepository<Store, Long>, QueryDslPredicateExecutor<Store> {

    @Query("SELECT o FROM Store o WHERE o.group = :group AND o.minVolume <= :weight AND o.maxVolume >= :weight")
    Store findByWeight(@Param("group") Integer group,@Param("weight") Double weight);

}
