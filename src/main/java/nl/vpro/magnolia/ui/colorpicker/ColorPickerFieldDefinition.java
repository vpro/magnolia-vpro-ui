package nl.vpro.magnolia.ui.colorpicker;

import com.vaadin.shared.ui.colorpicker.Color;
import info.magnolia.i18nsystem.SimpleTranslator;
import info.magnolia.ui.field.ConfiguredFieldDefinition;
import info.magnolia.ui.field.FieldType;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import static nl.vpro.magnolia.ui.colorpicker.ColorPickerFieldDefinition.TextLocation.AFTER;
import static nl.vpro.magnolia.ui.colorpicker.ColorPickerFieldDefinition.TextLocation.NOT;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Michiel Meeuwissen
 * @since 1.0
 * */
@Getter
@Setter
@FieldType("cssColorPickerField")
@Log4j2
public class ColorPickerFieldDefinition extends ConfiguredFieldDefinition<String>   {

    private static final Pattern COLOR = Pattern.compile("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$");

    private static final Pattern RGB = Pattern.compile("rgb\\(\\s*(\\d+)\\s*,\\s*(\\d+)\\s*,\\s*(\\d+)\\s*\\)");
    private static final Pattern RGBA = Pattern.compile("rgba\\(\\s*(\\d+)\\s*,\\s*(\\d+)\\s*,\\s*\\s*(\\d+)\\s*,\\s*([01]*(?:\\.\\d+)?)\\s*\\)");
    private static final Pattern HSL= Pattern.compile("hsl\\(\\s*(\\d+)\\s*%\\s*,\\s*(\\d+)\\s*%\\s*,\\s*(\\d+)\\s*%\\s*\\)");
    private static final Pattern HSLA= Pattern.compile("hsl\\(\\s*(\\d+)\\s*%\\s*,\\s*(\\d+)\\s*%\\s*,\\s*(\\d+)\\s*%\\s*,\\s*([01]*(?:\\.\\d+)?)\\s*\\)");

    private boolean cssInPopup = false;
    private int pickerWidth = 100;
    private int pickerHeight = 100;
    private TextLocation text = AFTER;
    private ValueFormat format = ValueFormat.HASH;
    private final SimpleTranslator translator;


    @Inject
    public ColorPickerFieldDefinition(SimpleTranslator translator) {
        setType(String.class);
        setFactoryClass(ColorPickerFieldFactory.class);
        setDefaultValue(Color.WHITE);
        setI18n(true);
        this.translator = translator;

    }


    boolean showsText() {
        return getText() != NOT;
    }


    protected String getStringValue(Color color) {
        if (color.getAlpha() != 255) {
            return getRGBA(color);
        }
        switch(getFormat()) {
            case HASH:
                return color.getCSS();
            case RGB:
                return "rgb(" + color.getRed() + "," + color.getGreen() + "," + color.getBlue() + ")";
            case RGBA:
                return getRGBA(color);
            default:
                return color.getCSS();
        }
    }

     protected String getRGBA(Color color){
        return String.format(Locale.US, "rgba(%d,%d,%d,%.1f)", color.getRed(), color.getGreen(), color.getBlue(), (float) color.getAlpha() / 255);
    }


    Optional<Color> getColorFromCSSValue(String color) {
        if (StringUtils.isNotBlank(color)) {
            color = color.trim();
            try {
                if (color.startsWith("#")) {

                    int red;
                    int green;
                    int blue;
                    if (color.length() == 7) {
                        red = parseInt(color.substring(1, 3), 16);
                        green = parseInt(color.substring(3, 5), 16);
                        blue = parseInt(color.substring(5, 7), 16);
                    } else if (color.length() == 4) {
                        red = parseInt(color.substring(1, 2), 16) * 16 + parseInt(color.substring(1, 2), 16);
                        green = parseInt(color.substring(2, 3), 16) * 16 + parseInt(color.substring(2, 3), 16);
                        blue = parseInt(color.substring(3, 4), 16) * 16 + parseInt(color.substring(3, 4), 16);
                    } else {
                        log.debug("Cannot parse {}", color);
                        return Optional.empty();
                    }
                    return Optional.of(new Color(red, green, blue));

                } else {
                    Matcher matcher = RGB.matcher(color);
                    if (matcher.matches()) {
                        return Optional.of(new Color(
                            parseInt(matcher.group(1)),
                            parseInt(matcher.group(2)),
                            parseInt(matcher.group(3))
                        ));
                    }
                    matcher = RGBA.matcher(color);
                    if (matcher.matches()) {
                        return Optional.of(new Color(
                            parseInt(matcher.group(1)),
                            parseInt(matcher.group(2)),
                            parseInt(matcher.group(3)),
                            (int) (255 * parseFloat(matcher.group(4)))
                        ));
                    }
                    matcher = HSL.matcher(color);
                    if (matcher.matches()) {
                        return Optional.of(new Color(
                            parseInt(matcher.group(1)) * 255 / 100,
                            parseInt(matcher.group(2)) * 255 / 100,
                            parseInt(matcher.group(3)) * 255 / 100
                            )
                        );
                    }
                    matcher = HSLA.matcher(color);
                    if (matcher.matches()) {
                        return Optional.of(new Color(
                            parseInt(matcher.group(1)) * 255 / 100,
                            parseInt(matcher.group(2)) * 255 / 100,
                            parseInt(matcher.group(3)) * 255 / 100,
                            (int) (255 * parseFloat(matcher.group(4)))
                        ));
                    }
                }
            } catch (StringIndexOutOfBoundsException | IllegalArgumentException nfe) {
                log.debug("Couldn't parse {}", color);

            }
        }
        return Optional.empty();
    }

    boolean isValid(String s) {
        return COLOR.matcher(s).matches() || RGB.matcher(s).matches() ||  RGBA.matcher(s).matches();
    }


    public enum TextLocation {
        AFTER,
        BEFORE,
        NOT
    }
    public enum ValueFormat {
        HASH("#ffffff".length() + "em"),
        RGB("rgb(255,255,255)".length() + "em"),
        RGBA("rgba(255,255,255,1.0".length() + "em"),
        HSL("hsl(100%,100%,100%)".length() + "em"),
        HSLA("hsla(100%,100%,100%,1.0".length() + "em");

        @Getter
        final String width;

        ValueFormat(String width) {
            this.width = width;
        }
    }

}

