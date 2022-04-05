package nl.vpro.magnolia.ui.enumfield;

import lombok.extern.log4j.Log4j2;

import java.util.*;
import java.util.stream.Collectors;

import org.vaadin.addons.ComboBoxMultiselect;

import com.vaadin.data.ValueContext;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.*;

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
        IconGenerator<String> iconGenerator = item -> {
            if (definition.isUseIcons()) {
                String icon = definition.getConverter().getIcon(item, valueContext);
                if (icon != null) {
                    return new ExternalResource(icon);
                }
            }
            return null;
        };
        boolean isMultiselect = definition.getMultiselect() == Boolean.TRUE || definition.isTwinselect();
        if (isMultiselect) {
            if (definition.isTwinselect()) {
                if (definition.getUseIcons() == Boolean.TRUE) {
                    throw new IllegalStateException();
                }
                TwinColSelect<String> twinColSelect = new TwinColSelect<>();
                twinColSelect.setItemCaptionGenerator((ItemCaptionGenerator<String>) EnumField.this::getCaption);
                twinColSelect.setRows((int) definition.getItems().limit(10).count());
                listing = twinColSelect;
            } else {
                ComboBoxMultiselect<String> multiselect = new ComboBoxMultiselect<>();
                multiselect.setItemCaptionGenerator((ItemCaptionGenerator<String>) EnumField.this::getCaption);
                multiselect.setItemIconGenerator(iconGenerator);
                listing = multiselect;
            }
        } else {
            if (definition.isTwinselect()) {
                throw new IllegalStateException();
            }
            ComboBox<String> comboBox = new ComboBox<>();
            comboBox.setEmptySelectionAllowed(! definition.isRequired());
            comboBox.setItemCaptionGenerator((ItemCaptionGenerator<String>) EnumField.this::getCaption);
            comboBox.setItemIconGenerator(iconGenerator);
            comboBox.setRequiredIndicatorVisible(definition.isRequired());
            listing = comboBox;
        }
        listing.setWidthFull();

        valueContext = new ValueContext(listing);
        Class<E> enumClass = definition.getEnum();
        listing.setItems(definition.getItems().map(Enum::name));

        listing.addStyleNames("vpro-ui", "vpro-ui-enumfield", "vpro-ui-enumfield-" + enumClass.getSimpleName().toLowerCase());
    }

    @Override
    public String getEmptyValue() {
        return definition.getEmptyValue();
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
