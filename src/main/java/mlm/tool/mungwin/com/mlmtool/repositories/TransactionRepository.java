package mlm.tool.mungwin.com.mlmtool.repositories;

import mlm.tool.mungwin.com.mlmtool.entities.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Long> {

    Page<Transaction> findAll(Specification<Transaction> transactionSpecification, Pageable pageable);

}
