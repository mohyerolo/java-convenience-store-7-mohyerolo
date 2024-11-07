package store.validator;

import store.exception.CustomIllegalArgException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DataTypeValidator {
    private static final String FILE_STRING_EXP = "^(?!\\s*$)(?!\\s).+";
    private static final String FILE_INT_EXP = "^[\\d]+$";
    private static final String NOT_STRING_FIELD = "문자가 없는 칸이 존재합니다.";
    private static final String NOT_INT_FIELD = "숫자가 필요한 부분에 숫자가 아닌 문자가 들어가있습니다.";
    private static final String NOT_DATE_FIELD = "yyyy-MM-dd 날짜 형식이 필요한 부분에 다른 데이터가 들어가있습니다.";
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void validateString(final String input) {
        if (!input.matches(FILE_STRING_EXP)) {
            throw new CustomIllegalArgException(NOT_STRING_FIELD);
        }
    }

    public static void validateInt(final String input) {
        if (!input.matches(FILE_INT_EXP)) {
            throw new CustomIllegalArgException(NOT_INT_FIELD);
        }
    }

    public static void validateDate(final String input) {
        try {
            LocalDate.parse(input, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            throw new CustomIllegalArgException(NOT_DATE_FIELD);
        }

    }
}
