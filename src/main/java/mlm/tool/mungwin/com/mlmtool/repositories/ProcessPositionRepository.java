package mlm.tool.mungwin.com.mlmtool.repositories;

import mlm.tool.mungwin.com.mlmtool.entities.ProcessPosition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProcessPositionRepository extends JpaRepository<ProcessPosition, Integer> {

    Optional<ProcessPosition> findProcessPositionByMessageIdId(Integer messageId);

}
