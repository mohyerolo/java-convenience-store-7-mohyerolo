package store.domain.order;

import store.constant.PromoProductStatus;

public class OrderAppliedPromotion {
    private final OrderItem orderItem;
    private int promotionSets;
    private PromoProductStatus orderProductPromoQuantityStatus;

    public OrderAppliedPromotion(final OrderItem orderItem, final int promotionSets, final PromoProductStatus orderProductPromoQuantityStatus) {
        this.orderItem = orderItem;
        this.promotionSets = promotionSets;
        this.orderProductPromoQuantityStatus = orderProductPromoQuantityStatus;
    }

}
