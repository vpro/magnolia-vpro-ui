package nl.vpro.magnolia.ui.countryfield;


import info.magnolia.ui.field.FieldType;

import com.neovisionaries.i18n.CountryCode;
import com.vaadin.data.ValueContext;

import nl.vpro.i18n.Locales;
import nl.vpro.magnolia.ui.enumfield.EnumFieldDefinition;

/**
 * @author Michiel Meeuwissen
 * @since 3.0
 */
@FieldType("countryField")
public class CountrySelectFieldDefinition extends EnumFieldDefinition {

    public CountrySelectFieldDefinition() {
        super(CountryCode.class);
    }

    @Override
    protected String convertToPresentation(String value, ValueContext context) {
        return CountryCode.valueOf(value).toLocale()
            .getDisplayCountry(context.getLocale().orElse(Locales.getDefault()));
    }
}
