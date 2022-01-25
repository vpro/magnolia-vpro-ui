package nl.vpro.magnolia.ui.enumfield;

import com.vaadin.data.ValueContext;
import com.vaadin.ui.*;
import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.vaadin.addons.ComboBoxMultiselect;

@Log4j2
public class EnumField<E extends Enum<E>> extends CustomField<String> {

    private final AbstractEnumFieldDefinition<E> definition;
    private AbstractListing<String> listing;
    private ValueContext valueContext;

    public EnumField(AbstractEnumFieldDefinition<E> definition) {
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
            listing = multiselect;
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
            setMultiValue((MultiSelect<String>) listing, value);
        }
    }


    @Override
    public String getValue() {
        if (listing instanceof SingleSelect) {
            return (String) ((SingleSelect<?>) listing).getValue();
        } else {
            Set<?> value = ((MultiSelect<?>) listing).getValue();
            if (value == null) {
                return null;
            }
            return value.stream().map(Object::toString).collect(Collectors.joining(","));
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

    protected void setMultiValue(MultiSelect<String> select, String value) {
        if (value != null) {
            try {
                select.setValue(Arrays.stream(value.split(",")).collect(Collectors.toSet()));
            } catch (IllegalArgumentException iae) {
                log.warn(iae);
            }
        } else {
            select.setValue(Collections.emptySet());
        }
    }

    protected String getCaption(String value) {
        return definition.convertToPresentation(value, valueContext);
    }

}