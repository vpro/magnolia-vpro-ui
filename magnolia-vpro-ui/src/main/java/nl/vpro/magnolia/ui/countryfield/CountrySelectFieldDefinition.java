package nl.vpro.magnolia.ui.countryfield;


import com.neovisionaries.i18n.CountryCode;
import com.vaadin.data.ValueContext;
import info.magnolia.ui.field.FieldType;
import lombok.Getter;
import lombok.Setter;
import nl.vpro.i18n.Locales;
import nl.vpro.magnolia.ui.enumfield.AbstractEnumFieldDefinition;
import org.meeuw.i18n.countries.Country;
import org.meeuw.i18n.regions.Region;

/**
 * @author Michiel Meeuwissen
 * @since 3.0
 */
@FieldType("countryField")
public class CountrySelectFieldDefinition extends AbstractEnumFieldDefinition<CountryCode> {

    @Setter
    @Getter
    Class<? extends Region> clazz = Country.class;

    public CountrySelectFieldDefinition() {
        super(CountryCode.class);
    }

    @Override
    protected String convertToPresentation(CountryCode value, ValueContext context) {
        if (value == null) {
            return "";
        }
        return value.toLocale()
            .getDisplayCountry(context.getLocale().orElse(Locales.getDefault()));
    }
}
