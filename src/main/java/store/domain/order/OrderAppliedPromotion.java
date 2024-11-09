package store.domain.order;

public class OrderAppliedPromotion {
    private OrderItem orderItem;
    private int promotionSets;

    public OrderAppliedPromotion(final OrderItem orderItem, final int promotionSets) {
        this.orderItem = orderItem;
        this.promotionSets = promotionSets;
    }

}
