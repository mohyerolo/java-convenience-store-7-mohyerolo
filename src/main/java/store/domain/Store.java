package store.domain;

import store.domain.product.ProductStorage;
import store.domain.promotion.Promotions;

public class Store {
    private final ProductStorage productStorage;
    private final Promotions promotions;

    public Store(final ProductStorage productStorage, final Promotions promotions) {
        this.productStorage = productStorage;
        this.promotions = promotions;
    }

    public boolean isStoreHaveProduct(String productName) {
        return productStorage.containsProduct(productName);
    }

    public boolean isStockOk(String productName, int requiredQuantity) {
        return productStorage.isProductStockAvailable(productName, requiredQuantity);
    }

    public boolean isProductCanAppliedPromotion(String productName) {
        return productStorage.isProductHasAvailablePromotion(productName);
    }

    public ProductStorage getProductStorage() {
        return productStorage;
    }

}
