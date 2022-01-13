package nl.vpro.magnolia.ui.colorpicker;

import lombok.extern.log4j.Log4j2;

import java.util.Optional;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.data.*;
import com.vaadin.server.Setter;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.shared.ui.colorpicker.Color;
import com.vaadin.ui.*;

/**
 * @author Michiel Meeuwissen
 * @since 1.0
 */
@Log4j2
@StyleSheet({"style.css"}) // to add a border to the color area
public class ColorPickerField extends CustomField<String> {

    private static final Pattern COLOR = Pattern.compile("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$");

    private final ColorPickerFieldDefinition definition;

    private final AbstractColorPicker picker;

    public ColorPickerField(ColorPickerFieldDefinition definition) {
        this.definition = definition;
        setCaption(definition.getLabel());
        setRequiredIndicatorVisible(definition.isRequired());
        addStyleName(definition.getStyleName());
        if (definition.isShowText()) {
            picker = new ColorPickerArea(this.definition.getDescription());
        } else {
            picker = new ColorPicker(this.definition.getDescription());
        }
    }

    @Override
    protected Component initContent() {
        createPicker();
        if (definition.isShowText()) {
            return createLayoutWithText();
        } else {
            return picker;
        }
    }

    void createPicker() {
        picker.setModal(true);
        //picker.setCaption(definition.getLabel());
        picker.setLocale(getLocale());
        picker.setWidth(definition.getPickerWidth(), Unit.PIXELS);
        picker.setHeight(definition.getPickerHeight(), Unit.PIXELS);
        picker.setTextfieldVisibility(definition.isCssInPopup());
        picker.setDefaultCaptionEnabled(true);
        picker.setHistoryVisibility(true);
        picker.setSwatchesVisibility(true);
    }

    HorizontalLayout createLayoutWithText() {
        final TextField textField = new TextField();
        textField.setValueChangeMode(ValueChangeMode.BLUR);
        textField.setWidth(7, Unit.EM);
        textField.setRequiredIndicatorVisible(definition.isRequired());
        textField.setValue(getValue());
        textField.setEnabled(true);

        textField.setReadOnly(false);
        textField.addValueChangeListener(
            event -> {
                Color color = getColor(event.getValue()).orElse(null);
                if (color != null) {
                    ColorPickerField.this.setValue(color.getCSS());
                    picker.setData(color);
                    }
            }
        );
        textField.addBlurListener(
            event -> {
                Color color = getColor(textField.getValue()).orElse(null);
                if (color != null) {
                    ColorPickerField.this.setValue(color.getCSS());
                    picker.setValue(color);
                    textField.setValue(color.getCSS());
                }
            }
            );

        Validator<String> beforeConversion = (value, context) -> {
            if (!COLOR.matcher(String.valueOf(value)).matches()) {
                return ValidationResult.error(String.format("Not a valid color value %s", value));
            } else {
                return ValidationResult.ok();
            }
        };
        new Binder<>(String.class)
            .forField(textField)
            .withValidator(beforeConversion)
            // binders seem not to be used at all, but without them no validation happens
            .bind(new ValueProvider<String, String>() {
                @Override
                public String apply(String s) {
                    return null;
                }
            }, new Setter<String, String>() {
                @Override
                public void accept(String s, String s2) {

                }
            })
        ;

        picker.addValueChangeListener(event -> {
            String c = event.getValue().getCSS();
            ColorPickerField.this.setValue(c);
            textField.setValue(c);
            }
        );


        final HorizontalLayout layout = new HorizontalLayout();
        //layout.setSizeFull();

        layout.setSpacing(false);
        if (definition.isPickerAfterText()) {
            layout.addComponent(textField);
            layout.addComponent(picker);
        } else {
            layout.addComponent(picker);
            layout.addComponent(textField);
        }
        return layout;

    }

    private Color getColor() {
        return getColor(getValue()).orElse(definition.getDefaultColor());
    }

    private static  Optional<Color> getColor(String color) {
        if (StringUtils.isNotBlank(color)) {
            try {
                int red;
                int green;
                int blue;
                if (color.length() == 7) {
                    red = Integer.parseInt(color.substring(1, 3), 16);
                    green = Integer.parseInt(color.substring(3, 5), 16);
                    blue = Integer.parseInt(color.substring(5, 7), 16);
                } else if (color.length() == 4 ) {
                    red = Integer.parseInt(color.substring(1, 2), 16) * 16 + Integer.parseInt(color.substring(1, 2), 16);
                    green = Integer.parseInt(color.substring(2, 3), 16) * 16 + Integer.parseInt(color.substring(2, 3), 16);
                    blue = Integer.parseInt(color.substring(3, 4), 16) * 16 + Integer.parseInt(color.substring(3, 4), 16);
                } else {
                    log.debug("Cannot parse {}", color);
                    return Optional.empty();
                }
                return Optional.of(new Color(red, green, blue));
            } catch (StringIndexOutOfBoundsException | IllegalArgumentException nfe) {
                log.debug("Couldn't parse {}", color);

            }
        }
        return Optional.empty();
    }


    @Override
    protected void doSetValue(String value) {
        this.picker.setValue(getColor(value).orElse(this.picker.getValue()));
    }

    @Override
    public String getValue() {
        return (picker == null ? definition.getDefaultColor() : picker.getValue()).getCSS();
    }

}
