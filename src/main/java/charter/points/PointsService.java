package charter.points;

import charter.customer.CustomerRepository;
import charter.purchase.PurchaseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
public class PointsService {
    private final CustomerRepository customers;
    private final PurchaseRepository purchases;


    public Long calcCustomerPointsEarnedInDateRange(Long customerId, Instant from, Instant to) {
        if (!customers.existsById(customerId)) {
            throw new IllegalArgumentException("Customer with given ID does not exist");
        }
        return 0L;
    }
}
