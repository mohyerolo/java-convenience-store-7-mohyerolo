package store.dto;

import store.domain.Store;
import store.domain.product.Product;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductStorageDto {
    private final Map<String, List<ProductDto>> products;

    private ProductStorageDto(final Map<String, List<ProductDto>> products) {
        this.products = products;
    }

    public static ProductStorageDto from(Store store) {
        return new ProductStorageDto(makeProductDto(store.getProductStorage().getOrganizedProducts()));
    }

    private static Map<String, List<ProductDto>> makeProductDto(final Map<String, List<Product>> products) {
        Map<String, List<ProductDto>> productsDto = new LinkedHashMap<>();
        for (Map.Entry<String, List<Product>> entry : products.entrySet()) {
            List<ProductDto> productDtos = parseDto(entry);
            productsDto.put(entry.getKey(), productDtos);
        }
        return productsDto;
    }

    private static List<ProductDto> parseDto(Map.Entry<String, List<Product>> entry) {
        return entry.getValue().stream()
                .map(ProductDto::new)
                .collect(Collectors.toList());
    }

    public Map<String, List<ProductDto>> getProducts() {
        return products;
    }
}
