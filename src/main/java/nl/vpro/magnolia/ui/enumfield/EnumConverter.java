package nl.vpro.magnolia.ui.enumfield;

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
        for (E e : clazz.getEnumConstants()) {
            if (convertToPresentation(e, context).equals(value)) {
                return Result.ok(e);
            }
        }
        //log.warn("Could not convert " + value + " to " + clazz);
        return Result.ok(null);
    }

    @Override
    public String convertToPresentation(E value, ValueContext context) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }
}
