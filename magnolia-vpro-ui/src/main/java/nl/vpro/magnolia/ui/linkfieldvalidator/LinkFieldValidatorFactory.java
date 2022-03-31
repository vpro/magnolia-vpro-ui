package nl.vpro.magnolia.ui.linkfieldvalidator;

import info.magnolia.ui.field.AbstractFieldValidatorFactory;

import com.vaadin.data.Validator;

public class LinkFieldValidatorFactory extends AbstractFieldValidatorFactory<LinkFieldValidatorDefinition, Object> {

    public LinkFieldValidatorFactory(LinkFieldValidatorDefinition definition) {
        super(definition);
    }

    @Override
    public Validator<Object> createValidator() {
        return new LinkFieldValidator(definition);
    }
}
