package charter.customer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Customer", description = "Customer related CRUD")
@ApiResponse(responseCode = "404", description = "Customer with given ID was not found")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
@RestController
@RequestMapping(value = "customer",produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerWs {
    private final CustomerService customerService;

    @Operation(summary = "List customers", description = "Allows listing of all customers")
    @GetMapping("list")
    public List<Customer> listCustomers() {
        return customerService.listCustomers();
    }

    @Operation(summary = "Create customer", description = "Creates new customer based on request body")
    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.saveOrUpdate(customer);
    }

    @Operation(summary = "Update customer", description = "Updates customer based on request body")
    @PutMapping("{customerId}")
    public Customer updateCustomer(@PathVariable("customerId") Long customerId, @RequestBody Customer customer) {
        customer.setId(customerId);
        return customerService.saveOrUpdate(customer);
    }

    @Operation(summary = "Get customer", description = "Gets details of single customer")
    @GetMapping("{customerId}")
    public Customer getCustomer(@PathVariable("customerId") Long customerId) {
        return this.customerService.get(customerId);
    }

    @Operation(summary = "Get customer", description = "Removes given customer. Customer cannot have any purchases - in such case operation will fail")
    @ApiResponse(responseCode = "412", description = "Given customer has some purchases. Remove them first")
    @DeleteMapping("{customerId}")
    public Customer deleteCustomer(@PathVariable("customerId") Long customerId) {
        return this.customerService.removeCustomer(customerId);
    }
}
