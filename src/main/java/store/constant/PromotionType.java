package store.constant;

public enum PromotionType {
    OnePlusOne(1, 1) {
        @Override
        public int calcPromoSets(int orderQuantity, int productStock) {
            int applicableQuantity = Math.min(orderQuantity, productStock);
            return applicableQuantity / (buy + free);
        }
    },
    TwoPlusOne(2, 1) {
        @Override
        public int calcPromoSets(int orderQuantity, int productStock) {
            int applicableQuantity = Math.min(orderQuantity, productStock);
            return applicableQuantity / (buy + free);
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
            return OnePlusOne;
        }
        return TwoPlusOne;
    }

    public int getFree() {
        return free;
    }

    public abstract int calcPromoSets(int orderQuantity, int productStock);
}
