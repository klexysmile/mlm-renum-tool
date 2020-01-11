package mlm.tool.mungwin.com.mlmtool.repositories;

import mlm.tool.mungwin.com.mlmtool.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {

    Page<Product> findAll(Specification<Product> productSpecification);

    Page<Product> findAll(Specification<Product> productSpecification, Pageable pageable);

}
