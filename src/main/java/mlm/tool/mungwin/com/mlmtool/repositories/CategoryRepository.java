package mlm.tool.mungwin.com.mlmtool.repositories;

import mlm.tool.mungwin.com.mlmtool.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {

    Page<Category> findAll(Specification<Category> categorySpecification);

    Page<Category> findAll(Specification<Category> categorySpecification, Pageable pageable);

}
