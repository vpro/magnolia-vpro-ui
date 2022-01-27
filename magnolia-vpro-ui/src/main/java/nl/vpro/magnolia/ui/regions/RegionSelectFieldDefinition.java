package nl.vpro.magnolia.ui.regions;


import info.magnolia.ui.field.ComboBoxFieldDefinition;
import info.magnolia.ui.field.FieldType;
import lombok.Getter;
import lombok.Setter;

import javax.inject.Inject;

import org.meeuw.i18n.regions.Region;

import com.neovisionaries.i18n.CountryCode;

/**
 * @author Michiel Meeuwissen
 * @since 1.0
 */
@FieldType("regionField")
public class RegionSelectFieldDefinition extends ComboBoxFieldDefinition<Region> {

    @Getter
    @Setter
    private boolean useIcons;

    @Getter
    @Setter
    private Region.Type regionType;

    @Getter
    @Setter
    private Class<? extends Region> regionClass;


    @Getter
    @Setter
    private CountryCode.Assignment countryAssignment;

    @Inject
    public RegionSelectFieldDefinition(
        RegionDataSource dataSourceDefinition) {
        setFactoryClass(RegionsSelectFactory.class);
        setDatasource(dataSourceDefinition);
        setStyleName("region");
    }

}
