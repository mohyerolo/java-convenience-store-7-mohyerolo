package store.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.Product;
import store.domain.Promotion;
import store.domain.PromotionFactory;
import store.domain.Promotions;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ProductServiceTest {
    private final ProductService productService = new ProductService();
    private Promotions promotions;
    @BeforeEach
    void setup() {
        Promotion promotion = PromotionFactory.createPromotion("탄산2+1,2,1,2024-01-01,2024-12-31");
        Promotion promotion2 = PromotionFactory.createPromotion("MD추천상품,1,1,2024-01-01,2024-12-31");
        Promotion promotion3 = PromotionFactory.createPromotion("반짝할인,1,1,2024-11-01,2024-11-30");
        List<Promotion> promotionList = List.of(promotion, promotion2, promotion3);
        promotions = new Promotions(promotionList);
    }

    @Test
    void 이름별로_묶어서_생성() {
        List<String> products = List.of("콜라,1000,10,탄산2+1", "콜라,1000,10,null", "사이다,1000,8,탄산2+1");
        Map<String, List<Product>> organizedProduct = productService.parseProducts(products, promotions);
        assertThat(organizedProduct.get("콜라").size())
                .isEqualTo(2);
        assertThat(organizedProduct.get("사이다").size())
                .isEqualTo(1);
    }

}
