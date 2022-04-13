package charter.points;

import charter.customer.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
public class PointsService {
    private final CustomerRepository customers;
    private final PointsRepository pointsRepository;


    public List<PointsInMonth> calcCustomerPointsEarnedInDateRange(Long customerId, Instant from, Instant to) {
        validateUserId(customerId);
        if (from != null && to != null && from.isAfter(to)) {
            throw new IllegalArgumentException("'from' value cannot be after 'to' value");
        }
        return this.pointsRepository.calcCustomerPointsInMonthsInRange(customerId, from, to);
    }

    public Long calcAllCustomerPoints(Long customerId) {
        validateUserId(customerId);
        return pointsRepository.calcAllCustomerPoints(customerId);
    }

    private void validateUserId(Long customerId) {
        if (!customers.existsById(customerId)) {
            throw new IllegalArgumentException("Customer with given ID does not exist");
        }
    }
}
