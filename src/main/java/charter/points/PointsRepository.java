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

    default List<PointsInMonth> calcCustomerPointsInMonths(Customer customer) {
        return this.calcCustomerPointsInMonths(customer.getId());
    }

    List<PointsInMonth> calcCustomerPointsInMonths(Long customerId);

    List<PointsInMonth> calcCustomerPointsInMonthsInRange(Long customerId,Instant from, Instant to);

}
