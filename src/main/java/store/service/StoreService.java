package store.service;

import store.domain.Product;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StoreService {
    private static final String DELIMITER = ",";

    public Map<String, List<Product>> parseProducts(List<String> products) {
        Map<String, List<Product>> organizedProducts = new LinkedHashMap<>();
        addProductsWithSameName(products, organizedProducts);
        return organizedProducts;
    }

    private void addProductsWithSameName(final List<String> products, final Map<String, List<Product>> organizedProducts) {
        for (String product : products) {
            String[] productData = splitProduct(product);
            organizedProducts.computeIfAbsent(productData[0], k -> new ArrayList<>())
                    .add(createProduct(productData));
        }
    }

    private String[] splitProduct(String product) {
        return product.split(DELIMITER);
    }

    private Product createProduct(String[] productData) {
        return new Product(productData);
    }
}
