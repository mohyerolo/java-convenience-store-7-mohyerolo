package store.domain.product;

import store.domain.promotion.Promotions;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProductStorageFactory {

    public static ProductStorage parseProducts(final List<String> products, final Promotions promotions) {
        Map<String, List<Product>> organizedProducts = organizeProducts(products, promotions);
        addDummyProductForPromotionProductMissingNoPromoStock(organizedProducts);
        return new ProductStorage(organizedProducts);
    }

    private static Map<String, List<Product>> organizeProducts(final List<String> products, final Promotions promotions) {
        Map<String, List<Product>> organizedProducts = new LinkedHashMap<>();
        for (String productData : products) {
            Product product = ProductFactory.createProduct(productData, promotions);
            organizedProducts.computeIfAbsent(product.getName(), k -> new ArrayList<>())
                    .add(product);
        }
        return organizedProducts;
    }

    private static void addDummyProductForPromotionProductMissingNoPromoStock(final Map<String, List<Product>> organizedProducts) {
        for (Map.Entry<String, List<Product>> entry : organizedProducts.entrySet()) {
            List<Product> products = entry.getValue();
            if (shouldAddDummyProduct(products)) {
                Product promoProduct = products.getFirst();
                entry.getValue().add(createDummyProduct(promoProduct));
            }
        }
    }

    private static boolean shouldAddDummyProduct(final List<Product> products) {
        return products.getFirst()
                .isProductHasPromotion() && products.size() == 1;
    }

    private static Product createDummyProduct(final Product promoProduct) {
        return Product.stockOutProductOf(promoProduct);
    }
}
