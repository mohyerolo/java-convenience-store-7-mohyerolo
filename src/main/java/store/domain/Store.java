package store.domain;

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

    public ProductStorage getProductStorage() {
        return productStorage;
    }

}
