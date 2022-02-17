package nl.vpro.magnolia.ui.enumfield;

import info.magnolia.ui.field.ConfiguredFieldDefinition;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;

import nl.vpro.i18n.Displayable;

/**
 * Abstract super class for {@link Enum}
 * @author Michiel Meeuwissen
 * @since 1.0
 */
@SuppressWarnings("rawtypes")
@Log4j2
public abstract class AbstractEnumFieldDefinition<E extends Enum<E>> extends ConfiguredFieldDefinition<String> {

    private Class<E> enumClass;

    @Getter
    private EnumConverter<E> converter;

    @Getter
    @Setter
    private Boolean multiselect = null;


    @Getter
    @Setter boolean twinselect = false;

    @Getter
    @Setter
    private Boolean useIcons = null;


    @Getter
    @Setter
    private boolean filter = true;

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

    public void setConverter(EnumConverter<E> converter) {
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
    protected  E getEnumByName(String value) {
        Class currentEnumClass = getEnum();
        assert currentEnumClass != null; // It seems that bytebuddy is messing all stuff horribly up. this.enumClass may simply be null.
        return value == null ? null : (E) Enum.valueOf(currentEnumClass, value);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    protected EnumConverter<E> converterFromFromEnum(Class<E> enumClass) {
        return getDisplayableConverter(enumClass).orElseGet(() ->  new EnumConverter(enumClass));
    }

    Optional<EnumConverter<E>> getDisplayableConverter(Class<E> enumClass) {
        return getDisplayableConverter(enumClass, isFilter());
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    static <E extends Enum<E>> Optional<EnumConverter<E>> getDisplayableConverter(Class<E> enumClass, boolean isFilter) {
        try {
            return Optional.of(enumClass)
                .filter(Displayable.class::isAssignableFrom)
                .map(e -> new DisplayableConverter(e, isFilter))
                ;
        } catch (NoClassDefFoundError classNotFoundException) {
            return Optional.empty();
        }
    }

    Stream<E> getItems() {
        return Arrays.stream(getEnum().getEnumConstants()).filter(e -> getConverter().filter(e));
    }


    public boolean isUseIcons() {
        return useIcons == null || useIcons == Boolean.TRUE;
    }
}
