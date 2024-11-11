package store.service;

import store.domain.Receipt;
import store.domain.order.OrderItem;
import store.dto.OrderItemDto;
import store.dto.ReceiptDto;

import java.util.ArrayList;
import java.util.List;

public class ReceiptService {

    public ReceiptDto makeReceiptToDto(Receipt receipt, boolean membership) {
        List<OrderItemDto> orderItemDtos = makeOrderItemToDto(receipt.getOrders());
        return new ReceiptDto(orderItemDtos, receipt, membership);
    }

    private List<OrderItemDto> makeOrderItemToDto(List<OrderItem> orderItems) {
        List<OrderItemDto> orderItemDtos = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            orderItemDtos.add(createOrderItemDto(orderItem));
        }
        return orderItemDtos;
    }

    private OrderItemDto createOrderItemDto(OrderItem orderItem) {
        String productName = orderItem.getOrderProductName();
        int orderQuantity = orderItem.getOrderQuantity();
        int freeQuantity = orderItem.calcPromoFreeQuantity();
        int totalAmount = orderItem.calcOrderPrice();
        return new OrderItemDto(productName, orderQuantity, freeQuantity, totalAmount);
    }

}
