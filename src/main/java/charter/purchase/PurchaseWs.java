package charter.purchase;

import charter.points.PointsInMonth;
import charter.points.PointsService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.time.Instant;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
@RestController
@RequestMapping("purchase")
public class PurchaseWs {

    private final PurchaseService purchaseService;
    private final PointsService pointsService;

    @PostMapping
    public Purchase newPurchase(@RequestBody NewPurchaseDto dto) {
        Purchase purchase = new Purchase();
        purchase.setPrice(dto.price);
        purchase.setPurchaseDate(dto.purchaseDate);
        return purchaseService.createPurchase(dto.customerId, purchase);
    }

    @PutMapping
    public Purchase updatePurchase(@RequestBody Purchase purchase) {
        return purchaseService.updatePurchase(purchase);
    }

    @GetMapping("{id}")
    public Purchase getPurchase(@PathVariable("id") Long id) {
        return purchaseService.getPurchase(id);
    }

    @DeleteMapping("{id}")
    public Purchase deletePurchase(@PathVariable("id") Long id) {
        return purchaseService.deletePurchase(id);
    }

    @GetMapping
    public List<Purchase> listPurchases(@RequestParam(value = "customerId", required = false) Long id) {
        if (id == null) {
            return purchaseService.listPurchases();
        }
        return purchaseService.listCustomerPurchases(id);
    }

    @GetMapping("points")
    public CustomerPointsDto listUserPoints(
            @RequestParam(value = "customerId", required = true) Long customerId,
            @RequestParam(value = "from", required = false) Instant from,
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
