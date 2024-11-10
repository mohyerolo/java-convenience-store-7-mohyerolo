package store.domain.promotion;

import java.util.List;
import java.util.stream.Collectors;

public class Promotions {
    private final List<Promotion> promotions;

    private Promotions(final List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public static Promotions from(final List<String> promotionData) {
        List<Promotion> promotions = promotionData.stream()
                .map(PromotionFactory::createPromotion)
                .collect(Collectors.toList());
        return new Promotions(promotions);
    }

    public Promotion findPromotion(final String productPromotion) {
        return promotions.stream()
                .filter(promotion -> promotion.isSamePromotion(productPromotion))
                .findFirst()
                .orElse(null);
    }
}
