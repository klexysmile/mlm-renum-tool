package mlm.tool.mungwin.com.mlmtool.repositories;

import mlm.tool.mungwin.com.mlmtool.entities.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DepartmentRepository extends PagingAndSortingRepository<Department, Integer> {

    Page<Department> findAll(Specification<Department> departmentSpecification);

    Page<Department> findAll(Specification<Department> departmentSpecification, Pageable pageable);

}
