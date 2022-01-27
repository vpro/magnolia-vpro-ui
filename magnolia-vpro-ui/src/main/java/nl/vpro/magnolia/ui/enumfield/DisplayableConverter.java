package nl.vpro.magnolia.ui.enumfield;

import com.vaadin.data.ValueContext;
import nl.vpro.i18n.Displayable;
import nl.vpro.i18n.Locales;

public class DisplayableConverter<E extends Enum<E> & Displayable> extends EnumConverter<E> {


    private final boolean filter;

    public DisplayableConverter(Class<E> clazz, boolean filter) {
        super(clazz);
        this.filter = filter;
    }

    @Override
    public String convertToPresentation(E value, ValueContext context) {
        return value.getDisplayName(
            context.getLocale().orElse(Locales.getDefault())
        ).getValue();
    }

    @Override
    public String getIcon(E value) {
        return value.getIcon().orElse(null);
    }

    @Override
    public boolean filter(E value) {
        return (! filter) || value.display();
    }
}
