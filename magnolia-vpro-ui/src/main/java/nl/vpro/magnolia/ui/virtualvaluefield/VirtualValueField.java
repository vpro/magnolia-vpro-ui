package nl.vpro.magnolia.ui.virtualvaluefield;

import com.vaadin.ui.*;

/**
 * Just wraps a {@link Label} in a field, so that it can be used to show some information in a form.
 */
public class VirtualValueField extends CustomField<String> {

    private final String value;

    public VirtualValueField(String value) {
        this.value = value;
    }


    @Override
    protected Component initContent() {
        Label field = new Label(value);
        field.addStyleNames("vpro-ui", "vpro-ui-virtualfield");
        return field;
    }

    @Override
    public String getValue() {
        return null;
    }

    @Override
    protected void doSetValue(String value) {

    }
}
