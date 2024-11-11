package store.dto;

import store.domain.Receipt;

import java.util.List;

public class ReceiptDto {

    private final List<OrderItemDto> orderItemDtos;
    private final int totalQuantity;
    private final int totalAmount;
    private final int promotionDiscount;
    private final int membershipDiscount;

    public ReceiptDto(List<OrderItemDto> orderItemDtos, Receipt receipt, boolean membership) {
        this.orderItemDtos = orderItemDtos;
        totalQuantity = receipt.calcTotalQuantity();
        totalAmount = receipt.calcTotalAmount();
        promotionDiscount = receipt.calcPromotionDiscount();
        membershipDiscount = receipt.calcMembershipDiscount(membership);
    }

    public boolean isPromotionExists() {
        return orderItemDtos.stream()
                .anyMatch(orderItemDto -> orderItemDto.getFreeQuantity() > 0);
    }

    public List<OrderItemDto> getOrderItemDtos() {
        return orderItemDtos;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public int getPromotionDiscount() {
        return promotionDiscount;
    }

    public int getMembershipDiscount() {
        return membershipDiscount;
    }
}
