package mlm.tool.mungwin.com.mlmtool.repositories;

import mlm.tool.mungwin.com.mlmtool.entities.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

    @Query(value = "SELECT SUM(p.quantity) FROM Inventory  p WHERE p.product.id = ?1 ")
    Double getProductStock(Integer productId);

}
