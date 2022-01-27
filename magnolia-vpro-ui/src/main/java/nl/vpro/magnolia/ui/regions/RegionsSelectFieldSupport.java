package nl.vpro.magnolia.ui.regions;

import info.magnolia.cms.i18n.I18nContentSupport;
import info.magnolia.ui.field.SelectFieldSupport;

import java.util.*;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.meeuw.i18n.countries.CurrentCountry;
import org.meeuw.i18n.regions.Region;
import org.meeuw.i18n.regions.RegionService;

import com.neovisionaries.i18n.CountryCode;
import com.vaadin.data.*;
import com.vaadin.data.provider.*;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.IconGenerator;
import com.vaadin.ui.ItemCaptionGenerator;

public class RegionsSelectFieldSupport implements SelectFieldSupport<Region> {

    // uses java service loader
    private static final RegionService regionService = RegionService.getInstance();

    private final RegionSelectFieldDefinition fieldDefinition;
    private final I18nContentSupport i18nContentSupport;

    @Inject
    public RegionsSelectFieldSupport(
        RegionSelectFieldDefinition definition,
        I18nContentSupport i18nContentSupport) {
        this.fieldDefinition = definition;
        this.i18nContentSupport = i18nContentSupport;

    }


    @Override
    public DataProvider<Region, String> getDataProvider() {

        final Locale locale = i18nContentSupport.getLocale();

        return new AbstractDataProvider<Region, String>() {
            @Override
            public boolean isInMemory() {
                return true;
            }

            @Override
            public int size(Query<Region, String> query) {
                return (int) fetch(query).count();
            }

            @Override
            public Stream<Region> fetch(Query<Region, String> query) {
                return fieldDefinition.regions()
                    .sorted(
                        Comparator.comparing(r -> r.getName(locale))
                    );
            }
        };
    }



    @Override
    public ItemCaptionGenerator<Region> getItemCaptionGenerator() {
        return region -> region.getName(i18nContentSupport.getLocale()) + (fieldDefinition.isShowCode() ? " (" + region.getCode() + ")" : "");
    }

    @Override
    public IconGenerator<Region> getIconGenerator() {
        //return item -> new ExternalResource("/webjars/flag-icon-css/flags/4x3/" + item.toLowerCase() + ".svg");

        return region -> {
            if (fieldDefinition.isUseIcons() && region instanceof CurrentCountry) {
                CurrentCountry country = (CurrentCountry) region;
                if (country.getCountryCode().getAssignment() == CountryCode.Assignment.OFFICIALLY_ASSIGNED) {
                    return new ExternalResource("https://cdnjs.cloudflare.com/ajax/libs/flag-icon-css/3.4.6/flags/4x3/" + region.getCode().toLowerCase() + ".svg");
                }
            }
            return null;
        };
    }

    @Override
    public Converter<Region, String> defaultConverter() {
        return new Converter<Region, String>() {
            @Override
            public Result<String> convertToModel(Region value, ValueContext context) {
                return Result.ok(value.getCode());
            }

            @Override
            public Region convertToPresentation(String value, ValueContext context) {
                if (value == null) {
                    return null;
                }
                return regionService.getByCode(value).orElse(null);
            }
        };

    }
}
