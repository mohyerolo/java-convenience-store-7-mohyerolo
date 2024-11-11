package store.dto;

import store.domain.product.Product;
import store.domain.promotion.Promotion;

public class ProductDto {
    private final String name;
    private final int price;
    private final int stock;
    private final String promotion;

    private ProductDto(String name, int price, int stock, String promotion) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.promotion = promotion;
    }

    public static ProductDto from(Product product) {
        String promotion = getPromotion(product.getPromotion());
        return new ProductDto(product.getName(), product.getPrice(), product.getQuantity(), promotion);
    }

    private static String getPromotion(Promotion promotion) {
        if (promotion == null) {
            return null;
        }
        return promotion.getName();
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public String getPromotion() {
        return promotion;
    }
}
