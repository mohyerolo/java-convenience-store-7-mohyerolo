package store.dto;

public class OrderItemDto {
    private final String productName;
    private final int quantity;
    private final int freeQuantity;
    private final int totalAmount;

    public OrderItemDto(final String productName, final int quantity, final int freeQuantity, final int totalAmount) {
        this.productName = productName;
        this.quantity = quantity;
        this.freeQuantity = freeQuantity;
        this.totalAmount = totalAmount;
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

    public int getTotalAmount() {
        return totalAmount;
    }
}
