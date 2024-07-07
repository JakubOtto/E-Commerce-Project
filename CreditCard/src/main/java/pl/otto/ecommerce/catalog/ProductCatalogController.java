package pl.otto.ecommerce.catalog;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.otto.ecommerce.catalog.Product;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductCatalogController {

    ProductCatalog catalog;

    public ProductCatalogController(ProductCatalog catalog) {
        this.catalog = catalog;
    }

    @GetMapping("/api/products")
    List<Product> getMyProduct() {
        return catalog.allProducts();
    }
}
