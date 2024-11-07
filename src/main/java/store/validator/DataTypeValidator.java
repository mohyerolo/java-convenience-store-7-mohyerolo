package store.validator;

import store.exception.CustomIllegalArgException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DataTypeValidator {
    private static final String STRING_EXP = "^(?!\\s*$)(?!\\s).+";
    private static final String INT_EXP = "^[\\d]+$";
    private static final String INPUT_TYPE_ERROR = "올바르지 않은 형식으로 입력했습니다.";
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
}
