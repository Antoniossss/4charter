package charter.purchase;

import charter.AbstractEntity;
import charter.customer.Customer;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.Instant;

@Entity
@Data
public class Purchase extends AbstractEntity {

    @ManyToOne
    private Customer customer;
    @Column(columnDefinition = "TIMESTAMP")
    private Instant purchaseDate;

    private Double price;

}
