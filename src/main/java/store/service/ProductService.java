package store.service;

import store.domain.product.ProductStorage;
import store.dto.ProductDto;

import java.util.List;
import java.util.stream.Collectors;

public class ProductService {
    private final ProductStorage productStorage;

    public ProductService(final ProductStorage productStorage) {
        this.productStorage = productStorage;
    }

    public List<ProductDto> makeProductToDto() {
        return productStorage.getOrganizedProducts().entrySet().stream()
                .flatMap(entry -> entry.getValue().stream().map(ProductDto::from))
                .collect(Collectors.toList());
    }
}
