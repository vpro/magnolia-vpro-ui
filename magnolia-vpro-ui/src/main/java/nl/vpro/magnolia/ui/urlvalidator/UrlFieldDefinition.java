package nl.vpro.magnolia.ui.urlvalidator;

import info.magnolia.ui.field.FieldType;
import info.magnolia.ui.field.TextFieldDefinition;

import java.util.Arrays;

/**
 * Just a text field with {@link URLValidatorDefinition} preset.
 */
@FieldType("urlField")
public class UrlFieldDefinition extends TextFieldDefinition {


    public UrlFieldDefinition() {
        super();
        setValidators(Arrays.asList(new URLValidatorDefinition()));
    }
}
