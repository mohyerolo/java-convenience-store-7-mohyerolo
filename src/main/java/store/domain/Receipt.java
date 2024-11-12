package store.domain;

import store.domain.order.Order;
import store.domain.order.OrderItem;

import java.util.List;

public class Receipt {
    private static final double MEMBERSHIP_DISCOUNT_PERCENTAGE = 0.3;
    private static final int MEMBERSHIP_DISCOUNT_LIMIT = 8000;

    private final List<OrderItem> orders;

    private Receipt(final List<OrderItem> orders) {
        this.orders = orders;
    }

    public static Receipt from(final Order order) {
        return new Receipt(order.getOrders());
    }

    public int calcTotalQuantity() {
        return orders.stream()
                .mapToInt(OrderItem::getOrderQuantity)
                .sum();
    }

    public int calcTotalAmount() {
        return orders.stream()
                .mapToInt(OrderItem::calcOrderPrice)
                .sum();
    }

    public int calcMembershipDiscount(final boolean membership) {
        if (!membership) {
            return 0;
        }
        return calcMembershipDiscount();
    }

    public int calcPromotionDiscount() {
        return orders.stream()
                .mapToInt(this::calcPromoDiscountAmount)
                .sum();
    }

    private int calcMembershipDiscount() {
        int totalDiscount = orders.stream()
                .mapToInt(this::calcRemainQuantityMembershipAmount)
                .sum();
        return Math.min(totalDiscount, MEMBERSHIP_DISCOUNT_LIMIT);
    }

    private int calcRemainQuantityMembershipAmount(final OrderItem orderItem) {
        int remainQuantity = getRemainQuantity(orderItem);
        int price = getProductPrice(orderItem);
        return (int) (remainQuantity * price * MEMBERSHIP_DISCOUNT_PERCENTAGE);
    }

    private int getRemainQuantity(final OrderItem orderItem) {
        if (orderItem.isOrderProductHavePromotion()) {
            return orderItem.calcRemainQuantityAfterPromotionApply();
        }
        return orderItem.getOrderQuantity();
    }

    private int getProductPrice(final OrderItem orderItem) {
        return orderItem.calcOrderPrice() / orderItem.getOrderQuantity();
    }

    private int calcPromoDiscountAmount(final OrderItem orderItem) {
        int price = orderItem.calcOrderPrice() / orderItem.getOrderQuantity();
        return orderItem.calcPromoFreeQuantity() * price;
    }

    public List<OrderItem> getOrders() {
        return orders;
    }
}
