package store.constant;

public enum PromotionType {
    NO_PROMO(0, 0),
    ONE_PLUS_ONE(1, 1),
    TWO_PLUS_ONE(2, 1);

    private final int buy;
    private final int free;

    PromotionType(final int buy, final int free) {
        this.buy = buy;
        this.free = free;
    }

    public static PromotionType from(final String buy) {
        if (Integer.parseInt(buy) == 0) {
            return NO_PROMO;
        }
        if (Integer.parseInt(buy) == 1) {
            return ONE_PLUS_ONE;
        }
        return TWO_PLUS_ONE;
    }

    public int getFree() {
        return free;
    }

    public boolean quantityBiggerThanPromoBuyNeed(int quantity) {
        return quantity >= buy;
    }

    public int calcPromoQuantity(int orderQuantity, int productStock) {
        int totalSets = calcTotalPromotionSets(orderQuantity, productStock);
        return totalSets * (buy + free);
    }

    public int calcPromoFreeQuantity(int orderQuantity, int productStock) {
        int totalSets = calcTotalPromotionSets(orderQuantity, productStock);
        return totalSets * free;
    }

    private int calcTotalPromotionSets(int orderQuantity, int productStock) {
        int applicableQuantity = Math.min(orderQuantity, productStock);
        return applicableQuantity / (buy + free);
    }
}
