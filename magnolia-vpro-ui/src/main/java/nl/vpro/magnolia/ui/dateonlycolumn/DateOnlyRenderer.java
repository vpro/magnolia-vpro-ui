package nl.vpro.magnolia.ui.dateonlycolumn;

import info.magnolia.context.Context;
import info.magnolia.ui.framework.util.TimezoneUtil;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import javax.inject.Inject;
import javax.inject.Provider;

import com.vaadin.ui.renderers.DateRenderer;

/**
 * Based on: info.magnolia.ui.contentapp.configuration.column.renderer.DefaultDateRenderer
 * but without the time.
 */
public class DateOnlyRenderer extends DateRenderer  {

   private static final String DEFAULT_DATE_TIME_PATTERN = "MMM dd, yyyy";

    @Inject
    public DateOnlyRenderer(Provider<Context> contextProvider) {
        super(
            createDateFormat(
                TimezoneUtil.getUserLocale(contextProvider.get().getUser()),
                TimezoneUtil.getUserTimezone(contextProvider.get().getUser())
            )
        );
    }

    private static SimpleDateFormat createDateFormat(Locale userLocale, TimeZone userTimeZone) {
        SimpleDateFormat df = new SimpleDateFormat(DEFAULT_DATE_TIME_PATTERN, userLocale);
        df.setTimeZone(userTimeZone);
        return df;
    }
}
