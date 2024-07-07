package pl.otto.ecommerce.catalog;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;


import java.awt.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.ListIterator;

public class ProductCatalogTest {

    @Test
    void itListsProducts() {
        ProductCatalog catalog = createProductCatalog();

        List<Product> productList = catalog.allProducts();

        assert productList.isEmpty();
        assertThat(productList).hasSize(0);
    }

    private static ProductCatalog createProductCatalog() {
        ProductCatalog catalog = new ProductCatalog(new ArrayListProductStorage());
        return catalog;
    }

    @Test
    void itAllowsToAddProducts() {
        ProductCatalog catalog = createProductCatalog();
        catalog.addProduct("Lego set 8833","Nice one",BigDecimal.valueOf(50),"");

        List<Product> productList = catalog.allProducts();
        assertThat(productList)
                .hasSize(1);
    }

    @Test
    void itLoadsProductDetails() {
        ProductCatalog catalog = createProductCatalog();
        String id = catalog.addProduct("Lego set 8833","Nice one",BigDecimal.valueOf(40),"");

        Product loadedProduct = catalog.getProductBy(id);
        assertThat(id).isEqualTo(loadedProduct.getId());
    }

    @Test
    void itAllowsToChangePrice() {
        ProductCatalog catalog = createProductCatalog();
        String id = catalog.addProduct("Lego set 8833","Nice one",BigDecimal.valueOf(40),"");

        catalog.changePrice(id, BigDecimal.valueOf(10.10));

        Product loadedProduct = catalog.getProductBy(id);
        assertThat(BigDecimal.valueOf(10.10))
                .isEqualTo(loadedProduct.getPrice());
    }
}
