package store.domain.order;

import store.domain.product.Product;

public class OrderItem {
    private final String productName;
    private final int quantity;
    private final Product promotionProduct;

    public OrderItem(final String productName, final int quantity, final Product product) {
        this.productName = productName;
        this.quantity = quantity;
        this.promotionProduct = product;
    }

    public boolean isOrderProductHavePromotion() {
        return promotionProduct.isProductHavePromotion();
    }

    public int calcPromotionSets() {
        return promotionProduct.calcPromotionSets(quantity);
    }
}
