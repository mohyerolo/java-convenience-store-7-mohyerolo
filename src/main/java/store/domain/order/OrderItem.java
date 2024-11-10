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

    public boolean isNameDuplicated(String orderProductName) {
        return productName.equals(orderProductName);
    }

    public void addDuplicatingOrderQuantity(int duplicatedOrderQuantity) {
        quantity += duplicatedOrderQuantity;
    }

    public boolean isOrderProductHavePromotion() {
        return promotionProduct.isProductHaveAvailablePromotion();
    }

    public int calcRemainQuantityAfterPromotionApply() {
        int promotionQuantity = promotionProduct.getPromotionQuantity(quantity);
        return quantity - promotionQuantity;
    }

    public boolean isRemainQuantityBiggerThanPromo(int remainQuantity) {
        return promotionProduct.isQuantityAvailablePromotionMet(remainQuantity);
    }

    public boolean isRemainingQuantityAvailableInPromoStock(int remainQuantity) {
        return promotionProduct.isProductQuantityAvailable(quantity, remainQuantity);
    }

    public int getFreeProductQuantity() {
        return promotionProduct.getPromotionFreeQuantity();
    }

    public void buyMorePromoProduct() {
        this.quantity += promotionProduct.getPromotionFreeQuantity();
    }

    public void cancelOrder(int cancelQuantity) {
        quantity -= cancelQuantity;
    }

    public int calcOrderPrice(int calcQuantity) {
        return promotionProduct.calcOrderPrice(calcQuantity);
    }

    public int calcPromoFreeQuantity() {
        return promotionProduct.calcPromoFreeQuantity(quantity);
    }

    public boolean isOrderItemExists() {
        return quantity > 0;
    }

    public String getOrderProductName() {
        return productName;
    }

    public int getOrderQuantity() {
        return quantity;
    }

}
