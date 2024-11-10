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
        if (isNoPromotion()) {
            return false;
        }

        LocalDateTime now = DateTimes.now();
        return now.isAfter(startDate.atStartOfDay()) && now.isBefore(endDate.atStartOfDay());
    }

    public int calcPromotionQuantity(int orderQuantity, int productStock) {
        return promotionType.calcPromoQuantity(orderQuantity, productStock);
    }

    public int calcPromoFreeQuantity(int orderQuantity, int productStock) {
        return promotionType.calcPromoFreeQuantity(orderQuantity, productStock);
    }

    public boolean isNoPromotion() {
        return promotionType.equals(PromotionType.NO_PROMO);
    }

    public String getName() {
        return name;
    }
}
