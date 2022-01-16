package nl.vpro.magnolia.ui.enumfield;

import com.vaadin.data.ValueContext;
import com.vaadin.ui.*;
import java.util.Arrays;
import lombok.extern.log4j.Log4j2;
import org.vaadin.addons.ComboBoxMultiselect;

@Log4j2
public class EnumField extends CustomField<String> {

    private final EnumFieldDefinition definition;
    private AbstractListing<String> listing;
    private ValueContext valueContext;

    public EnumField(EnumFieldDefinition definition) {
        this.definition = definition;
        createComponent();
    }

    @Override
    protected Component initContent() {
        return listing;
    }

    protected void createComponent() {
        if (definition.isMultiselect()) {
            ComboBoxMultiselect<String> multiselect = new ComboBoxMultiselect<>();
            multiselect.setItemCaptionGenerator((ItemCaptionGenerator<String>) EnumField.this::getCaption);
            listing = new ComboBoxMultiselect<>();
        } else {
            ComboBox<String> comboBox = new ComboBox<>();
            comboBox.setEmptySelectionAllowed(! definition.isRequired());
            comboBox.setItemCaptionGenerator((ItemCaptionGenerator<String>) EnumField.this::getCaption);
            listing = comboBox;
        }


        valueContext = new ValueContext(listing);
        Class<? extends Enum<?>> enumClass = definition.getEnum();

        listing.setItems(
            Arrays.stream(enumClass.getEnumConstants()).map(Enum::name)
        );
    }

    @Override
    protected void doSetValue(String value) {
        if (listing instanceof SingleSelect) {
            setSingleValue((SingleSelect<String>) listing, value);
        } else {
            // TODO

        }
    }


    @Override
    public String getValue() {
        if (listing instanceof SingleSelect) {
            return (String) ((SingleSelect<?>) listing).getValue();
        } else {
            // TODO
            return null;
        }
    }

    protected void setSingleValue(SingleSelect<String> select, String value) {
        if (value != null) {
            try {
                select.setValue(value);
            } catch (IllegalArgumentException iae) {
                log.warn(iae);
            }
        } else {
            select.setValue(null);
        }
    }

    protected String getCaption(String value) {
        return definition.convertToPresentation(value, valueContext);
    }

}
