package store.dto;

import store.domain.order.OrderItem;

public class OrderItemDto {
    private final String productName;
    private final int quantity;
    private final int freeQuantity;
    private final int remainQuantityNotAppliedPromotion;
    private final int totalOrderItemAmount;

    public OrderItemDto(final OrderItem orderItem) {
        this.productName = orderItem.getOrderProductName();
        this.quantity = orderItem.getOrderQuantity();
        this.freeQuantity = calcPromotionFreeQuantity(orderItem);
        this.remainQuantityNotAppliedPromotion = calcRemainQuantity(orderItem);
        this.totalOrderItemAmount = calcTotalAmount(orderItem);
    }

    public int calcOrderItemPromotionDiscount() {
        return calcPerPrice() * freeQuantity;
    }

    public int calcNotPromotionAmount() {
        return remainQuantityNotAppliedPromotion * calcPerPrice();
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getFreeQuantity() {
        return freeQuantity;
    }

    public int getTotalOrderItemAmount() {
        return totalOrderItemAmount;
    }

    private int calcPerPrice() {
        return totalOrderItemAmount / quantity;
    }

    private int calcPromotionFreeQuantity(final OrderItem orderItem) {
        return orderItem.calcPromoFreeQuantity();
    }

    private int calcRemainQuantity(final OrderItem orderItem) {
        int remainQuantity = orderItem.getOrderQuantity();
        if (orderItem.isOrderProductHavePromotion()) {
            remainQuantity = orderItem.calcRemainQuantityAfterPromotionApply();
        }
        return remainQuantity;
    }

    private int calcTotalAmount(final OrderItem orderItem) {
        return orderItem.calcOrderPrice(quantity);
    }

}
