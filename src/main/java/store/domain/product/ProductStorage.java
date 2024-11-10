package store.domain.product;

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

    public void reduceProduct(final String productName, final int quantity, final boolean promo) {
        int remainQuantity = quantity;
        List<Product> products = organizedProducts.get(productName);
        if (products.size() == 1) {
            products.getFirst().reduceStock(remainQuantity);
            return;
        }

        if (promo) {
            remainQuantity = products.getFirst().reduceStock(remainQuantity);
        }
        if (remainQuantity != 0) {
            products.getLast().reduceStock(remainQuantity);
        }
    }

    public Map<String, List<Product>> getOrganizedProducts() {
        return organizedProducts;
    }

}
