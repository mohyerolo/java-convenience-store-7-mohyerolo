package store.domain;

public class Order {
    private final String productName;
    private final int quantity;

    public Order(final String productName, final int quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }

}
