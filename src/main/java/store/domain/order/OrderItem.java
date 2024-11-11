package store.domain.order;

import store.domain.product.Product;

public class OrderItem {
    private final String productName;
    private int quantity;
    private final Product promotionProduct;

    private OrderItem(final String productName, final int quantity, final Product product) {
        this.productName = productName;
        this.quantity = quantity;
        this.promotionProduct = product;
    }

    public static OrderItem of(final String productName, final int quantity, final Product product) {
        return new OrderItem(productName, quantity, product);
    }

    public boolean isNameDuplicated(final String orderProductName) {
        return productName.equals(orderProductName);
    }


    public boolean isOrderProductHavePromotion() {
        return promotionProduct.isProductHaveAvailablePromotion();
    }

    public boolean isRemainQuantityBiggerThanOrEqualPromoCondition() {
        return promotionProduct.isQuantityAvailablePromotionCondition(quantity);
    }

    public boolean isRemainQuantityBiggerThanOrEqualPromoCondition(final int remainQuantity) {
        return promotionProduct.isQuantityAvailablePromotionCondition(remainQuantity);
    }

    public boolean isMorePromoApplicableInProductStock(final int remainQuantity) {
        return promotionProduct.isProductQuantityAvailable(quantity, remainQuantity);
    }

    public boolean isOrderItemExists() {
        return quantity > 0;
    }

    public int calcRemainQuantityAfterPromotionApply() {
        int promotionQuantity = promotionProduct.getPromotionQuantity(quantity);
        return quantity - promotionQuantity;
    }

    public int getFreeProductQuantity() {
        return promotionProduct.getPromotionFreeQuantity();
    }

    public void buyMorePromoProduct() {
        int promotionFreeQuantity = promotionProduct.getPromotionFreeQuantity();
        increaseQuantity(promotionFreeQuantity);
    }

    public void increaseQuantity(final int additionalQuantity) {
        quantity += additionalQuantity;
    }

    public void cancelOrder(final int cancelQuantity) {
        quantity -= cancelQuantity;
    }

    public int calcOrderPrice(final int calcQuantity) {
        return promotionProduct.calcOrderPrice(calcQuantity);
    }

    public int calcPromoFreeQuantity() {
        return promotionProduct.calcPromoFreeQuantity(quantity);
    }

    public String getOrderProductName() {
        return productName;
    }

    public int getOrderQuantity() {
        return quantity;
    }

}
