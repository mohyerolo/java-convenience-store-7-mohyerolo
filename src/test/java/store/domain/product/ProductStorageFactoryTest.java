package store.domain.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.product.ProductStorage;
import store.domain.product.ProductStorageFactory;
import store.domain.promotion.Promotions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductStorageFactoryTest {
    private Promotions promotions;
    private List<String> products;
    private ProductStorage productStorage;

    @BeforeEach
    void setup() {
        List<String> promotionData = List.of("탄산2+1,2,1,2024-01-01,2024-12-31", "MD추천상품,1,1,2024-01-01,2024-12-31", "반짝할인,1,1,2024-11-01,2024-11-30");
        promotions = Promotions.from(promotionData);

        products = List.of("콜라,1000,10,탄산2+1", "콜라,1000,10,null", "사이다,1000,8,탄산2+1");
        productStorage = ProductStorageFactory.parseProducts(products, promotions);
    }

    @Test
    void 이름별로_묶어서_생성() {
        assertTrue(productStorage.containsProduct("콜라"));
        assertTrue(productStorage.containsProduct("사이다"));
    }

    @Test
    void 재고_확인() {
        assertTrue(productStorage.isProductStockAvailable("콜라", 20));
        assertTrue(productStorage.isProductStockAvailable("사이다", 5));
    }

    @Test
    void 재고_부족() {
        assertFalse(productStorage.isProductStockAvailable("콜라", 21));
        assertFalse(productStorage.isProductStockAvailable("사이다", 9));
    }

}
