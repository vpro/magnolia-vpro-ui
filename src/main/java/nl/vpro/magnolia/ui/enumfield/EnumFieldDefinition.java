package nl.vpro.magnolia.ui.enumfield;

import info.magnolia.ui.field.FieldType;
import lombok.extern.log4j.Log4j2;

/**
 * Ungeneric version of {@link AbstractEnumFieldDefinition}, because otherwise bytebuddy gets very confused.
 * (might be related to https://stackoverflow.com/questions/45864313/define-field-with-generic-type-using-bytebuddy)
 * @author Michiel Meeuwissen
 * @since 3.0
 */
@FieldType("enumField")
@Log4j2
public class EnumFieldDefinition extends AbstractEnumFieldDefinition<Enum> {


}
