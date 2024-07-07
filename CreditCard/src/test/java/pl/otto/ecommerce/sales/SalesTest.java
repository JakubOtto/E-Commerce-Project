package pl.otto.ecommerce.sales;

import org.junit.jupiter.api.Test;
import pl.otto.ecommerce.catalog.Product;
import pl.otto.ecommerce.sales.cart.CartStorage;
import pl.otto.ecommerce.sales.offering.Offer;
import pl.otto.ecommerce.sales.offering.OfferCalculator;
import pl.otto.ecommerce.sales.reservation.ReservationRepository;
import pl.otto.ecommerce.sales.reservation.SpyPaymentGateway;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

public class SalesTest {

    @Test
    void itShowsCurrentOffer() {
        //ARRANGE
        SalesFacade sales = thereIsSalesFacade();
        String customerId = thereIsCustomer("Jakub");

        //ACT
        Offer offer = sales.getCurrentOffer(customerId);

        //ASSERT
        assertThat(offer.getTotal()).isEqualTo(BigDecimal.valueOf(0));
        assertThat(offer.getItemsCount()).isEqualTo(0);
    }

    private SalesFacade thereIsSalesFacade() {
        return new SalesFacade(
                new CartStorage(),
                new OfferCalculator(),
                new SpyPaymentGateway(),
                new ReservationRepository()
        );
    }


    private String thereIsCustomer(String name) {
        return name;
    }

    @Test
    void itAddsProductToCart() {
        SalesFacade sales = thereIsSalesFacade();
        String customerId = thereIsCustomer("Jakub");
        Product product = thereIsProduct("x", BigDecimal.valueOf(10));

        sales.addProduct(customerId,product.getId(),product.getPrice());

        Offer offer = sales.getCurrentOffer(customerId);

        assertThat(offer.getTotal()).isEqualTo(BigDecimal.valueOf(10));
        assertThat(offer.getItemsCount()).isEqualTo(1);

    }

    private Product thereIsProduct(String name, BigDecimal price) {
        Product product = new Product(UUID.randomUUID(), name, "description");
        product.changePrice(price);
        return product;
    }

    @Test
    void itAllowsToRemoveProductFromCart() {

    }

    @Test
    void itAllowsToAcceptOffer() {

    }


}
