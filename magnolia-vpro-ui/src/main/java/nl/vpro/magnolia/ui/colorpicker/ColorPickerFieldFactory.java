package nl.vpro.magnolia.ui.colorpicker;


import info.magnolia.objectfactory.ComponentProvider;
import info.magnolia.ui.field.factory.AbstractFieldFactory;

/**
 * @author Michiel Meeuwissen
 * @since 1.0
 */
public class ColorPickerFieldFactory extends AbstractFieldFactory<String, ColorPickerFieldDefinition> {

    public ColorPickerFieldFactory(ColorPickerFieldDefinition definition, ComponentProvider componentProvider) {
        super(definition, componentProvider);
    }

    @Override
    protected ColorPickerField createFieldComponent() {
        return new ColorPickerField(definition);
    }
}
