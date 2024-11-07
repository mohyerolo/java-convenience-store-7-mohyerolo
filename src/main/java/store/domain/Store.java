package store.domain;

import java.util.List;
import java.util.Map;

public class Store {
    private final Map<String, List<Product>> products;
    private final Promotions promotions;

    public Store(final Map<String, List<Product>> products, final Promotions promotions) {
        this.products = products;
        this.promotions = promotions;
    }

    public Map<String, List<Product>> getProducts() {
        return products;
    }
}
