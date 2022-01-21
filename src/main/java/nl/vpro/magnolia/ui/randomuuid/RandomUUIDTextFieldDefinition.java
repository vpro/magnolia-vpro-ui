package nl.vpro.magnolia.ui.randomuuid;

import info.magnolia.ui.field.FieldType;
import info.magnolia.ui.field.TextFieldDefinition;

import java.util.UUID;

import javax.inject.Inject;

@FieldType("randomUUIDField")
public class RandomUUIDTextFieldDefinition extends TextFieldDefinition  {
    @Inject
    public RandomUUIDTextFieldDefinition() {
        setReadOnly(true);
    }

    /**
     * Return random UUID for the Google tag manager by default
     * @return random UUID
     */
    @Override
    public String getDefaultValue() {
        return UUID.randomUUID().toString();
    }

}
