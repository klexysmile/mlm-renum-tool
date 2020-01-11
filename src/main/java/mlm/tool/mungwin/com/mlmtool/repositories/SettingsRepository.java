package mlm.tool.mungwin.com.mlmtool.repositories;

import mlm.tool.mungwin.com.mlmtool.entities.Settings;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface SettingsRepository extends PagingAndSortingRepository<Settings, Integer> {

    Optional<Settings> findByName(String name);

}
