package charter.points;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class PointsInMonth {
    LocalDate localMonth;
    Long points;
}
