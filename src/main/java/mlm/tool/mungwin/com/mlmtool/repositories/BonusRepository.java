package mlm.tool.mungwin.com.mlmtool.repositories;

import mlm.tool.mungwin.com.mlmtool.entities.Bonus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface BonusRepository extends PagingAndSortingRepository<Bonus, Integer> {

    Page<Bonus> findAll(Specification<Bonus> bonusSpecification);

    Page<Bonus> findAll(Specification<Bonus> bonusSpecification, Pageable pageable);

    Optional<Bonus> findBonusByTransactionTypeNameAndPackageId(String type, Integer packageId);

}
