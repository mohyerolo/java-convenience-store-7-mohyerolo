package store.service;

import store.domain.Product;
import store.domain.ProductFactory;
import store.domain.Promotions;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProductService {

    public Map<String, List<Product>> parseProducts(final List<String> products, final Promotions promotions) {
        Map<String, List<Product>> organizedProducts = new LinkedHashMap<>();
        addProductsWithSameName(products, promotions, organizedProducts);
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

}
