package nl.vpro.magnolia.ui.regions;


import info.magnolia.ui.field.FieldType;

import java.util.Arrays;

import javax.inject.Inject;

import org.meeuw.i18n.countries.CurrentCountry;

import com.neovisionaries.i18n.CountryCode;

/**
 * @author Michiel Meeuwissen
 * @since 3.0
 */
@FieldType("countryField")
//@StyleSheet("https://cdnjs.cloudflare.com/ajax/libs/flag-icon-css/3.4.6/css/flag-icon.min.css")
public class CountrySelectFieldDefinition extends RegionSelectFieldDefinition {

    @Inject
    public CountrySelectFieldDefinition(RegionDataSource dataSourceDefinition) {
        super(dataSourceDefinition);
        setRegionClass(CurrentCountry.class);
        setCountryAssignment(Arrays.asList(CountryCode.Assignment.OFFICIALLY_ASSIGNED));
    }

}
