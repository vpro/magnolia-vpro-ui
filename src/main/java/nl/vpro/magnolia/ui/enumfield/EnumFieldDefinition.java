package nl.vpro.magnolia.ui.enumfield;

import com.vaadin.data.*;
import info.magnolia.ui.field.ConfiguredFieldDefinition;
import info.magnolia.ui.field.FieldType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import nl.vpro.i18n.Displayable;

/**
 * @author Michiel Meeuwissen
 * @since 3.0
 */
@FieldType("enumField")
@Log4j2
public class EnumFieldDefinition extends ConfiguredFieldDefinition<String> {


    private Class<? extends Enum<?>> enumClass;

    @Getter
    private Converter<String, Enum<?>> converter;

    @Getter
    @Setter
    private boolean multiselect = false;

    public EnumFieldDefinition() {
        setFactoryClass(EnumFieldFactory.class);
    }

    /**
     * Can be used by extensions.
     */
    protected EnumFieldDefinition(Class<? extends Enum<?>> enumClass) {
        this();
        setEnum(enumClass);
    }

    public void setEnum(Class<? extends Enum<?>> enumClass) {
        this.enumClass = enumClass;
        if (this.converter == null && enumClass != null) {
            this.converter = converterFromFromEnum(enumClass);
        }
    }

    public Class<? extends Enum<?>> getEnum() {
        return enumClass;
    }

    public void setConverter(Converter<String, Enum<?>> converter) {
        if (converter != null) {
            this.converter = converter;
        } else if (this.converter != null) {
            log.info("Not setting converter {} to null", this.converter);
        }
    }



    /**
     *
     */
    protected String convertToPresentation(Enum<?> value, ValueContext context) {
        return getConverter().convertToPresentation(value, context);
    }

    protected String convertToModel(String value, ValueContext context) {
        Result<Enum<?>> enumResult = getConverter().convertToModel(value, context);
        Enum<?> enumValue = enumResult.getOrThrow(RuntimeException::new);
        return enumValue == null ? null : enumValue.name();
    }

    String convertToPresentation(String value, ValueContext context) {
        return getConverter().convertToPresentation(getEnumByName(value), context);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private  Enum<?> getEnumByName(String value) {
        Class currentEnumClass = getEnum();
        assert currentEnumClass != null; // It seems that bytebuddy is messing all stuff horribly up. this.enumClass may simply be null.
        return value == null ? null : Enum.valueOf(currentEnumClass, value);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    protected static Converter<String, Enum<?>> converterFromFromEnum(Class<? extends Enum<?>> enumClass) {
        return Displayable.class.isAssignableFrom(enumClass) ? new DisplayableConverter(enumClass) : new EnumConverter(enumClass);
    }

}
