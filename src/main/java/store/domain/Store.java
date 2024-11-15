package store.domain;

import store.domain.product.Product;
import store.domain.product.ProductStorage;
import store.domain.promotion.Promotions;

public class Store {
    private final ProductStorage productStorage;
    private final Promotions promotions;

    public Store(final ProductStorage productStorage, final Promotions promotions) {
        this.productStorage = productStorage;
        this.promotions = promotions;
    }

    public boolean isStoreHaveProduct(final String productName) {
        return productStorage.containsProduct(productName);
    }

    public boolean isStockOk(final String productName, final int requiredQuantity) {
        return productStorage.isProductStockAvailable(productName, requiredQuantity);
    }

    public Product getOrderProduct(final String productName) {
        return productStorage.getOrderProduct(productName);
    }

    public ProductStorage getProductStorage() {
        return productStorage;
    }

}
