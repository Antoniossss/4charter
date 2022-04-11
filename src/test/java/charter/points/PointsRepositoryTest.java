package charter.points;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ComponentScan("charter")
@Sql("examplePurchases.sql")
class PointsRepositoryTest {
    @Autowired
    private PointsRepository pointsRepository;

    @Test
    public void canSumPointsFromTimeRange() {
        ZonedDateTime from = ZonedDateTime.parse("2022-04-01T00:00:00Z");
        ZonedDateTime to = from.plusDays(from.getMonth().length(false));
        Long result = pointsRepository.calcCustomerPointsEarnedInDateRange(1L, from.toInstant(), to.toInstant());
        assertEquals(23L, result);
    }

    @Test
    public void canSumAllPointsOfUser() {
        Long result = pointsRepository.calcAllCustomerPoints(1L);
        assertEquals(481L, result);
    }

    @Test
    public void canSumPointsGroupedByMonths() {
        List<PointsInMonth> results = pointsRepository.calcCustomerPointsInMonths(1L);
        assertEquals(3, results.size());

        assertEquals(184, results.get(0).points);
        assertEquals(LocalDate.of(2022, 3, 1), results.get(0).localMonth);

        assertEquals(23, results.get(1).points);
        assertEquals(LocalDate.of(2022, 4, 1), results.get(1).localMonth);

        assertEquals(274, results.get(2).points);
        assertEquals(LocalDate.of(2022, 5, 1), results.get(2).localMonth);
    }


}
