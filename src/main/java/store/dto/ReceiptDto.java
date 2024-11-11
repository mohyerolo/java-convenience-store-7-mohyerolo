package store.dto;

import store.domain.order.Order;
import store.domain.order.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class ReceiptDto {

    private static final double MEMBERSHIP_DISCOUNT_RATE = 0.3;

    private final List<OrderItemDto> orderItemDtos;

    private ReceiptDto(final List<OrderItemDto> orderItemDtos) {
        this.orderItemDtos = orderItemDtos;
    }

    public static ReceiptDto from(final Order order) {
        List<OrderItemDto> orderItemDtos = new ArrayList<>();
        for (OrderItem orderItem : order.getOrders()) {
            orderItemDtos.add(createOrderItemDto(orderItem));
        }
        return new ReceiptDto(orderItemDtos);
    }

    public boolean isPromotionExists() {
        return orderItemDtos.stream()
                .anyMatch(orderItemDto -> orderItemDto.getFreeQuantity() > 0);
    }

    public int calcOrderTotalQuantity() {
        return orderItemDtos.stream()
                .mapToInt(OrderItemDto::getQuantity)
                .sum();
    }

    public int calcOrderTotalAmount() {
        return orderItemDtos.stream()
                .mapToInt(OrderItemDto::getTotalOrderItemAmount)
                .sum();
    }

    public int calcPromotionDiscountAmount() {
        return orderItemDtos.stream()
                .mapToInt(OrderItemDto::calcOrderItemPromotionDiscount)
                .sum();
    }

    public int calcMembershipAmount(final boolean membership) {
        if (!membership) return 0;
        int noPromoAmount = calcTotalNoPromoAmount();
        return calcMembershipDiscountAmount(noPromoAmount);
    }

    public List<OrderItemDto> getOrderItemDtos() {
        return orderItemDtos;
    }

    private static OrderItemDto createOrderItemDto(final OrderItem orderItem) {
        return new OrderItemDto(orderItem);
    }

    private int calcTotalNoPromoAmount() {
        return orderItemDtos.stream()
                .mapToInt(OrderItemDto::calcNotPromotionAmount)
                .sum();
    }

    private int calcMembershipDiscountAmount(final int noPromoAmount) {
        int memberShipDiscountAmount = (int) (noPromoAmount * MEMBERSHIP_DISCOUNT_RATE);
        if (memberShipDiscountAmount > 8000) {
            memberShipDiscountAmount = 8000;
        }
        return memberShipDiscountAmount;
    }

}
