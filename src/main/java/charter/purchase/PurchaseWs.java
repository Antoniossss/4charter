package charter.purchase;

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
    public List<Purchase> listPurchases() {
        return purchaseService.listPurchases();
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
