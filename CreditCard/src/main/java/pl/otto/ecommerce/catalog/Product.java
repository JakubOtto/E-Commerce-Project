package pl.otto.ecommerce.catalog;

import java.math.BigDecimal;
import java.util.UUID;

public class Product {
    private  String id;
    private  String name;
    private  String description;
    private BigDecimal price;



    private String image;
    Product() {
    }

    public Product(UUID id, String name, String description, BigDecimal price, String image) {
        this.id = id.toString();
        this.name = name;
        this.description = description;
        this.price = price;
        this.image=image;
    }

    public String getImage() {
        return image;
    }

    public String getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void changePrice(BigDecimal newPrice) {
        this.price = newPrice;
    }
}
