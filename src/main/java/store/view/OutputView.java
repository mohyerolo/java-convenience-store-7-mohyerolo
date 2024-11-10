package store.view;

import store.dto.OrderItemDto;
import store.dto.ProductDto;
import store.dto.ProductStorageDto;
import store.dto.ReceiptDto;

import java.text.NumberFormat;

import static store.view.OutputMessage.*;

public class OutputView {
    private static final NumberFormat numberFormat = NumberFormat.getNumberInstance();

    public void printGreetings() {
        System.out.println(OUTPUT_GREETINGS);
    }

    public void printCurrentInventory() {
        System.out.println(OUTPUT_CURRENT_PRODUCT_INVENTORY);
    }

    public void printProductStorage(ProductStorageDto storageDto) {
        StringBuilder sb = new StringBuilder();
        storageDto.getProducts().entrySet().stream()
                .flatMap(entry -> entry.getValue().stream())
                .map(this::makeProductStatusSentence)
                .forEach(sentence -> sb.append(sentence).append('\n'));
        System.out.println(sb);
    }

    public void printCancelNoPromoQuantity(String productName, int cancelQuantity) {
        System.out.printf((NO_PROMO_QUANTITY_CANCEL) + "%n", productName, numberFormat.format(cancelQuantity));
    }

    public void printReceipt(ReceiptDto receiptDto, boolean membership) {
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

    private String makeProductStatusSentence(ProductDto product) {
        String defaultSentence = makeDefaultProductSentence(product);
        return appendPromotion(defaultSentence, product);
    }

    private String appendPromotion(String sentence, ProductDto product) {
        if (product.getPromotion() == null) {
            return sentence;
        }
        return sentence + " " + product.getPromotion();
    }

    private String makeDefaultProductSentence(ProductDto product) {
        String quantity = numberFormat.format(product.getQuantity()) + "개";
        if (product.getQuantity() == 0) {
            quantity = PRODUCT_STOCK_OUT;
        }
        return String.format(PRODUCT_TEMPLATE, product.getName(), numberFormat.format(product.getPrice()), quantity);
    }

    private void appendOrderHistory(ReceiptDto receipt, StringBuilder sb) {
        sb.append(RECEIPT_STORE_NAME).append(RECEIPT_ORDER_FIELD_NAME);
        for (OrderItemDto orderItemDto : receipt.getOrderItemDtos()) {
            sb.append(makeOrderItemSentence(orderItemDto));
        }
    }

    private String makeOrderItemSentence(OrderItemDto orderItemDto) {
        return String.format(RECEIPT_ORDER_ITEM_FIELD, orderItemDto.getProductName(),
                orderItemDto.getQuantity(), numberFormat.format(orderItemDto.getTotalOrderItemAmount()));
    }

    private void appendPromotionHistory(ReceiptDto receipt, StringBuilder sb) {
        sb.append(RECEIPT_PROMOTION_PART);
        for (OrderItemDto orderItemDto : receipt.getOrderItemDtos()) {
            int freeQuantity = orderItemDto.getFreeQuantity();
            if (freeQuantity != 0) {
                sb.append(makePromotionOrderItemSentence(orderItemDto.getProductName(), freeQuantity));
            }
        }
    }

    private String makePromotionOrderItemSentence(String productName, int freeQuantity) {
        return String.format(RECEIPT_ORDER_PROMOTION_FIELD, productName, freeQuantity);
    }

    private void appendAmountCalculateHistory(ReceiptDto receipt, boolean membership, StringBuilder sb) {
        int totalOrderAmount = appendToTalAmount(receipt, sb);
        int promotionDiscountAmount = appendPromotionDiscountAmount(receipt, sb);
        int membershipDiscountAmount = appendMembershipDiscountAmount(receipt, membership, sb);

        int realAmount = totalOrderAmount - promotionDiscountAmount - membershipDiscountAmount;
        sb.append(String.format(REAL_AMOUNT_TO_PAY, numberFormat.format(realAmount)));
    }

    private int appendToTalAmount(ReceiptDto receipt, StringBuilder sb) {
        int totalOrderQuantity = receipt.calcOrderTotalQuantity();
        int totalOrderAmount = receipt.calcOrderTotalAmount();
        sb.append(String.format(TOTAL_AMOUNT, totalOrderQuantity, numberFormat.format(totalOrderAmount)));
        return totalOrderAmount;
    }

    private int appendPromotionDiscountAmount(ReceiptDto receiptDto, StringBuilder sb) {
        int promotionDiscountAmount = receiptDto.calcPromotionDiscountAmount();
        sb.append(String.format(PROMOTION_DISCOUNT, numberFormat.format(promotionDiscountAmount)));
        return promotionDiscountAmount;
    }

    private int appendMembershipDiscountAmount(ReceiptDto receiptDto, boolean membership, StringBuilder sb) {
        int membershipDiscountAmount = receiptDto.calcMembershipAmount(membership);
        sb.append(String.format(MEMBERSHIP_DISCOUNT, numberFormat.format(membershipDiscountAmount)));
        return membershipDiscountAmount;
    }
}
