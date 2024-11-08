package store.domain;

public class OrderItem {
    private final String productName;
    private final int quantity;

    public OrderItem(final String productName, final int quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }

}
