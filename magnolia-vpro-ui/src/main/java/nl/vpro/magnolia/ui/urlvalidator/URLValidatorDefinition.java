package nl.vpro.magnolia.ui.urlvalidator;

import info.magnolia.ui.field.*;

@ValidatorType("urlValidator")
public class URLValidatorDefinition extends ConfiguredFieldValidatorDefinition {


    public URLValidatorDefinition() {
        setFactoryClass(URLFieldValidatorFactory.class);
        setName("urlValidator");
    }
}
