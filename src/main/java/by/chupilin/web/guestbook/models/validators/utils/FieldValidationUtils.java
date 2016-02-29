package by.chupilin.web.guestbook.models.validators.utils;

import org.springframework.validation.Errors;

public abstract class FieldValidationUtils {

    public static void rejectIfFailLength(Errors errors, String field,
                                          Integer minLength, Integer maxLength, String lengthErrorCode) {
        Object value = errors.getFieldValue(field);
        int length = value.toString().length();
        if (length < minLength || length > maxLength) {
            errors.rejectValue(field, lengthErrorCode);
            errors.rejectValue(field, "rangeForLengthError",
                    rangeToStringBuilder(minLength, maxLength));
        }
    }

    private static String rangeToStringBuilder(Integer startRange, Integer endRange) {
        StringBuilder builder = new StringBuilder("[");
        builder.append(startRange).append("..");
        builder.append(endRange).append("]");
        return builder.toString();
    }

}
