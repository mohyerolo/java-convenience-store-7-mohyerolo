package store.constant;

public enum PromotionType {
    OnePlusOne(1, 1),
    TwoPlusOne(2, 1);

    private final int buy;
    private final int free;

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
}
