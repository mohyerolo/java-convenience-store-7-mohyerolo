package store.domain.product;

import store.domain.promotion.Promotion;
import store.domain.promotion.Promotions;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProductStorageFactory {
    private static final String NO_PROMOTION_NAME = "null";
    private static final int STOCK_OUT = 0;

    public static ProductStorage parseProducts(final List<String> products, final Promotions promotions) {
        Map<String, List<Product>> organizedProducts = organizeProducts(products, promotions);
        addDummyProductForPromotionProductMissingNoPromoStock(organizedProducts, promotions);
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

    private static void addDummyProductForPromotionProductMissingNoPromoStock(final Map<String, List<Product>> organizedProducts,
                                                                              final Promotions promotions) {
        Promotion noPromotion = promotions.findPromotion(NO_PROMOTION_NAME);
        for (Map.Entry<String, List<Product>> entry : organizedProducts.entrySet()) {
            if (shouldAddDummyProduct(entry)) {
                Product promoProduct = entry.getValue().getFirst();
                entry.getValue().add(createDummyProduct(promoProduct, noPromotion));
            }
        }
    }

    private static boolean shouldAddDummyProduct(final Map.Entry<String, List<Product>> entry) {
        return entry.getValue().getFirst()
                .isProductHasPromotionNotNoPromoStatus() && entry.getValue().size() == 1;
    }

    private static Product createDummyProduct(final Product promoProduct, final Promotion noPromotion) {
        return new Product(promoProduct.getName(), promoProduct.getPrice(), STOCK_OUT, noPromotion);
    }
}
