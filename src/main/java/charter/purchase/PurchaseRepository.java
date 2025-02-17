package charter.purchase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findAllByCustomerId(Long id);
    List<Purchase> findAllByCustomerIdOrderByPurchaseDateAsc(Long id);

    boolean existsByCustomerId(Long id);
}
