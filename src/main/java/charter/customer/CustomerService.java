package charter.customer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@Service
@Validated
public class CustomerService {
    private final CustomerRepository customerRepository;

    public List<Customer> listCustomers() {
        return customerRepository.findAll();
    }

    @Transactional
    public Customer saveOrUpdate(@Valid Customer newCustomer) {
        return customerRepository.save(newCustomer);
    }

    @Transactional
    public Customer removeCustomer(Long id) {
        Customer customer = get(id);
        customerRepository.delete(customer);
        return customer;
    }

    public Customer get(Long customerId) {
        return customerRepository.findById(customerId).orElseThrow(() -> new EntityNotFoundException("Cusomer not found: " + customerId));
    }
}
