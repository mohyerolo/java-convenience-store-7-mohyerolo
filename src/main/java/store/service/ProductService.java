package store.service;

import store.domain.product.Product;
import store.domain.product.ProductFactory;
import store.domain.product.ProductStorage;
import store.domain.promotion.Promotion;
import store.domain.promotion.Promotions;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProductService {
    private static final String NO_PROMOTION_NAME = "null";

    public ProductStorage parseProducts(final List<String> products, final Promotions promotions) {
        Map<String, List<Product>> organizedProducts = organizeProducts(products, promotions);
        return new ProductStorage(organizedProducts);
    }

    private Map<String, List<Product>> organizeProducts(final List<String> products, final Promotions promotions) {
        Map<String, List<Product>> organizedProducts = new LinkedHashMap<>();
        addProductsWithSameName(products, promotions, organizedProducts);
        checkPromoProductHaveNormalStock(organizedProducts, promotions);
        return organizedProducts;
    }

    private void addProductsWithSameName(final List<String> products, final Promotions promotions,
                                         final Map<String, List<Product>> organizedProducts) {
        for (String productData : products) {
            Product product = ProductFactory.createProduct(productData, promotions);
            organizedProducts.computeIfAbsent(product.getName(), k -> new ArrayList<>())
                    .add(product);
        }
    }

    private void checkPromoProductHaveNormalStock(final Map<String, List<Product>> organizedProducts, final Promotions promotions) {
        Promotion noPromo = promotions.findPromotion(NO_PROMOTION_NAME);

        for (Map.Entry<String, List<Product>> entry : organizedProducts.entrySet()) {
            if (shouldAddDummyProduct(entry)) {
                Product promoProduct = entry.getValue().getFirst();
                entry.getValue().add(new Product(promoProduct.getName(), promoProduct.getPrice(), 0, noPromo));
            }
        }
    }

    private boolean shouldAddDummyProduct(Map.Entry<String, List<Product>> entry) {
        return entry.getValue().getFirst().isProductHavePromotion() && entry.getValue().size() == 1;
    }
}
