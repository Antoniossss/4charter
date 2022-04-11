package charter.points;

import charter.customer.Customer;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface PointsRepository {
    default Long calcCustomerPointsEarnedInDateRange(Customer customerId, Instant from, Instant to) {
        return this.calcCustomerPointsEarnedInDateRange(customerId.getId(), from, to);
    }

    Long calcCustomerPointsEarnedInDateRange(Long customerId, Instant from, Instant to);

    Long calcAllCustomerPoints(long l);

    List<PointsInMonth> calcCustomerPointsInMonths(Long customerId);

}
