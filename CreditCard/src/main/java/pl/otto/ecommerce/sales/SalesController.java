package pl.otto.ecommerce.sales;

import org.springframework.web.bind.annotation.*;
import pl.otto.ecommerce.sales.offering.Offer;
import pl.otto.ecommerce.sales.reservation.AcceptOfferRequest;
import pl.otto.ecommerce.sales.reservation.ReservationDetails;

import java.math.BigDecimal;

@RestController
public class SalesController {

    SalesFacade sales;

    public SalesController(SalesFacade sales) {
        this.sales = sales;
    }

    @PostMapping("/api/add-product/{productId}")
    void addProduct (@PathVariable("productId") String productId, BigDecimal price){
        String customerId = getCurrentCustomer();
        sales.addProduct(customerId, productId, price);
    }

    @PostMapping("/api/accept-offer")
    ReservationDetails acceptOffer(@RequestBody AcceptOfferRequest request) {
        String customerId = getCurrentCustomer();
        return sales.acceptOffer(customerId, request);
    }

    @GetMapping("/api/current-offer")
    Offer getCurrentOffer() {
        String customerId = getCurrentCustomer();
        return sales.getCurrentOffer(customerId);
    }

    private String getCurrentCustomer() {
        return "guest";
    }
}
