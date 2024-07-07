package pl.otto.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import pl.otto.ecommerce.catalog.ArrayListProductStorage;
import pl.otto.ecommerce.catalog.ProductCatalog;
import pl.otto.ecommerce.payu.PayUCredentials;
import pl.otto.ecommerce.payu.Payu;
import pl.otto.ecommerce.sales.cart.CartStorage;
import pl.otto.ecommerce.sales.offering.OfferCalculator;
import pl.otto.ecommerce.sales.SalesFacade;
import pl.otto.ecommerce.sales.payment.PaymentGateway;
import pl.otto.ecommerce.sales.reservation.ReservationRepository;

import java.math.BigDecimal;

@SpringBootApplication
public class App {
    public static void main (String[] args) {
        System.out.println("Here we go");
        SpringApplication.run(App.class,args);
    }


    @Bean
    PaymentGateway createPaymentGw() {

                return new Payu(
                        new RestTemplate(),
                        PayUCredentials.sandbox(
                                "300746",
                                "2ee86a66e5d97e3fadc400c9f19b065d"
                        )
                );
    }

    @Bean
    SalesFacade createSalesFacade() {

        return new SalesFacade(
                new CartStorage(),
                new OfferCalculator(),
                createPaymentGw(),
                new ReservationRepository()
        );
    }

    @Bean
    ProductCatalog createMyCatalog() {
        var catalog = new ProductCatalog(new ArrayListProductStorage());
        catalog.addProduct("Lego set 8083","nice one ", BigDecimal.valueOf(50), "https://picsum.photos/id/55/200/200");
        catalog.addProduct("Cobi bricks","nice one ",BigDecimal.valueOf(40),"https://picsum.photos/id/55/200/200");
        catalog.addProduct("Lego set 8083","nice one ", BigDecimal.valueOf(50), "https://picsum.photos/id/55/200/200");
        catalog.addProduct("Cobi bricks","nice one ",BigDecimal.valueOf(40),"https://picsum.photos/id/55/200/200");
        catalog.addProduct("Lego set 8083","nice one ", BigDecimal.valueOf(50), "https://picsum.photos/id/55/200/200");
        catalog.addProduct("Cobi bricks","nice one ",BigDecimal.valueOf(40),"https://picsum.photos/id/55/200/200");
        catalog.addProduct("Lego set 8083","nice one ", BigDecimal.valueOf(50), "https://picsum.photos/id/55/200/200");
        catalog.addProduct("Lego set 8083","nice one ", BigDecimal.valueOf(50), "https://picsum.photos/id/55/200/200");
        catalog.addProduct("Lego set 8083","nice one ", BigDecimal.valueOf(50), "https://picsum.photos/id/55/200/200");
        catalog.addProduct("Cobi bricks","nice one ",BigDecimal.valueOf(40),"https://picsum.photos/id/55/200/200");

        return catalog;
    }
}
