package pl.otto.ecommerce.sales.offering;

import org.junit.jupiter.api.Test;
import pl.otto.ecommerce.sales.cart.CartItem;
import java.util.List;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Collections;

public class OfferCalculatorTest {
    @Test
    void zeroOfferForEmptyItems() {
        OfferCalculator calculator = new OfferCalculator();

        Offer offer = calculator.calculate(Collections.emptyList());

        assertThat(offer.getTotal()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void testOfferCalculatingPromoWithOneProduct() {
        OfferCalculator calculator = new OfferCalculator();

        List<CartItem> items = new ArrayList<>();
        items.add(new CartItem("product1", 15, BigDecimal.valueOf(10)));

        Offer offer = calculator.calculate(items);

        assertThat(offer.getTotal()).isEqualTo(BigDecimal.valueOf(110));
    }

    @Test
    void testOfferCalculatingPromoWithTwoSameProducts() {
        OfferCalculator calculator = new OfferCalculator();

        List<CartItem> items = new ArrayList<>();
        items.add(new CartItem("product1", 5, BigDecimal.valueOf(10)));
        items.add(new CartItem("product1", 5, BigDecimal.valueOf(10)));

        Offer offer = calculator.calculate(items);

        assertThat(offer.getTotal()).isEqualTo(BigDecimal.valueOf(80));
    }

    @Test
    void testOfferCalculatingPromoWithTwoDifferentProducts() {
        OfferCalculator calculator = new OfferCalculator();

        List<CartItem> items = new ArrayList<>();
        items.add(new CartItem("product1", 5, BigDecimal.valueOf(10)));
        items.add(new CartItem("product2", 10, BigDecimal.valueOf(10)));

        Offer offer = calculator.calculate(items);

        assertThat(offer.getTotal()).isEqualTo(BigDecimal.valueOf(110));
    }

}
