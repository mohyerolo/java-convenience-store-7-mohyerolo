package store.domain;

import java.util.List;

public class Promotions {
    private final List<Promotion> promotions;

    public Promotions(final List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public Promotion findPromotion(String productPromotion) {
        return promotions.stream()
                .filter(promotion -> promotion.isSamePromotion(productPromotion))
                .findFirst()
                .orElse(null);
    }
}
