package charter.purchase;

import charter.points.PointsInMonth;
import charter.points.PointsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.time.Instant;
import java.util.List;

@Tag(name = "Purchase", description = "Purchases related CRUD")
@ApiResponse(responseCode = "404", description = "Purchase with given ID was not found")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
@RestController
@RequestMapping("purchase")
public class PurchaseWs {

    private final PurchaseService purchaseService;
    private final PointsService pointsService;

    @Operation(summary = "Register purchase", description = "Creates new purchase entry using given parameters")
    @PostMapping
    public Purchase newPurchase(@RequestBody NewPurchaseDto dto) {
        Purchase purchase = new Purchase();
        purchase.setPrice(dto.price);
        purchase.setPurchaseDate(dto.purchaseDate);
        return purchaseService.createPurchase(dto.customerId, purchase);
    }

    @Operation(summary = "Update purchase", description = "Updates purchase entry using given parameters")
    @PutMapping
    public Purchase updatePurchase(@RequestBody Purchase purchase) {
        return purchaseService.updatePurchase(purchase);
    }

    @Operation(summary = "Get purchase", description = "Gets details of purchase")
    @GetMapping("{id}")
    public Purchase getPurchase(@PathVariable("id") Long id) {
        return purchaseService.getPurchase(id);
    }

    @Operation(summary = "Delete purchase", description = "Removes purchase entry")
    @DeleteMapping("{id}")
    public Purchase deletePurchase(@PathVariable("id") Long id) {
        return purchaseService.deletePurchase(id);
    }

    @Operation(summary = "List purchases", description = "Gets list ot purchases. Optionally can be narrowed do a single customer")
    @GetMapping
    public List<Purchase> listPurchases(
            @Parameter(name = "customerId", description = "ID of customer to filter out purchases", required = false)
            @RequestParam(value = "customerId", required = false) Long id) {
        if (id == null) {
            return purchaseService.listPurchases();
        }
        return purchaseService.listCustomerPurchases(id);
    }

    @Operation(summary = "Customer points", description = "Allows to retrieve customer points")
    @ApiResponse(responseCode = "404", description = "Customer with given ID was not found")
    @GetMapping("points")
    public CustomerPointsDto listUserPoints(
            @Parameter(name = "customerId", required = true, description = "ID of the customer", example = "33")
            @RequestParam(value = "customerId", required = true) Long customerId,
            @Parameter(name = "from", required = true, description = "'from' date time filter value", example = "2022-01-01T12:00:00Z")
            @RequestParam(value = "from", required = false) Instant from,
            @Parameter(name = "to", required = true, description = "'to' date time filter value. If missing, fallback to the current timestamp", example = "2022-05-01T12:00:00Z")
            @RequestParam(value = "to", required = false) Instant to) {
        CustomerPointsDto ret = new CustomerPointsDto();
        ret.setTotal(pointsService.calcAllCustomerPoints(customerId));
        ret.setByMonth(pointsService.calcCustomerPointsEarnedInDateRange(customerId, from, to));
        ret.setTotalInRange(ret.getByMonth().stream().mapToLong(PointsInMonth::getPoints).sum());
        return ret;
    }

}

@Data
class NewPurchaseDto {
    @NotEmpty
    Long customerId;
    Instant purchaseDate;
    @NotEmpty
    Double price;
}

@Data
class CustomerPointsDto {
    Long total;
    Long totalInRange;
    List<PointsInMonth> byMonth;
}
