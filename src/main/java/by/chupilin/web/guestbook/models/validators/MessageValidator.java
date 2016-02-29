package by.chupilin.web.guestbook.models.validators;

import by.chupilin.web.guestbook.models.impl.Message;
import by.chupilin.web.guestbook.models.validators.utils.FieldValidationUtils;
import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


public class MessageValidator implements Validator {

    private static final int AUTHOR_MIN_LENGTH_DEFAULT = 3;
    private static final int AUTHOR_MAX_LENGTH_DEFAULT = 20;
    private static final int TEXT_MIN_LENGTH_DEFAULT = 5;
    private static final int TEXT_MAX_LENGTH_DEFAULT = 500;

    private static Integer authorMinLength;
    private static Integer authorMaxLength;
    private static Integer textMinLength;
    private static Integer textMaxLength;

    private static Logger log = Logger.getLogger(MessageValidator.class.getName());

    public void setAuthorMinLength(String authorMinLength) {
        try {
            validateOneParam(authorMinLength);
            this.authorMinLength = Integer.valueOf(authorMinLength);
            if (log.isDebugEnabled()) {
                log.debug("authorMinLength property set value: " + this.authorMinLength);
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            this.authorMinLength = AUTHOR_MIN_LENGTH_DEFAULT;
            log.error("authorMinLength property error. Setting default value: " +
                    AUTHOR_MIN_LENGTH_DEFAULT, e);
        }
    }

    public void setAuthorMaxLength(String authorMaxLength) {
        try {
            validateOneParam(authorMaxLength);
            this.authorMaxLength = Integer.valueOf(authorMaxLength);
            if (log.isDebugEnabled()) {
                log.debug("authorMaxLength property set value: " + this.authorMaxLength);
            }
        } catch (NumberFormatException | NullPointerException e) {
            this.authorMaxLength = AUTHOR_MAX_LENGTH_DEFAULT;
            log.error("authorMaxLength property error. Setting default value: " +
                    AUTHOR_MAX_LENGTH_DEFAULT, e);
        }
    }

    public void setTextMinLength(String textMinLength) {
        try {
            validateOneParam(textMinLength);
            this.textMinLength = Integer.valueOf(textMinLength);
            if (log.isDebugEnabled()) {
                log.debug("textMinLength property set value: " + this.textMinLength);
            }
        } catch (NumberFormatException | NullPointerException e) {
            this.textMinLength = TEXT_MIN_LENGTH_DEFAULT;
            log.error("textMinLength property error. Setting default value: " +
                    TEXT_MIN_LENGTH_DEFAULT, e);
        }
    }

    public void setTextMaxLength(String textMaxLength) {
        try {
            validateOneParam(textMaxLength);
            this.textMaxLength = Integer.valueOf(textMaxLength);
            if (log.isDebugEnabled()) {
                log.debug("textMaxLength property set value: " + this.textMaxLength);
            }
        } catch (NumberFormatException | NullPointerException e) {
            this.textMaxLength = TEXT_MAX_LENGTH_DEFAULT;
            log.error("textMaxLength property error. Setting default value: " +
                    TEXT_MAX_LENGTH_DEFAULT, e);
        }
    }

    public Integer getAuthorMinLength() {
        return authorMinLength;
    }

    public Integer getAuthorMaxLength() {
        return authorMaxLength;
    }

    public Integer getTextMinLength() {
        return textMinLength;
    }

    public Integer getTextMaxLength() {
        return textMaxLength;
    }

    public boolean supports(Class clazz) {
        return Message.class.isAssignableFrom(clazz);
    }

    public void validate(Object object, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "author", "author.empty.error");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "text", "text.empty.error");

        if (!errors.hasFieldErrors("author")) {
            FieldValidationUtils.rejectIfFailLength(errors, "author",
                    authorMinLength, authorMaxLength, "author.length.error");
        }
        if (!errors.hasFieldErrors("text")) {
            FieldValidationUtils.rejectIfFailLength(errors, "text",
                    textMinLength, textMaxLength, "text.length.error");
        }
    }

    private void validateOneParam(String nameParam)
            throws NullPointerException, IllegalArgumentException {
        if (Integer.valueOf(nameParam) == null) {
            throw new NullPointerException();
        }
        if (Integer.valueOf(nameParam).intValue() <= 0) {
            throw new IllegalArgumentException();
        }
    }

    private void validatePairParams(Integer paramMin, Integer paramMax)
            throws IllegalArgumentException {
        if (Integer.valueOf(paramMax).intValue() < Integer.valueOf(paramMin).intValue()) {
            throw new IllegalArgumentException();
        }
    }

    // Initialization method to invoke after setting bean properties
    private void validateAllPairParams() {
        try {
            validatePairParams(authorMinLength, authorMaxLength);
        } catch (IllegalArgumentException e) {
            authorMinLength = AUTHOR_MIN_LENGTH_DEFAULT;
            authorMaxLength = AUTHOR_MAX_LENGTH_DEFAULT;
            log.error("authorMaxLength < authorMinLength. Property error. Setting default values.", e);
        }
        try {
            validatePairParams(textMinLength, textMaxLength);
        } catch (IllegalArgumentException e) {
            textMinLength = TEXT_MIN_LENGTH_DEFAULT;
            textMaxLength = TEXT_MAX_LENGTH_DEFAULT;
            log.error("textMaxLength < textMinLength. Property error. Setting default values.", e);
        }
    }

}