package nl.vpro.magnolia.ui.linkfieldvalidator;

import info.magnolia.ui.field.AbstractFieldValidatorFactory;

import javax.jcr.Node;

import com.vaadin.data.Validator;

public class LinkFieldValidatorFactory extends AbstractFieldValidatorFactory<LinkFieldValidatorDefinition, Node> {

    public LinkFieldValidatorFactory(LinkFieldValidatorDefinition definition) {
        super(definition);
    }

    @Override
    public Validator<Node> createValidator() {
        return new LinkFieldValidator(definition);
    }
}
