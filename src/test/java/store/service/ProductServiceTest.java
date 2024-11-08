package store.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.ProductStorage;
import store.domain.Promotion;
import store.domain.PromotionFactory;
import store.domain.Promotions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductServiceTest {
    private final ProductService productService = new ProductService();
    private Promotions promotions;
    private List<String> products;

    @BeforeEach
    void setup() {
        Promotion promotion = PromotionFactory.createPromotion("탄산2+1,2,1,2024-01-01,2024-12-31");
        Promotion promotion2 = PromotionFactory.createPromotion("MD추천상품,1,1,2024-01-01,2024-12-31");
        Promotion promotion3 = PromotionFactory.createPromotion("반짝할인,1,1,2024-11-01,2024-11-30");
        List<Promotion> promotionList = List.of(promotion, promotion2, promotion3);
        promotions = new Promotions(promotionList);

        products = List.of("콜라,1000,10,탄산2+1", "콜라,1000,10,null", "사이다,1000,8,탄산2+1");
    }

    @Test
    void 이름별로_묶어서_생성() {
        ProductStorage productStorage = productService.parseProducts(products, promotions);
        assertTrue(productStorage.containsProduct("콜라"));
        assertTrue(productStorage.containsProduct("사이다"));
    }

    @Test
    void 재고_확인() {
        ProductStorage productStorage = productService.parseProducts(products, promotions);
        assertTrue(productStorage.isProductStockAvailable("콜라", 20));
        assertTrue(productStorage.isProductStockAvailable("사이다", 5));
    }

    @Test
    void 재고_부족() {
        ProductStorage productStorage = productService.parseProducts(products, promotions);
        assertFalse(productStorage.isProductStockAvailable("콜라", 21));
        assertFalse(productStorage.isProductStockAvailable("사이다", 9));
    }

}
