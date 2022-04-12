package charter.purchase;

import charter.AbstractEntity;
import charter.customer.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Purchase extends AbstractEntity {

    @ManyToOne
    private Customer customer;

    /*
     * On repository level, I this must be not Null, however on service level it can be null so it will be filled up
     * In such case @Valid fails the call. How to handle that ?
     * */
    @Column(columnDefinition = "TIMESTAMP")
    @NotNull
    private Instant purchaseDate;
    @NotNull
    private Double price;

}
