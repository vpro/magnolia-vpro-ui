package nl.vpro.magnolia.ui.enumfield;

import java.util.Optional;

import com.vaadin.data.*;

public class EnumConverter<E extends Enum<E>> implements Converter<String, E> {

    private  final Class<E> clazz;

    public EnumConverter(Class<E> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Result<E> convertToModel(String value, ValueContext context) {
        if (value == null) {
            return Result.ok(null);
        }
        Optional<E> e =  fromString(value, context);
        //log.warn("Could not convert " + value + " to " + clazz);
        return e.map(Result::ok).orElseGet(() -> Result.ok(null));
    }

    @Override
    public String convertToPresentation(E value, ValueContext context) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    private Optional<E> fromString(String value, ValueContext context) {
        for (E e : clazz.getEnumConstants()) {
            if (convertToPresentation(e, context).equals(value)) {
                return Optional.of(e);
            }
        }
        return Optional.empty();
    }

    public String getIcon(String value, ValueContext context) {
        return getIcon(Enum.valueOf(clazz, value));
    }


    public String getIcon(E value) {
        return null;
    }

    protected boolean filter(E value) {
        return true;
    }

}
