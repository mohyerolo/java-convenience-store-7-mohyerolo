package store.domain.product;

import store.domain.promotion.Promotion;

public class Product {
    private final String name;
    private final int price;
    private int quantity;
    private final Promotion promotion;

    public Product(final String name, final int price, final int quantity, final Promotion promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public boolean isProductHavePromotion() {
        return promotion.isAvailablePromotion();
    }

    public int getPromotionQuantity(int orderQuantity) {
        if (!isProductHavePromotion()) {
           return 0;
        }
        return promotion.calcPromotionQuantity(orderQuantity, quantity);
    }

    public boolean isProductQuantityAvailable(int orderQuantity, int remainQuantity) {
        int promotionAppliedOrderQuantity = orderQuantity - remainQuantity;
        int remainPromotionApplyQuantity = remainQuantity + getPromotionFreeQuantity();
        return quantity - promotionAppliedOrderQuantity >= remainPromotionApplyQuantity;
    }

    public int getPromotionFreeQuantity() {
        return promotion.getPromotionFreeProduct();
    }

    public int calcOrderPrice(int orderQuantity) {
        return orderQuantity * price;
    }

    public int calcPromoFreeQuantity(int orderQuantity) {
        if (!isProductHavePromotion()) {
            return 0;
        }
        return promotion.calcPromoFreeQuantity(orderQuantity, quantity);
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

    public Promotion getPromotion() {
        return promotion;
    }
}
