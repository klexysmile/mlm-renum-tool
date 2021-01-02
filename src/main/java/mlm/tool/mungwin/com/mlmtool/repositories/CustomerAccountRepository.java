package mlm.tool.mungwin.com.mlmtool.repositories;

import jdk.nashorn.internal.runtime.options.Option;
import mlm.tool.mungwin.com.mlmtool.entities.CustomerAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CustomerAccountRepository extends JpaRepository<CustomerAccount, Long> {

    Optional<CustomerAccount> findCustomerAccountByCustomerId(Long customerId);

    @Query("SELECT SUM (a.points) FROM CustomerAccount a WHERE a.id = ?1 ")
    Double sumCustomerAccountPoints(Long customerAccountId);

    @Modifying
    @Query(value = " UPDATE CustomerAccount c SET c.points = (c.points + :pta), c.networkSize = (c.networkSize +  1), c.lastUpdate = :ld WHERE c.customerId IN :cids ")
    int updateCustomerAccountPoints(@Param("pta") Integer pointsToAdd, @Param("cids") List<Long> customerIds, @Param("ld") Date lastUpdate);

    List<CustomerAccount> findAllByCustomerIdIn(List<Long> customerIds);
}
