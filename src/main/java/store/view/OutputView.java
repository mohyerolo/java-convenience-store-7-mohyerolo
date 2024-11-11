package store.view;

import store.dto.OrderItemDto;
import store.dto.ProductDto;
import store.dto.ReceiptDto;

import java.text.NumberFormat;
import java.util.List;

import static store.view.OutputMessage.*;

public class OutputView {
    private static final NumberFormat numberFormat = NumberFormat.getNumberInstance();
    private static final int STOCK_OUT = 0;
    private static final String UNIT = "개";
    private static final String MINUS = "-";

    public void printGreetings() {
        System.out.println(OUTPUT_GREETINGS);
    }

    public void printCurrentInventory() {
        System.out.println(OUTPUT_CURRENT_PRODUCT_INVENTORY);
    }

    public void printProductStorage(final List<ProductDto> productDtos) {
        StringBuilder sb = new StringBuilder();
        for (ProductDto productDto : productDtos) {
            sb.append(makeProductStatusSentence(productDto)).append('\n');
        }
        System.out.println(sb);
    }

    public void printCancelNoPromoQuantity(final String productName, final int cancelQuantity) {
        System.out.printf((NO_PROMO_QUANTITY_CANCEL) + "%n", productName, numberFormat.format(cancelQuantity));
    }

    public void printReceipt(final ReceiptDto receiptDto, final boolean membership) {
        StringBuilder sb = new StringBuilder();
        appendOrderHistory(receiptDto, sb);
        if (receiptDto.isPromotionExists()) {
            appendPromotionHistory(receiptDto, sb);
        }
        sb.append(RECEIPT_DIVIDER);
        appendAmountCalculateHistory(receiptDto, membership, sb);
        System.out.println(sb);
    }

    public void printNewLine() {
        System.out.println();
    }

    private String makeProductStatusSentence(final ProductDto product) {
        String defaultSentence = makeDefaultProductSentence(product);
        return appendPromotion(defaultSentence, product);
    }

    private String makeDefaultProductSentence(final ProductDto product) {
        String quantity = numberFormat.format(product.getStock()) + UNIT;
        if (product.getStock() == STOCK_OUT) {
            quantity = PRODUCT_STOCK_OUT;
        }
        return String.format(PRODUCT_TEMPLATE, product.getName(), numberFormat.format(product.getPrice()), quantity);
    }

    private String appendPromotion(final String sentence, final ProductDto product) {
        if (product.getPromotion() == null) {
            return sentence;
        }
        return sentence + " " + product.getPromotion();
    }

    private void appendOrderHistory(final ReceiptDto receipt, final StringBuilder sb) {
        sb.append(RECEIPT_STORE_NAME).append(RECEIPT_ORDER_FIELD_NAME);
        for (OrderItemDto orderItemDto : receipt.getOrderItemDtos()) {
            sb.append(makeOrderItemSentence(orderItemDto));
        }
    }

    private String makeOrderItemSentence(final OrderItemDto orderItemDto) {
        return String.format(RECEIPT_ORDER_ITEM_FIELD, orderItemDto.getProductName(),
                orderItemDto.getQuantity(), numberFormat.format(orderItemDto.getTotalOrderItemAmount()));
    }

    private void appendPromotionHistory(final ReceiptDto receipt, final StringBuilder sb) {
        sb.append(RECEIPT_PROMOTION_PART);
        for (OrderItemDto orderItemDto : receipt.getOrderItemDtos()) {
            int freeQuantity = orderItemDto.getFreeQuantity();
            if (freeQuantity != 0) {
                sb.append(makePromotionOrderItemSentence(orderItemDto.getProductName(), freeQuantity));
            }
        }
    }

    private String makePromotionOrderItemSentence(final String productName, final int freeQuantity) {
        return String.format(RECEIPT_ORDER_PROMOTION_FIELD, productName, freeQuantity);
    }

    private void appendAmountCalculateHistory(final ReceiptDto receipt, final boolean membership, final StringBuilder sb) {
        int totalOrderAmount = appendToTalAmount(receipt, sb);
        int promotionDiscountAmount = appendPromotionDiscountAmount(receipt, sb);
        int membershipDiscountAmount = appendMembershipDiscountAmount(receipt, membership, sb);

        int realAmount = totalOrderAmount - promotionDiscountAmount - membershipDiscountAmount;
        sb.append(String.format(REAL_AMOUNT_TO_PAY, numberFormat.format(realAmount)));
    }

    private int appendToTalAmount(final ReceiptDto receipt, final StringBuilder sb) {
        int totalOrderQuantity = receipt.calcOrderTotalQuantity();
        int totalOrderAmount = receipt.calcOrderTotalAmount();
        sb.append(String.format(TOTAL_AMOUNT, totalOrderQuantity, numberFormat.format(totalOrderAmount)));
        return totalOrderAmount;
    }

    private int appendPromotionDiscountAmount(final ReceiptDto receiptDto, final StringBuilder sb) {
        int promotionDiscountAmount = receiptDto.calcPromotionDiscountAmount();
        sb.append(String.format(PROMOTION_DISCOUNT, MINUS + numberFormat.format(promotionDiscountAmount)));
        return promotionDiscountAmount;
    }

    private int appendMembershipDiscountAmount(final ReceiptDto receiptDto, final boolean membership, final StringBuilder sb) {
        int membershipDiscountAmount = receiptDto.calcMembershipAmount(membership);
        sb.append(String.format(MEMBERSHIP_DISCOUNT, MINUS + numberFormat.format(membershipDiscountAmount)));
        return membershipDiscountAmount;
    }
}
