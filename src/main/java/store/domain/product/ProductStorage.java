package store.domain.product;

import store.domain.promotion.Promotion;

import java.util.List;
import java.util.Map;

public class ProductStorage {
    private final Map<String, List<Product>> organizedProducts;

    public ProductStorage(final Map<String, List<Product>> organizedProducts) {
        this.organizedProducts = organizedProducts;
    }

    public boolean containsProduct(String productName) {
        return organizedProducts.containsKey(productName);
    }

    public boolean isProductStockAvailable(String productName, int requiredQuantity) {
        List<Product> productsList = organizedProducts.get(productName);
        int productQuantity = productsList.stream().mapToInt(Product::getQuantity).sum();
        return productQuantity >= requiredQuantity;
    }

    public Product getOrderProduct(String productName) {
        return organizedProducts.get(productName).getFirst();
    }

    public Map<String, List<Product>> getOrganizedProducts() {
        return organizedProducts;
    }

}
