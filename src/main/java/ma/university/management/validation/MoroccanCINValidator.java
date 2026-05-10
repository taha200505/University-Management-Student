package ma.university.management.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

/**
 * Moroccan CIN validator.
 * Accepted formats: 1-2 uppercase letters + 5-7 digits.
 * Examples: AB123456, J123456, BK654321
 */
public class MoroccanCINValidator implements ConstraintValidator<MoroccanCIN, String> {

    private static final Pattern CIN_PATTERN = Pattern.compile("^[A-Z]{1,2}\\d{5,7}$");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true; // @NotBlank handles null/blank
        }
        return CIN_PATTERN.matcher(value.trim().toUpperCase()).matches();
    }
}
