package nl.vpro.magnolia.ui.enumfield;

import com.vaadin.data.ValueContext;
import java.util.Locale;
import nl.vpro.i18n.Displayable;

public class DisplayableConverter<E extends Enum<E> & Displayable> extends EnumConverter<E> {

    public DisplayableConverter(Class<E> clazz) {
        super(clazz);
    }

    @Override
    public String convertToPresentation(E value, ValueContext context) {
        return value.getDisplayName(context.getLocale().orElse(Locale.getDefault())).getValue();
    }
}
