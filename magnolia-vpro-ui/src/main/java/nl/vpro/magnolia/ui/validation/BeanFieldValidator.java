package nl.vpro.magnolia.ui.validation;

import lombok.SneakyThrows;

import java.util.Optional;
import java.util.Set;

import javax.validation.*;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.ValueContext;
import com.vaadin.data.validator.AbstractValidator;


/**
 */
public class BeanFieldValidator extends AbstractValidator<String> {

    private static final ValidatorFactory FACTORY = Validation.byDefaultProvider()
        .configure()
        .buildValidatorFactory();

    private static final Validator VALIDATOR = FACTORY.getValidator();

    private final BeanFieldValidatorDefinition definition;

    protected BeanFieldValidator(String errorMessage, BeanFieldValidatorDefinition definition) {
        super(errorMessage);
        this.definition = definition;
    }

    @SneakyThrows
    @Override
    public ValidationResult apply(String value, ValueContext context) {
        Set<? extends ConstraintViolation<?>> violations = VALIDATOR.validateValue(
            definition.getBeanClass(), definition.getProperty(), value, Optional.ofNullable(definition.getGroups()).orElse(new Class<?>[0])
        );
        if (violations.isEmpty()) {
            return toResult(value, true);
        } else {
            return  toResult(violations.toString(), false);
        }
    }
}
