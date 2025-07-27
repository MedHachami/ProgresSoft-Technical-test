package com.progressoft.technicaltest.validation.interfaces;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.progressoft.technicaltest.validation.DifferentCurrenciesValidator;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = DifferentCurrenciesValidator.class)
@Target({ TYPE })  
@Retention(RUNTIME)
public @interface DifferentCurrencies {

    String message() default "From currency and To currency must be different";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}