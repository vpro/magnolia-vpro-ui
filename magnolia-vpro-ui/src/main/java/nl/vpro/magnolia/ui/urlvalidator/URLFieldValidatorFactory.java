package nl.vpro.magnolia.ui.urlvalidator;

import info.magnolia.ui.field.AbstractFieldValidatorFactory;

import com.vaadin.data.Validator;

public class URLFieldValidatorFactory extends AbstractFieldValidatorFactory<URLValidatorDefinition, String> {

    public URLFieldValidatorFactory(URLValidatorDefinition definition) {
        super(definition);
    }

    @Override
    public Validator<String> createValidator() {
        return new URLValidator(getI18nErrorMessage());
    }
}
