package nl.vpro.magnolia.ui.integerrangefield;

import info.magnolia.objectfactory.ComponentProvider;
import info.magnolia.ui.field.ConfiguredFieldDefinition;
import info.magnolia.ui.field.FieldType;
import info.magnolia.ui.field.factory.AbstractFieldFactory;
import lombok.Getter;
import lombok.Setter;

import java.util.stream.IntStream;

import com.vaadin.ui.*;

/**
 * A simple custom field that just shows a combobox with a range of integers
 * @author Michiel Meeuwissen
 * @since 1.0
 * */
@Getter
@Setter
@FieldType("integerRangeField")
public class IntegerRangeSelectFieldDefinition extends ConfiguredFieldDefinition<Integer> {

    private int min = 0;
    private int max = 10;

    public IntegerRangeSelectFieldDefinition() {
        setFactoryClass(Factory.class);
        setType(Integer.class);
    }

    public static class Factory extends AbstractFieldFactory<Integer, IntegerRangeSelectFieldDefinition> {

        public Factory(IntegerRangeSelectFieldDefinition definition, ComponentProvider componentProvider) {
            super(definition, componentProvider);
        }

        @Override
        protected Component createFieldComponent() {
            return new CustomField<Integer>() {

                private final ComboBox<Integer> comboBox = new ComboBox<>();
                @Override
                public Integer getValue() {
                    return comboBox.getValue();
                }

                @Override
                protected void doSetValue(Integer value) {
                    comboBox.setValue(value);
                }

                @Override
                protected Component initContent() {
                    comboBox.setItems(IntStream.range(definition.getMin(), definition.getMax() + 1).boxed().toArray(Integer[]::new));
                    comboBox.addStyleNames("vpro-ui", "vpro-ui-integerrange");
                    return comboBox;
                }
            };
        }

    }

}
