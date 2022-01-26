package nl.vpro.magnolia.ui.urlvalidator;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.ValueContext;
import com.vaadin.data.validator.AbstractValidator;

import nl.vpro.validation.URI;
import nl.vpro.validation.URIValidator;

/**
 * URL Validator (base on {@link URIValidator}. Not actually using Validation framework. I think it may be feasible to make
 * a validator that simply point to some method in some class.
 */
@URI(mustHaveScheme = true, minHostParts = 2, allowEmptyString = true, lenient = false)
public class URLValidator extends AbstractValidator<String> {

    final URIValidator validator = new URIValidator();

    protected URLValidator(String errorMessage) {
        super(errorMessage);
        validator.initialize(this.getClass().getAnnotation(URI.class));
    }

    @Override
    public ValidationResult apply(String value, ValueContext context) {
        boolean valid = validator.isValid(value, null);
        return toResult(value, valid);
    }
}
