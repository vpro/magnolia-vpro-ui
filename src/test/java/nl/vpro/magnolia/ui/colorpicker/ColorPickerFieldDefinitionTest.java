package nl.vpro.magnolia.ui.colorpicker;

import com.vaadin.shared.ui.colorpicker.Color;
import info.magnolia.i18nsystem.SimpleTranslator;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;

class ColorPickerFieldDefinitionTest {
    ColorPickerFieldDefinition definition  =  new ColorPickerFieldDefinition(mock(SimpleTranslator.class));


    @Test
    public void parseRgb() {
        Color color = definition.getColorFromCSSValue("rgb(10, 11, 12)").get();
        assertThat(color.getRed()).isEqualTo(10);
        assertThat(color.getGreen()).isEqualTo(11);
        assertThat(color.getBlue()).isEqualTo(12);
        assertThat(color.getCSS()).isEqualTo("#0a0b0c");
    }

    @Test
    public void parseRgba() {
        Color color = definition.getColorFromCSSValue("rgba(10, 11, 12, 0.5)").get();
        assertThat(color.getRed()).isEqualTo(10);
        assertThat(color.getGreen()).isEqualTo(11);
        assertThat(color.getBlue()).isEqualTo(12);
        assertThat(color.getAlpha()).isEqualTo(127);
        assertThat(color.getCSS()).isEqualTo("#0a0b0c");
        assertThat(definition.getStringValue(color)).isEqualTo("rgba(10,11,12,0.5)");
    }

    @Test
    public void parseHash() {
        Color color = definition.getColorFromCSSValue("#fff").get();
        assertThat(color.getCSS()).isEqualTo("#ffffff");
    }

}
