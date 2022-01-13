package nl.vpro.magnolia.ui.colorpicker;

import info.magnolia.ui.field.*;
import lombok.Getter;
import lombok.Setter;

import com.vaadin.shared.ui.colorpicker.Color;

/**
 * @author Michiel Meeuwissen
 * @since 1.0
 * */
@Getter
@Setter
@FieldType("cssColorPickerField")
public class ColorPickerFieldDefinition extends ConfiguredFieldDefinition<String>   {

    private boolean pickerAfterText = true;
    private boolean cssInPopup = false;
    private int pickerWidth = 100;
    private int pickerHeight = 100;
    private boolean showText = true;

    public ColorPickerFieldDefinition() {
        setType(String.class);
        setFactoryClass(ColorPickerFieldFactory.class);
        //setFieldBinderClass((Class) TextFieldBinder.class);
    }

    @Override
    public String getDefaultValue() {
        return getDefaultColor().getCSS();
    }

    public Color getDefaultColor() {
        return Color.WHITE;
    }

}

