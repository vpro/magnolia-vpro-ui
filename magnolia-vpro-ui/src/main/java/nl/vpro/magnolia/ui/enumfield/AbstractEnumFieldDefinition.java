package nl.vpro.magnolia.ui.enumfield;

import com.vaadin.data.*;
import info.magnolia.ui.field.ConfiguredFieldDefinition;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import nl.vpro.i18n.Displayable;

/**
 * Abstract super class for {@link Enum}
 * @author Michiel Meeuwissen
 * @since 3.0
 */
@SuppressWarnings("rawtypes")
@Log4j2
public abstract class AbstractEnumFieldDefinition<E extends Enum> extends ConfiguredFieldDefinition<String> {


    private Class<E> enumClass;


    @Getter
    private Converter<String, E> converter;

    @Getter
    @Setter
    private boolean multiselect = false;

    @SuppressWarnings("unchecked")
    public AbstractEnumFieldDefinition() {
        setFactoryClass((Class) EnumFieldFactory.class);
    }

    /**
     * Can be used by extensions.
     */
    protected AbstractEnumFieldDefinition(Class<E> enumClass) {
        this();
        setEnum(enumClass);
    }

    public void setEnum(Class<E> enumClass) {
        this.enumClass = enumClass;
        if (this.converter == null && enumClass != null) {
            this.converter = converterFromFromEnum(enumClass);
        }
    }

    public Class<E> getEnum() {
        return enumClass;
    }

    public void setConverter(Converter<String, E> converter) {
        if (converter != null) {
            this.converter = converter;
        } else if (this.converter != null) {
            log.info("Not setting converter {} to null", this.converter);
        }
    }

    /**
     *
     */
    protected String convertToPresentation(E value, ValueContext context) {
        return getConverter().convertToPresentation(value, context);
    }

    /**
     * Converts the name of the enum to a value to display in the combobox.
     *
     * This per default is
     */
    protected String convertToPresentation(String value, ValueContext context) {
        return convertToPresentation(getEnumByName(value), context);
    }

    protected String convertToModel(String value, ValueContext context) {
        Result<E> enumResult = getConverter().convertToModel(value, context);
        E enumValue = enumResult.getOrThrow(RuntimeException::new);
        return enumValue == null ? null : enumValue.name();
    }



    @SuppressWarnings({"unchecked", "rawtypes"})
    private  E getEnumByName(String value) {
        Class currentEnumClass = getEnum();
        assert currentEnumClass != null; // It seems that bytebuddy is messing all stuff horribly up. this.enumClass may simply be null.
        return value == null ? null : (E) Enum.valueOf(currentEnumClass, value);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    protected Converter<String, E> converterFromFromEnum(Class<E> enumClass) {
        return getDisplayableConverter(enumClass).orElseGet(() ->  new EnumConverter(enumClass));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    Optional<Converter<String, E>> getDisplayableConverter(Class<E> enumClass) {
        try {
            return Optional.of(enumClass)
                .filter(Displayable.class::isAssignableFrom)
                .map(e -> new DisplayableConverter(e))
                ;
        } catch (NoClassDefFoundError classNotFoundException) {
            return Optional.empty();
        }

    }

}