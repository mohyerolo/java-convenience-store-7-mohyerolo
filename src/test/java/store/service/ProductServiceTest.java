package store.service;

import org.junit.jupiter.api.Test;
import store.domain.Product;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ProductServiceTest {
    private final ProductService productService = new ProductService();

    @Test
    void 이름별로_묶어서_생성() {
        List<String> products = List.of("콜라,1000,10,탄산2+1", "콜라,1000,10,null", "사이다,1000,8,탄산2+1");
        Map<String, List<Product>> organizedProduct = productService.parseProducts(products);
        assertThat(organizedProduct.get("콜라").size())
                .isEqualTo(2);
        assertThat(organizedProduct.get("사이다").size())
                .isEqualTo(1);
    }

}
