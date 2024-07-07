package pl.otto.ecommerce.sales.cart;

import org.junit.jupiter.api.Test;
import pl.otto.ecommerce.sales.cart.Cart;
import pl.otto.ecommerce.sales.cart.CartItem;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;


public class CartTest {

    @Test
    void newCartIsEmpty(){
        Cart cart = Cart.empty();

        assertThat(cart.isEmpty()).isTrue();
    }

    @Test
    void notEmptyWhenProductAdded() {
        Cart cart = Cart.empty();
        String productId = thereIsProduct("X");

        cart.addProduct(productId, new BigDecimal(10));

        assertThat(cart.isEmpty()).isFalse();
    }

    @Test
    void cartConsiderSameProductAsSingleItemS1(){
        Cart cart = Cart.empty();
        String productId = thereIsProduct("X");

        cart.addProduct(productId, new BigDecimal(10));

        assertThat(cart.getItemsCount()).isEqualTo(1);
    }
    @Test
    void cartConsiderSameProductAsSingleItemS2(){
        Cart cart = Cart.empty();
        String productId = thereIsProduct("X");

        cart.addProduct(productId,new BigDecimal(10));
        cart.addProduct(productId, new BigDecimal(10));

        assertThat(cart.getItemsCount()).isEqualTo(1);
    }
    @Test
    void cartConsiderSameProductAsSingleItemS3(){
        Cart cart = Cart.empty();
        String productX = thereIsProduct("X");
        String productY = thereIsProduct("y");


        cart.addProduct(productX,new BigDecimal(10));
        cart.addProduct(productY,new BigDecimal(10));

        assertThat(cart.getItemsCount()).isEqualTo(2);
    }

    @Test
    void exposeProductQuantityWithCartLineS1(){
        Cart cart = Cart.empty();
        String productX = thereIsProduct("X");

        cart.addProduct(productX,new BigDecimal(10));

        List<CartItem> cartItems = cart.getItems();

        assertCartLinesContainsProductWithNQuantity(cartItems, productX, 1);
    }
    @Test
    void exposeProductQuantityWithCartLineS2(){
        Cart cart = Cart.empty();
        String productX = thereIsProduct("X");

        cart.addProduct(productX,new BigDecimal(10));
        cart.addProduct(productX,new BigDecimal(10));

        List<CartItem> cartItems = cart.getItems();

        assertCartLinesContainsProductWithNQuantity(cartItems, productX, 2);
    }
    @Test
    void exposeProductQuantityWithCartLineS3(){
        Cart cart = Cart.empty();
        String productX = thereIsProduct("X");
        String productY = thereIsProduct("Y");


        cart.addProduct(productX,new BigDecimal(10));
        cart.addProduct(productX,new BigDecimal(10));
        cart.addProduct(productY,new BigDecimal(10));
        cart.addProduct(productY,new BigDecimal(10));
        cart.addProduct(productY,new BigDecimal(10));


        List<CartItem> cartItems = cart.getItems();

        assertCartLinesContainsProductWithNQuantity(cartItems, productX, 2);
        assertCartLinesContainsProductWithNQuantity(cartItems, productY, 3);
    }

    private void assertCartLinesContainsProductWithNQuantity(List<CartItem> cartItems, String productId, int expectedQuantity) {
        assertThat(cartItems)
                .filteredOn(cartItem -> cartItem.getProductId().equals(productId))
                .extracting(cartItem -> cartItem.getQuantity())
                .first()
                .isEqualTo(expectedQuantity);
    }

    private String thereIsProduct(String id) {
        return id;
    }
}
