package charter.purchase;

import charter.customer.Customer;
import charter.customer.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@Validated
@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final CustomerRepository customerRepository;

    public List<Purchase> listCustomerPurchases(Long customerId) {
        return purchaseRepository.findAllByCustomerId(customerId);
    }

    public List<Purchase> listPurchases() {
        return purchaseRepository.findAll();
    }

    /*
    Here purchase should be valid with a small exception that purchase date is actually optional, as it would be filled in for us
    how to tackle such situations??
     */
    @Transactional
    public Purchase createPurchase(Long customerId,/*@Valid*/ Purchase purchase) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found: " + customerId));

        if (purchase.getPurchaseDate() == null) {
            purchase.setPurchaseDate(Instant.now());
        }
        purchase.setCustomer(customer);
        return purchaseRepository.save(purchase);
    }

    @Transactional
    public Purchase deletePurchase(Long id) {
        Purchase purchase = getPurchase(id);
        purchaseRepository.delete(purchase);
        return purchase;
    }

    @Transactional
    public Purchase updatePurchase(@Valid Purchase purchase) {
        if (purchase.getCustomer() == null) {
            Purchase persisted = purchaseRepository.getById(purchase.getId());
            purchase.setCustomer(persisted.getCustomer());
        }
        return purchaseRepository.save(purchase);
    }

    public Purchase getPurchase(Long purchaseId) {
        return purchaseRepository.findById(purchaseId).orElseThrow(() -> new EntityNotFoundException("Purchase not found: " + purchaseId));
    }
}



