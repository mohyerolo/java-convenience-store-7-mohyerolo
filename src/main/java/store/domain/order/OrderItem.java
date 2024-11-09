package store.domain.order;

import store.domain.product.Product;

public class OrderItem {
    private final String productName;
    private int quantity;
    private final Product promotionProduct;

    public OrderItem(final String productName, final int quantity, final Product product) {
        this.productName = productName;
        this.quantity = quantity;
        this.promotionProduct = product;
    }

    public boolean isOrderProductHavePromotion() {
        return promotionProduct.isProductHavePromotion();
    }

    public boolean isPromotionWellApplied() {
        return promotionProduct.getPromotionQuantity(quantity) == quantity;
    }

    public int calcRemainQuantityAfterPromotionApply() {
        int promotionQuantity = promotionProduct.getPromotionQuantity(quantity);
        return quantity - promotionQuantity;
    }

    public boolean isRemainQuantityCanAppliedPromotionProduct() {
        return promotionProduct.isProductQuantityAvailable(quantity);
    }

    public String getOrderProductName() {
        return productName;
    }

    public int getFreeProductQuantity() {
        return promotionProduct.getPromotionFreeQuantity();
    }

    public void buyMorePromoProduct() {
        this.quantity += promotionProduct.getPromotionFreeQuantity();
    }
}
