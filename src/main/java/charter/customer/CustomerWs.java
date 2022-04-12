package charter.customer;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
@RestController
@RequestMapping("customer")
public class CustomerWs {
    private final CustomerService customerService;

    @GetMapping("list")
    public List<Customer> listCustomers() {
        return customerService.listCustomers();
    }

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.saveOrUpdate(customer);
    }

    @PutMapping("{customerId}")
    public Customer updateCustomer(@PathVariable("customerId") Long customerId, @RequestBody Customer customer) {
        customer.setId(customerId);
        return customerService.saveOrUpdate(customer);
    }

    @GetMapping("{customerId}")
    public Customer getCustomer(@PathVariable("customerId") Long customerId) {
        return this.customerService.get(customerId);
    }

    @DeleteMapping("{customerId}")
    public Customer deleteCustomer(@PathVariable("customerId") Long customerId) {
        return this.customerService.removeCustomer(customerId);
    }
}
