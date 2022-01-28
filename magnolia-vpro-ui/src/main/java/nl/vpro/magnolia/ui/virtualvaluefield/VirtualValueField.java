package nl.vpro.magnolia.ui.virtualvaluefield;

import lombok.Getter;
import lombok.Setter;

import com.vaadin.data.HasValue;
import com.vaadin.shared.Registration;
import com.vaadin.ui.Label;

/**
 * Just wraps a {@link Label} in a field, so that it can be used to show some information in a form.
 */
class VirtualValueField extends Label implements HasValue<String> {

    @Getter
    @Setter
    private boolean requiredIndicatorVisible = false;

    @Getter
    @Setter
    private boolean readOnly = true;

    VirtualValueField(String value) {
        super(value);
        addStyleNames("vpro-ui", "vpro-ui-virtualfield");
    }

    @Override
    public void setValue(String value) {

    }

    @Override
    public String getValue() {
        return null;
    }

    @Override
    public Registration addValueChangeListener(ValueChangeListener<String> listener) {
        return () -> {};
    }
}

