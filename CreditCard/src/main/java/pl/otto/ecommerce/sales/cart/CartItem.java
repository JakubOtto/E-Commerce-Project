package pl.otto.ecommerce.sales.cart;

import java.math.BigDecimal;

public class CartItem {
    private final String productId;
    private final Integer qty;
    private final BigDecimal price;

    public CartItem(String productId, Integer qty, BigDecimal price) {
        this.productId = productId;
        this.qty = qty;
        this.price = price;
    }

    public String getProductId() {

        return productId;
    }

    public Integer getQuantity() {
        return qty;
    }

    public BigDecimal getPrice() {
        return this.price;
    }
}
