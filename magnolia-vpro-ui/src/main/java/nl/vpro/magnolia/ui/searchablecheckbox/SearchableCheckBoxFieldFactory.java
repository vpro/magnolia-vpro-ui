package nl.vpro.magnolia.ui.searchablecheckbox;

import info.magnolia.objectfactory.ComponentProvider;
import info.magnolia.ui.field.factory.AbstractFieldFactory;

import org.apache.commons.lang3.StringUtils;

import com.vaadin.shared.Registration;
import com.vaadin.ui.*;

public class SearchableCheckBoxFieldFactory  extends AbstractFieldFactory<String, SearchableCheckBoxFieldDefinition> {

    public SearchableCheckBoxFieldFactory(SearchableCheckBoxFieldDefinition definition, ComponentProvider componentProvider) {
        super(definition, componentProvider);
    }

    @Override
    protected Component createFieldComponent() {
        return new CustomCheckBox(getDefinition().getButtonLabel(), getDefinition().isNegate());
    }

    static class CustomCheckBox extends CustomField<String> {
        private final CheckBox checkBox;
        private final TextField textField;
        private final CssLayout  layout;
        private final boolean negate;

        public CustomCheckBox(String checkBoxCaption, boolean negate) {
            this.checkBox = new CheckBox(checkBoxCaption);
            this.textField = new TextField();
            this.textField.setVisible(false);
            this.layout = new CssLayout();
            this.layout.addComponents(checkBox, textField);
            this.checkBox.addValueChangeListener((ValueChangeListener<Boolean>) event ->
                textField.setValue(booleanToString(event.getValue()))
            );
            this.negate = negate;
        }

        @Override
        protected Component initContent() {
            return layout;
        }

        @Override
        protected void doSetValue(String value) {
            textField.setValue(StringUtils.isEmpty(value) ? "" : value);
            checkBox.setValue(stringToBoolean(value));
        }

        @Override
        public String getValue() {
            return textField.getValue();
        }

        @Override
        public Registration addValueChangeListener(ValueChangeListener<String> listener) {
            return textField.addValueChangeListener(listener);
        }

        protected String booleanToString(Boolean b) {
            if (negate) {
                return b == null || b ? "" : "false";
            } else {
                return b == null || !b ? "" : "true";
            }
        }

        protected boolean stringToBoolean(String b) {
            if (negate) {
                return ! "false".equals(b);
            } else {
                return "true".equals(b);
            }
        }
    }


}
