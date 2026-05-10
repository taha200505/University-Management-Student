package ma.university.management.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Validates a Moroccan CIN (Carte d'Identité Nationale).
 * Format: 1-2 uppercase letters followed by 5-7 digits (e.g. AB123456, J123456).
 */
@Documented
@Constraint(validatedBy = MoroccanCINValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface MoroccanCIN {

    String message() default "Le CIN marocain n'est pas valide (ex: AB123456 ou J123456)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
