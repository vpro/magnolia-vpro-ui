package nl.vpro.magnolia.ui.virtualvaluefield;

import com.vaadin.ui.*;

/**
 * Just wraps a {@link Label} in a field, so that it can be used to show some information in a form.
 */
class VirtualValueField extends CustomField<String> {

    private final String value;
    private final String name;

    public VirtualValueField(String value, String name) {
        this.value = value;
        this.name = name;
    }


    @Override
    protected Component initContent() {
        Label field = new Label(value);
        field.addStyleNames("vpro-ui", "vpro-ui-virtualfield", "vpro-ui-virtualfield-" + name);
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
