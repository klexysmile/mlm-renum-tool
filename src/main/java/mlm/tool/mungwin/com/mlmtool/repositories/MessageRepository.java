package mlm.tool.mungwin.com.mlmtool.repositories;

import mlm.tool.mungwin.com.mlmtool.entities.Messages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Messages, Integer> {

    List<Messages> findMessagesByStatus(String status);

    List<Messages> findMessagesByStatusAndType(String status, String type);

}
