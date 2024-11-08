package store.dto;

import store.domain.product.ProductStorage;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductStorageDto {
    private final Map<String, List<ProductDto>> products;

    private ProductStorageDto(final Map<String, List<ProductDto>> products) {
        this.products = products;
    }

    public static ProductStorageDto from(ProductStorage productStorage) {
        Map<String, List<ProductDto>> productsDto = productStorage.getOrganizedProducts().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(ProductDto::new)
                                .collect(Collectors.toList())
                ));
        return new ProductStorageDto(productsDto);
    }

    public Map<String, List<ProductDto>> getProducts() {
        return products;
    }
}
