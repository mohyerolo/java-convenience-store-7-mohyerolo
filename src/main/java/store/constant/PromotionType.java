package store.constant;

public enum PromotionType {
    ONE_PLUS_ONE(1, 1) {
        @Override
        public int calcPromoQuantity(int orderQuantity, int productStock) {
            int applicableQuantity = Math.min(orderQuantity, productStock);
            int totalSets = applicableQuantity / (buy + free);
            return totalSets * (buy + free);
        }
    },
    TWO_PLUS_ONE(2, 1) {
        @Override
        public int calcPromoQuantity(int orderQuantity, int productStock) {
            int applicableQuantity = Math.min(orderQuantity, productStock);
            int totalSets = applicableQuantity / (buy + free);
            return totalSets * (buy + free);
        }
    };

    protected final int buy;
    protected final int free;

    PromotionType(final int buy, final int free) {
        this.buy = buy;
        this.free = free;
    }

    public static PromotionType from(final String buy) {
        if (Integer.parseInt(buy) == 1) {
            return ONE_PLUS_ONE;
        }
        return TWO_PLUS_ONE;
    }

    public int getPromoQuantity() {
        return buy + free;
    }

    public int getBuy() {
        return buy;
    }

    public int getFree() {
        return free;
    }

    public abstract int calcPromoQuantity(int orderQuantity, int productStock);
}
