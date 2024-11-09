package store.domain.promotion;

import camp.nextstep.edu.missionutils.DateTimes;
import store.constant.PromotionType;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    public boolean isAvailablePromotion() {
        LocalDateTime now = DateTimes.now();
        return now.isAfter(startDate.atStartOfDay()) && now.isBefore(endDate.atStartOfDay());
    }

    public int calcPromotionSets(int orderQuantity, int productStock) {
        return promotionType.calcPromoSets(orderQuantity, productStock);
    }

    public int getPromoQuantity(int promoSets) {
        return promoSets * promotionType.getPromoQuantity();
    }

    public String getName() {
        return name;
    }
}
