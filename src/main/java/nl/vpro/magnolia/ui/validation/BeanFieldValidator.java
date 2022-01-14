package nl.vpro.magnolia.ui.validation;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.ValueContext;
import com.vaadin.data.validator.AbstractValidator;
import java.util.Set;
import javax.validation.*;
import lombok.SneakyThrows;


/**
 */
public class BeanFieldValidator extends AbstractValidator<String> {

    private static final ValidatorFactory FACTORY = Validation.byDefaultProvider()
        .configure()
        .buildValidatorFactory();

    private static final Validator VALIDATOR = FACTORY.getValidator();

    private BeanFieldValidatorDefinition definition;

    protected BeanFieldValidator(String errorMessage, BeanFieldValidatorDefinition definition) {
        super(errorMessage);
    }

    @SneakyThrows
    @Override
    public ValidationResult apply(String value, ValueContext context) {
        Set<? extends ConstraintViolation<?>> violations = VALIDATOR.validateValue(definition.getBeanClass(), definition.getProperty(), value, definition.getGroups());
        if (violations.isEmpty()) {
            return toResult(value, true);
        } else {
            return  toResult(violations.toString(), false);
        }
    }
}
