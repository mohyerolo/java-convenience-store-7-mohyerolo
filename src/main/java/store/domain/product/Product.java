package store.domain.product;

import store.domain.promotion.Promotion;

public class Product {
    private final static int STOCK_OUT = 0;

    private final String name;
    private final int price;
    private int quantity;
    private final Promotion promotion;

    private Product(final String name, final int price, final int quantity, final Promotion promotion) {
        this.name= name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public static Product of(final String name, final int price, final int quantity) {
        return new Product(name, price, quantity, null);
    }

    public static Product promotionOf(final String name, final int price, final int quantity, final Promotion promotion) {
        return new Product(name, price, quantity, promotion);
    }

    public static Product stockOutProductOf(final Product product) {
        return new Product(product.name, product.price, STOCK_OUT, null);
    }

    public boolean isProductHasPromotion() {
        return promotion != null;
    }

    public boolean isProductHaveAvailablePromotion() {
        return promotion != null && promotion.isAvailablePromotion();
    }

    public int getPromotionQuantity(final int orderQuantity) {
        return promotion.calcPromotionQuantity(orderQuantity, quantity);
    }

    public int getPromotionFreeQuantity() {
        return promotion.getPromotionFreeProduct();
    }

    public boolean isProductQuantityAvailable(final int orderQuantity, final int remainQuantity) {
        int promotionAppliedOrderQuantity = orderQuantity - remainQuantity;
        int remainPromotionApplyQuantity = remainQuantity + getPromotionFreeQuantity();
        return quantity - promotionAppliedOrderQuantity >= remainPromotionApplyQuantity;
    }

    public boolean isQuantityAvailablePromotionMet(final int quantity) {
        return promotion.isPromotionConditionMet(quantity);
    }

    public int calcOrderPrice(final int orderQuantity) {
        return orderQuantity * price;
    }

    public int calcPromoFreeQuantity(final int orderQuantity) {
        if (!isProductHaveAvailablePromotion()) {
            return 0;
        }
        return promotion.calcPromoFreeQuantity(orderQuantity, quantity);
    }

    public int reduceStock(final int orderQuantity) {
        int orderItemQuantity = orderQuantity;
        while (quantity > 0 && orderItemQuantity > 0) {
            quantity--;
            orderItemQuantity--;
        }
        return orderItemQuantity;
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
