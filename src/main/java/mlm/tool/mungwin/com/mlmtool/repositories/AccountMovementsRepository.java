package mlm.tool.mungwin.com.mlmtool.repositories;

import mlm.tool.mungwin.com.mlmtool.entities.AccountMovements;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface AccountMovementsRepository extends PagingAndSortingRepository<AccountMovements, Long> {

}
