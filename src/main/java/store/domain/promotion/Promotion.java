package store.domain.promotion;

import store.constant.PromotionType;

import java.time.LocalDate;

public class Promotion {
    private final String name;
    private final PromotionType promotionType;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Promotion(final String name, final PromotionType promotionType, final LocalDate startDate, final LocalDate endDate) {
        this.name = name;
        this.promotionType = promotionType;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isSamePromotion(String productPromotion) {
        return name.equals(productPromotion);
    }

    public int getPromotionFreeProduct() {
        return promotionType.getFree();
    }

    public String getName() {
        return name;
    }
}