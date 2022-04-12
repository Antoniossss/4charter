package charter.purchase;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.time.Instant;

@AllArgsConstructor
@RestController
@RequestMapping("purchase")
public class PurchaseWs {

    private final PurchaseService purchaseService;

    @PostMapping
    public Purchase newPurchase(@RequestBody NewPurchaseDto dto) {
        Purchase purchase = new Purchase();
        purchase.setPrice(dto.price);
        purchase.setPurchaseDate(dto.purchaseTime);
        return purchaseService.createPurchase(dto.customerId, purchase);
    }

    @GetMapping("{id}")
    public Purchase getPurchase(@PathVariable("id") Long id) {
        return purchaseService.getPurchase(id);
    }

}

class NewPurchaseDto {
    @NotEmpty
    Long customerId;
    Instant purchaseTime;
    @NotEmpty
    Double price;
}
