package nl.vpro.magnolia.ui.validation;

import com.vaadin.data.Validator;
import info.magnolia.ui.field.AbstractFieldValidatorFactory;

public class BeanFieldValidatorFactory extends AbstractFieldValidatorFactory<BeanFieldValidatorDefinition, String> {

    public BeanFieldValidatorFactory(BeanFieldValidatorDefinition definition) {
        super(definition);
    }

    @Override
    public Validator<String> createValidator() {
        return new BeanFieldValidator(getI18nErrorMessage(), definition);
    }
}
