package charter.purchase;

import charter.customer.Customer;
import charter.customer.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ComponentScan("charter")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
class PurchaseServiceTest {

    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer;

    @BeforeEach
    public void setup() {
        customer = new Customer("Sebastian");
    }

    @Test
    public void purchaseCanBeCreated() {
        customer = customerRepository.save(customer);
        Instant now = Instant.now();

        Purchase p1 = purchaseService.createPurchase(customer.getId(), Purchase.builder()
                .purchaseDate(now)
                .price(123.5)
                .build());
        //sets purchase time to the current instant if not provided
        Purchase p2 = purchaseService.createPurchase(customer.getId(), Purchase.builder()
                .price(432.0)
                .build()
        );
        assertThat(p1).hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("customer", customer)
                .hasFieldOrPropertyWithValue("purchaseDate", now);

        assertThat(p2).hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("customer", customer);
    }

    @Test
    public void failsIfInvalidCustomerIsProvided() {
        assertThatThrownBy(() -> purchaseService.createPurchase(4321L, new Purchase(null, null, 4.0)))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void failsIfInvalidIdUsedForDeletion() {
        assertThatThrownBy(() -> purchaseService.deletePurchase(4321L))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void canUpdatePurchase() {
        customer = customerRepository.save(customer);
        Purchase p1 = purchaseService.createPurchase(customer.getId(), Purchase.builder()
                .price(123.5)
                .build());

        Purchase patch = new Purchase(null, Instant.parse("2022-04-23T12:00:00Z"), 14d);
        patch.setId(p1.getId());

        Purchase updated = purchaseService.updatePurchase(patch);
        assertThat(updated)
                .hasFieldOrPropertyWithValue("customer", customer)
                .hasFieldOrPropertyWithValue("purchaseDate", patch.getPurchaseDate())
                .hasFieldOrPropertyWithValue("price", 14d);

    }

    @Test
    public void itAllowsToChangeCustomer() {
        customer = customerRepository.save(customer);
        Purchase p1 = purchaseService.createPurchase(customer.getId(), Purchase.builder()
                .price(123.5)
                .build());
        Customer newCustomer = customerRepository.save(new Customer("Someone else"));

        Purchase patch = new Purchase(newCustomer, Instant.parse("2022-04-23T12:00:00Z"), 14d);
        Purchase updated = purchaseService.updatePurchase(patch);

        assertThat(updated).hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("customer", newCustomer);
    }
}
