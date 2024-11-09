package store.validator;

import store.exception.CustomIllegalArgException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DataTypeValidator {
    private static final String STRING_EXP = "^(?!\\s*$)(?!\\s).+";
    private static final String INT_EXP = "^[\\d]+$";
    private static final String Y_OR_N_REG_EXP = "[YN]";
    private static final String INPUT_TYPE_ERROR = "올바르지 않은 형식으로 입력했습니다.";
    private static final String INPUT_ERROR = "잘못된 입력입니다. 다시 입력해 주세요";

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void validateString(final String input) {
        if (!input.matches(STRING_EXP)) {
            throw new CustomIllegalArgException(INPUT_TYPE_ERROR);
        }
    }

    public static void validateInt(final String input) {
        if (!input.matches(INT_EXP)) {
            throw new CustomIllegalArgException(INPUT_TYPE_ERROR);
        }
    }

    public static void validateDate(final String input) {
        try {
            LocalDate.parse(input, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            throw new CustomIllegalArgException(INPUT_TYPE_ERROR);
        }
    }

    public static void validateYOrN(final String input) {
        if (!input.matches(Y_OR_N_REG_EXP)) {
            throw new CustomIllegalArgException(INPUT_ERROR);
        }
    }
}
