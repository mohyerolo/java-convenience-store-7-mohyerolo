package store.domain;

public class Product {
    private final String name;
    private final int price;
    private int quantity;
    private final String promotion;

    public Product(final String name, final int price, final int quantity, final String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public boolean existsPromotion() {
        return !promotion.equals("null");
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getPromotion() {
        return promotion;
    }
}
