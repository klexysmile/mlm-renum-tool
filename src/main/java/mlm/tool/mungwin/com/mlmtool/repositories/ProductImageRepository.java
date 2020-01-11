package mlm.tool.mungwin.com.mlmtool.repositories;

import mlm.tool.mungwin.com.mlmtool.entities.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {

    Optional<ProductImage> findByProductIdAndOrder(Integer productId, Integer order);

}
