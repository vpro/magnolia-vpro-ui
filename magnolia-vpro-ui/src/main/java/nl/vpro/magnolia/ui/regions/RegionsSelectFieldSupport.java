package nl.vpro.magnolia.ui.regions;

import info.magnolia.cms.i18n.I18nContentSupport;
import info.magnolia.context.MgnlContext;
import info.magnolia.ui.field.SelectFieldSupport;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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

@Log4j2
public class RegionsSelectFieldSupport implements SelectFieldSupport<Region> {

    // uses java service loader
    private static final RegionService regionService = RegionService.getInstance();

    private final RegionSelectFieldDefinition fieldDefinition;
    private final I18nContentSupport i18nContentSupport;

    static final String WEBJARS;
    public static final boolean HAS_WEBJARS_JAR;
    static {
        Optional<String> localWebJars = getLocalWebJars();
        WEBJARS = getLocalWebJars().orElseGet(
            RegionsSelectFieldSupport::getCdnWebJars
        );
        HAS_WEBJARS_JAR =  getLocalWebJars().isPresent();
    }

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
        return region -> {
            if (fieldDefinition.isUseIcons() && region instanceof CurrentCountry) {
                CurrentCountry country = (CurrentCountry) region;
                if (country.getCountryCode().getAssignment() == CountryCode.Assignment.OFFICIALLY_ASSIGNED) {
                    String baseUrl = HAS_WEBJARS_JAR ? MgnlContext.getWebContext().getContextPath() + WEBJARS : WEBJARS;
                    return new ExternalResource(baseUrl + region.getCode().toLowerCase() + ".svg");
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

    private static Optional<String> getLocalWebJars() {
        URL url  = RegionsSelectFieldSupport.class.getClassLoader().getResource("META-INF/maven/org.webjars.npm/flag-icon-css/pom.properties");
        if (url != null) {
            Properties prop = new Properties();
            try (InputStream input = url.openStream()) {
                prop.load(input);
                return Optional.of("/webjars/flag-icon-css/" + prop.getProperty("version") + "/flags/4x3/");
            } catch (IOException e) {
                log.warn(e.getMessage());
            }
        }
        return Optional.empty();
    }

    @SneakyThrows
    static String getCdnWebJars()  {
        URL url  = RegionsSelectFieldSupport.class.getClassLoader().getResource("META-INF/maven/nl.vpro.magnolia/magnolia-vpro-ui/maven.properties");
        Properties prop = new Properties();
        try (InputStream input = url.openStream()) {
            prop.load(input);
        }
        return "https://cdnjs.cloudflare.com/ajax/libs/flag-icon-css/" + prop.getProperty("flag-icon-css.version")+ "/flags/4x3/";
    }
}
