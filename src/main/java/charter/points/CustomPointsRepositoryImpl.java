package charter.points;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
@AllArgsConstructor
public class CustomPointsRepositoryImpl implements PointsRepository {

    private final EntityManager entityManager;
    //probably can be reused as CrteriaQuery or stored procedure
    private static final String SUM_POINTS_RULE = "SUM(CASE WHEN p.price>50 AND p.price <=100 THEN (p.price - 50) " +
            "WHEN p.price>100 THEN (50+(p.price-100)*2) " +
            "ELSE 0 " +
            "END) AS points ";

    public Long calcCustomerPointsEarnedInDateRange(Long customerId, Instant from, Instant to) {
        Query query = entityManager.createQuery(
                        "SELECT " +
                                SUM_POINTS_RULE +
                                "from Purchase p WHERE p.customer.id=:customerId AND p.purchaseDate BETWEEN :from AND :to " +
                                "GROUP BY p.customer")
                .setParameter("customerId", customerId)
                .setParameter("from", from)
                .setParameter("to", to);
        return ((Double) query.getSingleResult()).longValue();
    }

    @Override
    public Long calcAllCustomerPoints(long customerId) {
        Query query = entityManager.createQuery(
                "SELECT " + SUM_POINTS_RULE + " from Purchase p WHERE p.customer.id=:customerId GROUP BY p.customer"
        ).setParameter("customerId", customerId);

        return ((Double) query.getSingleResult()).longValue();
    }

    @Override
    public List<PointsInMonth> calcCustomerPointsInMonths(Long customerId) {
        Query query = entityManager.createQuery(
                "SELECT YEAR(p.purchaseDate) as y, MONTH(p.purchaseDate) as m, " + SUM_POINTS_RULE +
                        " FROM Purchase p WHERE p.customer.id=:customerId GROUP BY y,m ORDER BY y ASC,m ASC"
        ).setParameter("customerId", customerId);
        return ((Stream<Object[]>) query.getResultStream())
                .map(row -> {
                    LocalDate date = LocalDate.of(((Integer) row[0]), ((Integer) row[1]).intValue(), 1);
                    Long points = ((Double) row[2]).longValue();
                    return new PointsInMonth(date, points);
                })
                .collect(Collectors.toList());
    }
}
