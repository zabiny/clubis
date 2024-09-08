package club.klabis.members.domain.forms;

import club.klabis.members.domain.Contact;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {
        AtLeastOneContactIsDefinedConstraint.MemberEditFormValidator.class,
        AtLeastOneContactIsDefinedConstraint.RegistrationFormValidator.class
})
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface AtLeastOneContactIsDefined {

    String message() default "At least one contact or guardian with contact must be provided";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Contact.Type contactType();

    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        AtLeastOneContactIsDefined[] value();
    }
}
