package store.validator;

import store.exception.CustomIllegalArgException;

public class FileValidator {
    private static final String PRODUCT_FILE_FIELD_ERROR = "상품 정보 파일에 name,price,quantity,promotion 양식을 지키지 못한 라인이 있습니다.";
    private static final String PROMOTION_FILE_FIELD_ERROR = "프로모션 정보 파일에 name,buy,get,start_date,end_date 양식을 지키지 못한 라인이 있습니다.";

    private static final int PRODUCT_FILE_FIELD_LENGTH = 4;
    private static final int PROMOTION_FILE_FIELD_LENGTH = 5;

    public static void validateProductField(final String[] productsFileLine) {
        if (productsFileLine.length < PRODUCT_FILE_FIELD_LENGTH) {
            throw new CustomIllegalArgException(PRODUCT_FILE_FIELD_ERROR);
        }
    }

    public static void validatePromotionField(final String[] promotionFileLine) {
        if (promotionFileLine.length < PROMOTION_FILE_FIELD_LENGTH) {
            throw new CustomIllegalArgException(PROMOTION_FILE_FIELD_ERROR);
        }
    }

}
