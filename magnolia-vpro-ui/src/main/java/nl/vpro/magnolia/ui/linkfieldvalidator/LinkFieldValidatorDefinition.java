package nl.vpro.magnolia.ui.linkfieldvalidator;

import info.magnolia.ui.field.ConfiguredFieldValidatorDefinition;
import info.magnolia.ui.field.ValidatorType;
import lombok.Getter;
import lombok.Setter;

@ValidatorType("linkFieldValidator")
public class LinkFieldValidatorDefinition extends ConfiguredFieldValidatorDefinition {
    @Getter
    @Setter
    private String repository;

    public LinkFieldValidatorDefinition() {
        setFactoryClass(LinkFieldValidatorFactory.class);
    }
}
