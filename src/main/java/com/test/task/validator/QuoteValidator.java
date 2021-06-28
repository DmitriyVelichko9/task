package com.test.task.validator;

import com.test.task.model.Quote;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class QuoteValidator implements ConstraintValidator<ValidQuote, Quote> {

    @Override
    public boolean isValid(Quote value, ConstraintValidatorContext context) {
        return (value.getBid() < value.getAsk()) && (value.getIsin().length() == 12);
    }
}
