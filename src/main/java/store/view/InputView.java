package store.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {
    private static final String INPUT_BUY_PRODUCT_AND_QUANTITY = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    private static final String INPUT_FREE_AVAILABLE = "\n현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)\n";
    private static final String INPUT_OUT_OF_STOCK_NO_PROMO = "\n현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)\n";
    private static final String INPUT_CANCEL_NO_PROMO_PRODUCT_QUANTITY = "\n%s에 프로모션이 적용되지 않은 %d개 만큼의 주문은 취소하시겠습니까? N 선택 시 해당 상품 전체 취소됩니다. (Y/N)\n";
    private static final String INPUT_MEMBERSHIP = "\n멤버십 할인을 받으시겠습니까? (Y/N)";
    private static final String INPUT_BUY_OTHER = "감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)";

    public String readBuyProductAndQuantity() {
        System.out.println(INPUT_BUY_PRODUCT_AND_QUANTITY);
        return Console.readLine();
    }

    public String readAvailablePromotionProductAdd(String productName, int free) {
        System.out.printf(INPUT_FREE_AVAILABLE, productName, free);
        return Console.readLine();
    }

    public String readNoPromoFine(String productName, int noPromoQuantity) {
        System.out.printf(INPUT_OUT_OF_STOCK_NO_PROMO, productName, noPromoQuantity);
        return Console.readLine();
    }

    public String readCancelNoPromoQuantity(String productName, int noPromoQuantity) {
        System.out.printf(INPUT_CANCEL_NO_PROMO_PRODUCT_QUANTITY, productName, noPromoQuantity);
        return Console.readLine();
    }

    public String readMembership() {
        System.out.println(INPUT_MEMBERSHIP);
        return Console.readLine();
    }

    public String readBuyOtherProduct() {
        System.out.println(INPUT_BUY_OTHER);
        return Console.readLine();
    }
}
