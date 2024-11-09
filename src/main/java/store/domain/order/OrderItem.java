package store.domain.order;

import store.constant.PromoProductStatus;
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

    public int calcPromotionSets() {
        return promotionProduct.calcPromotionSets(quantity);
    }

    public PromoProductStatus getQuantityStatusAfterApplyPromotion(int promotionSets) {
        int promotionQuantity = promotionProduct.getPromotionQuantity(promotionSets);
        return getPromoProductQuantityStatus(promotionQuantity);
    }

    private PromoProductStatus getPromoProductQuantityStatus(int orderPromotionQuantity) {
        if (orderPromotionQuantity == quantity) {
            return PromoProductStatus.PERFECT;
        }
        if (promotionProduct.isProductQuantityAvailable(quantity)) {
            return PromoProductStatus.PROMO_PRODUCT_REMAIN;
        }
        return PromoProductStatus.PROMO_PRODUCT_STOCK_OUT;
    }

    public void buyMore(int add) {
        this.quantity += add;
    }
}
