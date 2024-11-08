package store.dto;

import store.domain.product.Product;
import store.domain.promotion.Promotion;

public class ProductDto {
    private final String name;
    private final int price;
    private final int quantity;
    private final String promotion;

    public ProductDto(Product product) {
        this.name = product.getName();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
        this.promotion = makePromotion(product.getPromotion());
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

    private String makePromotion(Promotion promotion) {
        if (promotion == null) {
            return null;
        }
        return promotion.getName();
    }
}
