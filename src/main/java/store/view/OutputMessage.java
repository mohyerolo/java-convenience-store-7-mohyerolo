package store.view;

public class OutputMessage {
    public static final String OUTPUT_GREETINGS = "안녕하세요. W편의점입니다.";
    public static final String OUTPUT_CURRENT_PRODUCT_INVENTORY = "현재 보유하고 있는 상품입니다.\n";
    public static final String PRODUCT_TEMPLATE = "- %s %s원 %s";
    public static final String PRODUCT_STOCK_OUT = "재고 없음";
    public static final String NO_PROMO_QUANTITY_CANCEL = "프로모션이 적용되지 않는 %s %s개를 취소했습니다.";

    public static final String RECEIPT_STORE_NAME = "\n==============W 편의점================\n";
    public static final String RECEIPT_ORDER_FIELD_NAME = "상품명\t\t\t\t수량\t\t금액\n";
    public static final String RECEIPT_ORDER_ITEM_FIELD = "%-6s\t\t\t%-2d\t\t%-6s\n";

    public static final String RECEIPT_PROMOTION_PART = "=============증\t정===============\n";
    public static final String RECEIPT_ORDER_PROMOTION_FIELD = "%s\t\t%d\n";
    public static final String RECEIPT_DIVIDER = "====================================\n";
    public static final String TOTAL_AMOUNT = "총구매액\t\t%d\t%s\n";
    public static final String PROMOTION_DISCOUNT = "행사할인\t\t\t-%s\n";
    public static final String MEMBERSHIP_DISCOUNT = "멤버십할인\t\t\t-%s\n";
    public static final String REAL_AMOUNT_TO_PAY = "내실돈\t\t\t %s\n";
}
