package pl.otto.ecommerce.sales.cart;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pl.otto.ecommerce.sales.cart.CartItem;

public class Cart {
    Map<String, CartItem> products;

    public Cart() {
        this.products = new HashMap<>();
    }
    public static Cart empty() {
        return new Cart();
    }

    public void addProduct(String productId, BigDecimal price) {
        if (!isInCart(productId)) {
            putIntoCart(productId,price);
        } else {
            increaseProductQty(productId);
        }
    }

    private boolean isInCart(String productId) {
        return products.containsKey(productId);
    }

    private void putIntoCart(String productId, BigDecimal price) {
        products.put(productId, new CartItem(productId,1,price));
    }

    private void increaseProductQty(String productId) {
        CartItem item = products.get(productId);
        products.put(productId, new CartItem(productId, item.getQuantity()+1, item.getPrice()));
    }

    public List<CartItem> getItems() {
        return products.values().stream().toList();
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public Integer getItemsCount() {
        return products.size();
    }


}
