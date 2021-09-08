package pl.estrix.backend.settings.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import pl.estrix.backend.settings.dao.Setting;

public interface SettingRepository extends JpaRepository<Setting, Long>, QueryDslPredicateExecutor<Setting> {

    Setting findByName(String name);

}
