package nl.vpro.magnolia.ui.enumfield;

import com.vaadin.data.ValueContext;
import nl.vpro.i18n.Displayable;
import nl.vpro.i18n.Locales;

public class DisplayableConverter<E extends Enum<E> & Displayable> extends EnumConverter<E> {

    public DisplayableConverter(Class<E> clazz) {
        super(clazz);
    }

    @Override
    public String convertToPresentation(E value, ValueContext context) {
        return value.getDisplayName(
            context.getLocale().orElse(Locales.getDefault())
        ).getValue();
    }
}
