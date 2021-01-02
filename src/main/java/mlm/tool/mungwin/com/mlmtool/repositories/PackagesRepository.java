package mlm.tool.mungwin.com.mlmtool.repositories;

import mlm.tool.mungwin.com.mlmtool.entities.Packages;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PackagesRepository extends JpaRepository<Packages, Integer> {

    List<Packages> findAll(Specification<Packages> specification);

}
