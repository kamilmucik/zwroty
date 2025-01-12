package pl.estrix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.estrix.entity.Setting;

import java.util.Optional;

@Repository
public interface SettingRepository extends JpaRepository<Setting, Integer> {

    Optional<Setting> getByName(String name);
}
