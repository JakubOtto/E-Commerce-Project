package pl.otto.ecommerce.sales.cart;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CartStorage {
    Map<String, Cart> carts = new HashMap<>();

    public Optional<Cart> loadForCustomer(String customerId) {
        return Optional.ofNullable(carts.get(customerId));
    }

    public void saveForCustomer(String customerId, Cart cart) {
        carts.put(customerId, cart);
    }
}