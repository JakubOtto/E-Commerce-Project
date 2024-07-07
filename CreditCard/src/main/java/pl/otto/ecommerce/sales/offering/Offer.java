package pl.otto.ecommerce.sales.offering;

import java.math.BigDecimal;

public class Offer {
    BigDecimal total;
    int itemsCount;

    public Offer(BigDecimal total, int itemsCount) {
        this.total = total;
        this.itemsCount = itemsCount;
    }

    public BigDecimal getTotal() {
        return this.total;

    }

    public int getItemsCount() {
        return this.itemsCount;
    }
}
