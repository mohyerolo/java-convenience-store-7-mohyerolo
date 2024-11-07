package store.service;

import store.domain.Product;
import store.domain.ProductFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProductService {

    public Map<String, List<Product>> parseProducts(List<String> products) {
        Map<String, List<Product>> organizedProducts = new LinkedHashMap<>();
        addProductsWithSameName(products, organizedProducts);
        return organizedProducts;
    }

    private void addProductsWithSameName(final List<String> products, final Map<String, List<Product>> organizedProducts) {
        for (String productData : products) {
            Product product = ProductFactory.createProduct(productData);
            organizedProducts.computeIfAbsent(product.getName(), k -> new ArrayList<>())
                    .add(product);
        }
    }

}
