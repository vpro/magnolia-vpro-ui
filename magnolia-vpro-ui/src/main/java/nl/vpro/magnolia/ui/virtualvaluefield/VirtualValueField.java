package nl.vpro.magnolia.ui.virtualvaluefield;

import org.vaadin.viritin.fields.LabelField;

import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;

/**
 * Just to unbind all values.
 */
public class VirtualValueField extends CustomField<String> {

    private final String value;

    public VirtualValueField(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return null;
    }

    @Override
    protected Component initContent() {
        LabelField<String> field = new LabelField<>();
        field.setReadOnly(true);
        field.setValue(value);
        return field;
    }

    @Override
    protected void doSetValue(String value) {

    }
}
