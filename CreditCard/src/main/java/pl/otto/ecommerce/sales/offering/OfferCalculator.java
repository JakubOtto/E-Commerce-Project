package pl.otto.ecommerce.sales.offering;

import pl.otto.ecommerce.sales.cart.CartItem;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OfferCalculator {
    int XthGratis=5;
    BigDecimal totalPromoTreshold = BigDecimal.valueOf(100);

    public Offer calculate(List<CartItem> items) {
        BigDecimal total = BigDecimal.ZERO;
        int totalQty = 0;
        Map<String, Integer> productCounts = new HashMap<>();

        for (CartItem item : items) {
            String productId = item.getProductId();
            int qty = item.getQuantity();
            totalQty += qty;

            productCounts.put(productId, productCounts.getOrDefault(productId, 0) + qty);

            int freeItems = qty / XthGratis;
            int itemsToPay = qty - freeItems;

            BigDecimal price = item.getPrice();
            BigDecimal itemTotal = price.multiply(BigDecimal.valueOf(itemsToPay));
            total = total.add(itemTotal);
        }

        if (total.compareTo(totalPromoTreshold) > 0) {
            total = total.subtract(BigDecimal.valueOf(10));
        }

        return new Offer(total, totalQty);
    }
}