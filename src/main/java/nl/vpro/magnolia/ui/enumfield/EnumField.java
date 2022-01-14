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
            listing = new ComboBoxMultiselect<>();
        } else {
            ComboBox<String> comboBox = new ComboBox<>();
            comboBox.setEmptySelectionAllowed(! definition.isRequired());
            listing = comboBox;
        }

        valueContext = new ValueContext(listing);
        Class<? extends Enum<?>> enumClass = definition.getEnum();

        listing.setItems(
            Arrays.stream(enumClass.getEnumConstants())
                .map(e -> definition.convertToPresentation(e, valueContext))
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
            return getValue((String) ((SingleSelect<?>) listing).getValue());
        } else {
            // TODO
            return null;
        }
    }

    protected void setSingleValue(SingleSelect<String> select, String value) {
        if (value != null) {
            try {
                select.setValue(definition.convertToPresentation(value, valueContext));
            } catch (IllegalArgumentException iae) {
                log.warn(iae);
            }
        } else {
            select.setValue(null);
        }
    }

    protected String getValue(String value) {
        return definition.convertToModel(value, valueContext);
    }

}
