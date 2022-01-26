package nl.vpro.magnolia.ui.colorpicker;

import lombok.extern.log4j.Log4j2;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.data.*;
import com.vaadin.server.Setter;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.shared.ui.colorpicker.Color;
import com.vaadin.ui.*;

/**
 * @author Michiel Meeuwissen
 * @since 3.0
 */
@Log4j2
@StyleSheet({"colorPicker.css"}) // to add a border to the color area, and some other details
public class ColorPickerField extends CustomField<String> {


    private final ColorPickerFieldDefinition definition;

    private final AbstractColorPicker picker;

    public ColorPickerField(ColorPickerFieldDefinition definition) {
        this.definition = definition;
        setCaption(definition.getLabel());
        setRequiredIndicatorVisible(definition.isRequired());
        addStyleName(definition.getStyleName());
        if (definition.showsText()) {
            picker = new ColorPickerArea(this.definition.getDescription());
        } else {
            picker = new ColorPicker(this.definition.getDescription());
        }
    }

    @Override
    protected Component initContent() {
        createPicker();
        if (definition.showsText()) {
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
        textField.setWidth(definition.getFormat().getWidth());
        //textField.setRequiredIndicatorVisible(definition.isRequired());
        Color defaultColor = getDefaultColor();
        textField.setValue(definition.getStringValue(defaultColor));
        textField.setEnabled(true);

        textField.setReadOnly(false);
        textField.addValueChangeListener(
            event -> {
                Color color = definition.getColorFromStringValue(event.getValue()).orElse(null);
                if (color != null) {
                    ColorPickerField.this.setValue(definition.getStringValue(color));
                    picker.setData(color);
                    }
            }
        );
        textField.addBlurListener(
            event -> {
                Color color = definition.getColorFromStringValue(textField.getValue()).orElse(null);
                if (color != null) {
                    ColorPickerField.this.setValue(definition.getStringValue(color));
                    picker.setValue(color);
                    textField.setValue(definition.getStringValue(color));
                }
            }
            );

        Validator<String> beforeConversion = (value, context) -> {
            String s = String.valueOf(value);
            if (! definition.isValid(s)) {
                return ValidationResult.error(
                    definition.getTranslator()
                        .translate("coloricker.not_valid", value));
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
            String c = definition.getStringValue(event.getValue());
            ColorPickerField.this.setValue(c);
            textField.setValue(c);
            }
        );


        final HorizontalLayout layout = new HorizontalLayout();
        layout.addStyleName("colorpicker"); // for css class .v-horizontal-layout-colorpicker (used in style.css)
        //layout.setSizeFull();

        layout.setSpacing(false);
        if (definition.getText() == ColorPickerFieldDefinition.TextLocation.BEFORE) {
            layout.addComponent(textField);
            layout.addComponent(picker);
            textField.addStyleName("right");
            picker.addStyleName("left");
        } else {
            layout.addComponent(picker);
            layout.addComponent(textField);
            textField.addStyleName("left");
            picker.addStyleName("right");
        }
        return layout;

    }

    private Color getColor() {
        return definition.getColorFromStringValue(getValue()).orElse(getDefaultColor());
    }


    @Override
    protected void doSetValue(String value) {
        this.picker.setValue(definition.getColorFromStringValue(value).orElse(this.picker.getValue()));
        Color color = definition.getColorFromStringValue(value).orElse(this.picker.getValue());
        this.picker.setValue(color);
    }

    @Override
    public String getValue() {
        return definition.getStringValue(picker == null ? getDefaultColor() : picker.getValue());
    }

    protected Color getDefaultColor() {
        return definition.getColorFromStringValue((String) definition.getDefaultValue()).orElse(Color.WHITE);

    }

}
